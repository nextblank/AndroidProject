package com.nextblank.sdk.tools.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.nextblank.sdk.tools.Tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public final class AppTool {

    private static final Object lock = new Object();
    private static volatile AppTool instance;
    private static Stack<Activity> activityStack;

    private AppTool() {
    }

    public static AppTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppTool();
                }
            }
        }
        return instance;
    }

    /**
     * 获取所有运行的服务
     *
     * @param context 上下文
     * @return 服务名集合
     */
    public Set getAllRunningService(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = activityManager.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (infos == null || infos.size() == 0) return null;
        for (RunningServiceInfo info : infos) {
            names.add(info.service.getClassName());
        }
        return names;
    }

    /**
     * 启动服务
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     */
    public void startService(Context context, String className) {
        try {
            startService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     *
     * @param context 上下文
     * @param cls     服务类
     */
    public void startService(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }

    /**
     * 停止服务
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public boolean stopService(Context context, String className) {
        try {
            return stopService(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     *
     * @param context 上下文
     * @param cls     服务类
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public boolean stopService(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        return context.stopService(intent);
    }

    /**
     * 绑定服务
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public void bindService(Context context, String className, ServiceConnection conn, int flags) {
        try {
            bindService(context, Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     *
     * @param context 上下文
     * @param cls     服务类
     * @param conn    服务连接对象
     * @param flags   绑定选项
     *                <ul>
     *                <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                </ul>
     */
    public void bindService(Context context, Class<?> cls, ServiceConnection conn, int flags) {
        Intent intent = new Intent(context, cls);
        context.bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     *
     * @param context 上下文
     * @param conn    服务连接对象
     */
    public void unbindService(Context context, ServiceConnection conn) {
        context.unbindService(conn);
    }

    /**
     * 服务是否运行
     *
     * @param mContext
     * @param className
     * @return
     */
    public boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> serviceList
                = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList.size() == 0) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    /**
     * 获取顶层activity名称
     *
     * @param context
     * @return
     */
    public String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager =
                (ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }

    /**
     * 判断是否存在Activity
     *
     * @param context
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return
     */
    public boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     */
    public void launchActivity(Context context, String packageName, String className) {
        launchActivity(context, packageName, className, null);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     */
    public void launchActivity(Context context, String packageName, String className, Bundle bundle) {
        context.startActivity(Tools.intent().getComponentIntent(packageName, className, bundle));
    }

    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public String getLauncherActivity(String packageName) {
        return getLauncherActivity(Tools.app(), packageName);
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    //在需要彻底退出应用的地方调用这个方法，
    public void finishAllActivityAndExit(Context context) {
        if (null != activityStack) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }
}
