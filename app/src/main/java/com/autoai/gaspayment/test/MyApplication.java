package com.autoai.gaspayment.test;

import android.app.Application;

/**
 * Created by Android Studio.
 * User: autoai
 * Date: 2020/6/12
 * Time: 18:20
 * Describe:
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE;
    public MyLiveData mMyLiveData;

    public static MyApplication getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        mMyLiveData = new MyLiveData();
    }
}
