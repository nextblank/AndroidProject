package com.nextblank.android.sdk.tools.io;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import com.nextblank.android.sdk.tools.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class StorageTool {

    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";

    private static final Object lock = new Object();
    private static volatile StorageTool instance;

    private StorageTool() {
    }

    public static StorageTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new StorageTool();
                }
            }
        }
        return instance;
    }

    /**
     * @return True if the external storage is available. False otherwise.
     */
    public boolean isAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * @return True if the external storage is writable. False otherwise.
     */
    public boolean isWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * @return A map of all storage locations available
     */
    public Map<String, File> getAllStorageLocations() {
        Map<String, File> map = new HashMap<String, File>(10);

        List<String> mMounts = new ArrayList<String>(10);
        List<String> mVold = new ArrayList<String>(10);
        mMounts.add("/mnt/sdcard");
        mVold.add("/mnt/sdcard");

        try {
            File mountFile = new File("/proc/mounts");
            if (mountFile.exists()) {
                Scanner scanner = new Scanner(mountFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("/dev/block/vold/")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[1];

                        // don't add the default mount path
                        // it's already in the list.
                        if (!element.equals("/mnt/sdcard")) {
                            mMounts.add(element);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File voldFile = new File("/system/etc/vold.fstab");
            if (voldFile.exists()) {
                Scanner scanner = new Scanner(voldFile);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("dev_mount")) {
                        String[] lineElements = line.split(" ");
                        String element = lineElements[2];

                        if (element.contains(":")) {
                            element = element.substring(0, element.indexOf(":"));
                        }
                        if (!element.equals("/mnt/sdcard")) {
                            mVold.add(element);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < mMounts.size(); i++) {
            String mount = mMounts.get(i);
            if (!mVold.contains(mount)) {
                mMounts.remove(i--);
            }
        }
        mVold.clear();

        List<String> mountHash = new ArrayList<String>(10);

        for (String mount : mMounts) {
            File root = new File(mount);
            if (root.exists() && root.isDirectory() && root.canWrite()) {
                File[] list = root.listFiles();
                String hash = "[";
                if (list != null) {
                    for (File f : list) {
                        hash += f.getName().hashCode() + ":" + f.length() + ", ";
                    }
                }
                hash += "]";
                if (!mountHash.contains(hash)) {
                    String key = SD_CARD + "_" + map.size();
                    if (map.size() == 0) {
                        key = SD_CARD;
                    } else if (map.size() == 1) {
                        key = EXTERNAL_SD_CARD;
                    }
                    mountHash.add(hash);
                    map.put(key, root);
                }
            }
        }

        mMounts.clear();

        if (map.isEmpty()) {
            map.put(SD_CARD, Environment.getExternalStorageDirectory());
        }
        return map;
    }

    /**
     * 获取内部可用空间大小
     *
     * @return
     */
    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取外部可用空间大小
     *
     * @return
     */
    public long getAvailableExternalMemorySize() {
        if (isWritable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;
        }
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 清除内部缓存
     * <p>/data/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalCache() {
        return Tools.file().deleteFilesInDir(Tools.app().getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalFiles() {
        return Tools.file().deleteFilesInDir(Tools.app().getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalDbs() {
        return Tools.file().deleteFilesInDir(Tools.app().getFilesDir().getParent() + File.separator + "databases");
    }

    /**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param dbName 数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalDbByName(String dbName) {
        return Tools.app().deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanInternalSP() {
        return Tools.file().deleteFilesInDir(Tools.app().getFilesDir().getParent() + File.separator + "shared_prefs");
    }

    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanExternalCache() {
        return isWritable() && Tools.file().deleteFilesInDir(Tools.app().getExternalCacheDir());
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanCustomCache(String dirPath) {
        return Tools.file().deleteFilesInDir(dirPath);
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir 目录
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public boolean cleanCustomCache(File dir) {
        return Tools.file().deleteFilesInDir(dir);
    }

    /**
     * 清除App所有数据
     *
     * @param context  上下文
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean cleanAppData(Context context, String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除App所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean cleanAppData(File... dirs) {
        boolean isSuccess = cleanInternalCache();
        isSuccess &= cleanInternalDbs();
        isSuccess &= cleanInternalSP();
        isSuccess &= cleanInternalFiles();
        isSuccess &= cleanExternalCache();
        for (File dir : dirs) {
            isSuccess &= cleanCustomCache(dir);
        }
        return isSuccess;
    }

    public void cleanMemory() {
        // To change body of implemented methods use File | Settings | File Templates.
        ActivityManager am = (ActivityManager) Tools.app()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        long beforeMem = getAvailMemoryMB();
        Tools.log().d("-----------before memory info : " + beforeMem);
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                Tools.log().d("process name : " + appProcessInfo.processName);
                // importance 该进程的重要程度 分为几个级别，数值越低就越重要。
                Tools.log().d("importance : " + appProcessInfo.importance);
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (String aPkgList : pkgList) {// pkgList 得到该进程下运行的包名
                        Tools.log().d("It will be killed, package name : " + aPkgList);
                        am.killBackgroundProcesses(aPkgList);
                    }
                }
            }
        }
        long afterMem = getAvailMemoryMB();
        Tools.log().d("----------- after memory info : " + afterMem);
    }

    // 获取android当前可用内存大小
    public String getAvailMemory() {
        ActivityManager am = (ActivityManager) Tools.app()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(Tools.app(), mi.availMem);// 将获取的内存大小规格化
    }

    // 获取可用内存大小
    public long getAvailMemoryMB() {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) Tools.app().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        Tools.log().d("可用内存---->>>" + mi.availMem / (1024 * 1024));
        return mi.availMem / (1024 * 1024);
    }

    // 获得系统总内存
    public String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Tools.log().i("系统内存Info1" + str2 + num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]) * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(Tools.app(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    public boolean isLowInternalMemory() {
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        return outInfo.lowMemory;
    }

}
