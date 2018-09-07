package com.handsomexi.homework;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.handsomexi.homework.Util.Util;
import com.squareup.leakcanary.LeakCanary;

public class Myapp extends Application {
    public static Myapp instances;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    public DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        setDatabase();
        Util.mkDirs();
        if (!LeakCanary.isInAnalyzerProcess(this)) LeakCanary.install(this);
    }
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "main", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public static Myapp getInstances(){
        return instances;
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


}
