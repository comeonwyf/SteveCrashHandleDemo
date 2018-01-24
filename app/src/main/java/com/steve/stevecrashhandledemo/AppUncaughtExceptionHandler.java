package com.steve.stevecrashhandledemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.steve.stevecrashhandledemo.model.CrashException;

/**
 * @author steve
 * @date 2018/1/17
 */

public class AppUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {


    private PrintWriter pw;
    private StringWriter sw;

    //构造方法私有，防止外部构造多个实例，即采用单例模式
    private AppUncaughtExceptionHandler() {
    }

    //程序的Context对象
    private Context applicationContext;

    private volatile boolean crashing;

    /**
     * 日期格式器
     */
    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 单例
     */
    private static AppUncaughtExceptionHandler sAppUncaughtExceptionHandler;
    public static synchronized AppUncaughtExceptionHandler getInstance() {
        if (sAppUncaughtExceptionHandler == null) {
            synchronized (AppUncaughtExceptionHandler.class) {
                if (sAppUncaughtExceptionHandler == null) {
                    sAppUncaughtExceptionHandler = new AppUncaughtExceptionHandler();
                }
            }
        }
        return sAppUncaughtExceptionHandler;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        applicationContext = context.getApplicationContext();
        crashing = false;

        sw =  new StringWriter();
        pw = new PrintWriter(sw);

        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (crashing) {
            return;
        }
        crashing = true;

        // 打印异常信息
        ex.printStackTrace();

        // 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
        if (!handlelException(ex) && mDefaultHandler != null) {
            // 系统处理
            mDefaultHandler.uncaughtException(thread, ex);
        }

        byebye();
    }

    private void byebye() {
        SystemClock.sleep(2000);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private boolean handlelException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        try {
            //处理异常信息，并存入数据库，然后发送至服务器
            handleCrashReport(ex);
            // 提示对话框
            showPatchDialog();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     *处理异常信息，并存入数据库，然后发送至服务器
     * @param ex
     * @return
     */
    private String handleCrashReport(Throwable ex) {
        String exceptionStr = "";
        PackageInfo pinfo = CrashApp.getInstance().getLocalPackageInfo();
        CrashException crashException = null;
        if (pinfo != null) {
            if (ex != null) {
                //异常全部信息
                if(sw==null){
                    sw = new StringWriter();
                }
                if(pw==null){
                    pw = new PrintWriter(sw);
                }
                ex.printStackTrace(pw);
                String errorStr = sw.toString();

                if (TextUtils.isEmpty(errorStr)) {
                    errorStr = ex.getMessage();
                }
                if (TextUtils.isEmpty(errorStr)) {
                    errorStr = ex.toString();
                }
                exceptionStr = errorStr;

                //存入数据库中
                crashException = new CrashException(CrashException.creatCrashId(),
                        mFormatter.format(new Date()),getApplicationName(applicationContext),pinfo.versionName,
                        Build.VERSION.RELEASE,Build.MANUFACTURER,Build.MODEL,errorStr,CrashException.TYPE_TOSEND);


                Log.e("print", "异常信息: "+ crashException.getCrashExceptionInfor());

                //发送到邮箱
                SendExceptionManager.getInstance().sendToEmail(crashException);

            } else {
                exceptionStr = "no exception. Throwable is null";
            }

            return exceptionStr;
        } else {
            return "";
        }
    }


    private void showPatchDialog() {
        Intent intent = PatchDialogActivity.newIntent(applicationContext, getApplicationName(applicationContext), null);
        applicationContext.startActivity(intent);
    }

    private String getApplicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String name = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    context.getApplicationInfo().packageName, 0);
            name = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (final PackageManager.NameNotFoundException e) {
            String[] packages = context.getPackageName().split(".");
            name = packages[packages.length - 1];
        }
        return name;
    }


}
