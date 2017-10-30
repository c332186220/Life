package com.cxl.life.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;

/**
 * Created by cxl on 2017/10/30.
 * 异常日志捕获
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    //文件夹目录
    private static final String PATH = Constants.crash_sd;
    //文件名
    private static final String FILE_NAME = "crash";
    //文件名后缀
    private static final String FILE_NAME_SUFFIX = ".txt";
    //上下文
    private Context mContext;

    //单例模式
    private static CrashHandler sInstance = new CrashHandler();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    public void init(Context context) {
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
//        autoClear(30);//删除30天之前的数据
    }

    /**
     * 捕获异常回掉
     *
     * @param t 当前线程
     * @param e 异常信息
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //导出异常信息到SD卡
        dumpExceptionToSDCard(e);
        //上传异常信息到服务器
        uploadExceptionToServer(e);
        //延时1秒杀死进程
        SystemClock.sleep(2000);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /**
     * 导出异常信息到SD卡
     *
     * @param ex
     */
    private void dumpExceptionToSDCard(Throwable ex) {
        if (!FileUtil.hasSdcard()) {
            return;
        }
        //创建文件夹
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //获取当前时间
        String time = TimeUtil.getCurrentTime(2);
        //以当前时间创建log文件
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            //输出流操作
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出手机信息和异常信息
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.println("发生异常时间：" + time);
            pw.println("应用版本：" + pi.versionName);
            pw.println("应用版本号：" + pi.versionCode);
            pw.println("android版本号：" + Build.VERSION.RELEASE);
            pw.println("android版本号API：" + Build.VERSION.SDK_INT);
            pw.println("手机制造商:" + Build.MANUFACTURER);
            pw.println("手机型号：" + Build.MODEL);
            ex.printStackTrace(pw);
            //关闭输出流
            pw.close();
        } catch (Exception e) {
            L.e("打印错误信息报错：" + e.getMessage());
        }
    }

    /**
     * 获取异常信息
     *
     * @param ex
     * @return
     */
    private String getCrashReport(Throwable ex) {
        if (ex == null) {
            return "空异常";
        }
        StringBuilder exceptionStr = new StringBuilder();
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            //获取当前时间
            String time = TimeUtil.getCurrentTime(2);
            exceptionStr.append("发生异常时间：" + time);
            exceptionStr.append("应用版本：" + pi.versionName);
            exceptionStr.append("应用版本号：" + pi.versionCode);
            exceptionStr.append("android版本号：" + Build.VERSION.RELEASE);
            exceptionStr.append("android版本号API：" + Build.VERSION.SDK_INT);
            exceptionStr.append("手机制造商:" + Build.MANUFACTURER);
            exceptionStr.append("手机型号：" + Build.MODEL);
            String errorStr = ex.getLocalizedMessage();
            if (TextUtils.isEmpty(errorStr)) {
                errorStr = ex.getMessage();
            }
            if (TextUtils.isEmpty(errorStr)) {
                errorStr = ex.toString();
            }
            exceptionStr.append("Exception: " + errorStr + "\n");
            StackTraceElement[] elements = ex.getStackTrace();
            if (elements != null) {
                for (int i = 0; i < elements.length; i++) {
                    exceptionStr.append(elements[i].toString() + "\n");
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return exceptionStr.toString();
    }

    /**
     * 上传异常信息到服务器
     *
     * @param ex
     */
    private void uploadExceptionToServer(Throwable ex) {
//        Error error = new Error(ex.getMessage());
//        error.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//
//            }
//        });
    }

    /**
     * 文件删除
     *
     * @param autoClearDay 文件保存天数
     */
    public void autoClear(final int autoClearDay) {
        FileUtil.delete(Constants.crash_sd, new FilenameFilter() {

            @Override
            public boolean accept(File file, String filename) {
                String s = FileUtil.getFileNameWithoutExtension(filename);//获取命名
                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                String date = TimeUtil.getOtherDay(day);
                return date.compareTo(s.substring(5)) >= 0;//用N天之前的时间跟命名中的时间对比，来删除文件
            }
        });
    }


}
