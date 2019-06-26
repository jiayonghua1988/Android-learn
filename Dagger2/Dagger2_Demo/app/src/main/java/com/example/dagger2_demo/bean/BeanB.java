package com.example.dagger2_demo.bean;

import android.util.Log;

import javax.inject.Inject;

public class BeanB {

    @Inject
    BeanA beanA;

    private void printInfo() {
        Log.d("TEST",beanA.getName());
    }
}
