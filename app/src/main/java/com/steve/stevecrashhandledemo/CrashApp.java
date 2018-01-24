package com.steve.stevecrashhandledemo;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.steve.stevecrashhandledemo.dao.DaoMaster;
import com.steve.stevecrashhandledemo.dao.DaoSession;

/**
 * @author steve
 * @date 2018/1/17
 */

public class CrashApp extends Application {

    private static CrashApp mInstance = null;
    private static DaoSession daoSession;

    public static CrashApp getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mInstance;
    }

    public static DaoSession getDaoInstance(){
        if (daoSession == null) {
            throw new IllegalStateException("GreenDao is not created.");
        }
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // 捕捉异常初始化
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());

        //初始化greendao数据库
        setupDatabase();

    }

    /**
     * 获取自身App安装包信息
     * @return
     */
    public PackageInfo getLocalPackageInfo() {
        return getPackageInfo(getPackageName());
    }

    /**
     * 获取App安装包信息
     * @return
     */
    public PackageInfo getPackageInfo(String packageName) {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    private void setupDatabase() {
        //创建数据库myDB.db
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mInstance,"myDB.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();

        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象的管理者
        daoSession = daoMaster.newSession();
    }


}
