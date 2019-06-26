package com.example.dagger2_demo.bean;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Test {

    private Context mContext;

    @Inject
    public Test(Context context) {
        mContext = context;
    }
}
