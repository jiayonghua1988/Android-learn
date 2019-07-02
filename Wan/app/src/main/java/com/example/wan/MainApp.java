package com.example.wan;

import android.content.Context;

import com.example.common_base.base.BaseApplication;

public class MainApp extends BaseApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }

}
