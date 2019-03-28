package com.pn.android.sdk.tools.apk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import com.pn.android.sdk.domain.model.AppInfo;
import com.pn.android.sdk.tools.Tools;
import com.pn.android.sdk.tools.shell.ShellTool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ApkTool {

    private static final Object lock = new Object();
    private static volatile ApkTool instance;

    private ApkTool() {
    }

    public static ApkTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ApkTool();
                }
            }
        }
        return instance;
    }

    /**
     * 安装App
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public void installApp(Context context, String filePath) {
        installApp(context, Tools.file().getFileByPath(filePath));
    }

    /**
     * 安装App
     *
     * @param context 上下文
     * @param file    文件
     */
    public void installApp(Context context, File file) {
        if (!Tools.file().isFileExists(file)) return;
        context.startActivity(Tools.intent().getInstallAppIntent(file));
    }

    /**
     * 安装App
     *
     * @param activity    activity
     * @param filePath    文件路径
     * @param requestCode 请求值
     */
    public void installApp(Activity activity, String filePath, int requestCode) {
        installApp(activity, Tools.file().getFileByPath(filePath), requestCode);
    }

    /**
     * 安装App(支持6.0)
     *
     * @param activity    activity
     * @param file        文件
     * @param requestCode 请求值
     */
    public void installApp(Activity activity, File file, int requestCode) {
        if (!Tools.file().isFileExists(file)) return;
        activity.startActivityForResult(Tools.intent().getInstallAppIntent(file), requestCode);
    }

    /**
     * 静默安装App
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    public boolean installAppSilent(String filePath) {
        File file = Tools.file().getFileByPath(filePath);
        if (!Tools.file().isFileExists(file)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
        ShellTool.CommandResult commandResult = Tools.shell().execCmd(command, !isSystemApp(Tools.app()), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * 卸载App
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public void uninstallApp(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return;
        context.startActivity(Tools.intent().getUninstallAppIntent(packageName));
    }

    /**
     * 卸载App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public void uninstallApp(Activity activity, String packageName, int requestCode) {
        if (Tools.string().isEmpty(packageName)) return;
        activity.startActivityForResult(Tools.intent().getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 静默卸载App
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
     *
     * @param context     上下文
     * @param packageName 包名
     * @param isKeepData  是否保留数据
     * @return {@code true}: 卸载成功<br>{@code false}: 卸载成功
     */
    public boolean uninstallAppSilent(Context context, String packageName, boolean isKeepData) {
        if (Tools.string().isEmpty(packageName)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + (isKeepData ? "-k " : "") + packageName;
        ShellTool.CommandResult commandResult = Tools.shell().execCmd(command, !isSystemApp(context), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }


    /**
     * 判断App是否有root权限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isAppRoot() {
        ShellTool.CommandResult result = Tools.shell().execCmd("echo root", true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            Tools.log().d("isAppRoot", result.errorMsg);
        }
        return false;
    }

    /**
     * 打开App
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public void launchApp(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return;
        context.startActivity(Tools.intent().getLaunchAppIntent(context, packageName));
    }

    /**
     * 打开App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public void launchApp(Activity activity, String packageName, int requestCode) {
        if (Tools.string().isEmpty(packageName)) return;
        activity.startActivityForResult(Tools.intent().getLaunchAppIntent(activity, packageName), requestCode);
    }

    /**
     * 获取App名称
     *
     * @param context 上下文
     * @return App名称
     */
    public String getAppName(Context context) {
        return getAppName(context, context.getPackageName());
    }

    /**
     * 获取App名称
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App名称
     */
    public String getAppName(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App图标
     *
     * @param context 上下文
     * @return App图标
     */
    public Drawable getAppIcon(Context context) {
        return getAppIcon(context, context.getPackageName());
    }

    /**
     * 获取App图标
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App图标
     */
    public Drawable getAppIcon(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App路径
     *
     * @param context 上下文
     * @return App路径
     */
    public String getAppPath(Context context) {
        return getAppPath(context, context.getPackageName());
    }

    /**
     * 获取App路径
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App路径
     */
    public String getAppPath(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断App是否是系统应用
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isSystemApp(Context context) {
        return isSystemApp(context, context.getPackageName());
    }

    /**
     * 判断App是否是系统应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isSystemApp(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isAppDebug(Context context) {
        return isAppDebug(context, context.getPackageName());
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param context     上下文
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isAppDebug(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取App签名
     *
     * @param context 上下文
     * @return App签名
     */
    public Signature[] getAppSignature(Context context) {
        return getAppSignature(context, context.getPackageName());
    }

    /**
     * 获取App签名
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App签名
     */
    @SuppressLint("PackageManagerGetSignatures")
    public Signature[] getAppSignature(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @param context 上下文
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public String getAppSignatureSHA1(Context context) {
        return getAppSignatureSHA1(context, context.getPackageName());
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
     */
    public String getAppSignatureSHA1(Context context, String packageName) {
        Signature[] signature = getAppSignature(context, packageName);
        if (signature == null) return null;
        return Tools.secure().encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }


    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @param channelPrefix
     * @return
     */
    public String getChannelFromApk(Context context, String channelPrefix) {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelPrefix;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split(channelPrefix);
        String channel = "";
        if (split != null && split.length >= 2) {
            channel = ret.substring(key.length());
        }
        return channel;
    }


    /**
     * 判断App是否安装
     *
     * @param context     上下文
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public boolean isInstallApp(Context context, String packageName) {
        return !Tools.string().isEmpty(packageName) && Tools.intent().getLaunchAppIntent(packageName) != null;
    }

    /**
     * 获取App包名
     *
     * @param context 上下文
     * @return App包名
     */
    public String getAppPackageName(Context context) {
        return context.getPackageName();
    }


    /**
     * 获取App版本号
     *
     * @param context 上下文
     * @return App版本号
     */
    public String getAppVersionName(Context context) {
        return getAppVersionName(context, context.getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本号
     */
    public String getAppVersionName(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本码
     *
     * @param context 上下文
     * @return App版本码
     */
    public int getAppVersionCode(Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param context     上下文
     * @param packageName 包名
     * @return App版本码
     */
    public int getAppVersionCode(Context context, String packageName) {
        if (Tools.string().isEmpty(packageName)) return -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 读取application 节点 meta-data 信息
     */
    public String getMetaDataFromApplication(String tag) {
        String mTag = "";
        try {
            ApplicationInfo appInfo = Tools.app().getPackageManager()
                    .getApplicationInfo(Tools.app().getPackageName(),
                            PackageManager.GET_META_DATA);
            mTag = appInfo.metaData.getString(tag);
            Tools.log().i("getMetaDataFromApplication:" + tag + "=" + mTag);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mTag;
    }

    /**
     * 读取BroadcastReceiver cls类 中节点 meta-data 信息
     */
    public String getMetaDataFromBroadCast(String tag, Class<?> cls) {
        String mTag = "";
        try {
            ComponentName cn = new ComponentName(Tools.app(), cls);
            ActivityInfo info = Tools.app().getPackageManager()
                    .getReceiverInfo(cn, PackageManager.GET_META_DATA);
            mTag = info.metaData.getString(tag);
            Tools.log().i("getMetaDataFromBroadCast>" + cls.getSimpleName() + ":"
                    + tag + "=" + mTag);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mTag;
    }

    /**
     * 读取Service 节点 meta-data 信息
     */
    public String getMetaDataFromService(String tag, Class<?> cls) {
        String mTag = "";
        try {
            ComponentName cn = new ComponentName(Tools.app(), cls);
            ServiceInfo info = Tools.app().getPackageManager()
                    .getServiceInfo(cn, PackageManager.GET_META_DATA);
            mTag = info.metaData.getString(tag);
            Tools.log().i("getMetaDataFromService>" + cls.getSimpleName() + ":"
                    + tag + "=" + mTag);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mTag;
    }

    /**
     * 读取Activity 节点 meta-data 信息
     */
    public String getMetaDataFromActivity(String tag, Class<?> cls) {
        String mTag = "";
        ComponentName cn = new ComponentName(Tools.app(), cls);
        try {
            ActivityInfo info = Tools.app().getPackageManager()
                    .getActivityInfo(cn, PackageManager.GET_META_DATA);
            mTag = info.metaData.getString(tag);
            Tools.log().i("getMetaDataFromActivity>" + cls.getSimpleName() + ":"
                    + tag + "=" + mTag);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mTag;
    }

    /**
     * 判断App是否处于前台
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isAppForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return info.processName.equals(context.getPackageName());
            }
        }
        return false;
    }

    /**
     * 判断App是否处于前台
     * <p>当不是查看当前App，且SDK大于21时，
     * 需添加权限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>}</p>
     *
     * @param context     上下文
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isAppForeground(Context context, String packageName) {
        return !Tools.string().isEmpty(packageName) && packageName.equals(Tools.process().getForegroundProcessName());
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @param context 上下文
     * @return 当前应用的AppInfo
     */
    public AppInfo getAppInfo(Context context) {
        return getAppInfo(context, context.getPackageName());
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 当前应用的AppInfo
     */
    public AppInfo getAppInfo(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到AppInfo的Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo类
     */
    private AppInfo getBean(PackageManager pm, PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 获取所有已安装App信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}（名称，图标，包名，包路径，版本号，版本Code，是否系统应用）</p>
     * <p>依赖上面的getBean方法</p>
     *
     * @param context 上下文
     * @return 所有已安装的AppInfo列表
     */
    public List<AppInfo> getAppsInfo(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) continue;
            list.add(ai);
        }
        return list;
    }

}
