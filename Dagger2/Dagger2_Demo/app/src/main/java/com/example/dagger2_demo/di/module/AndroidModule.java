package com.example.dagger2_demo.di.module;

import android.content.Context;
import android.location.LocationManager;

import com.example.dagger2_demo.app.DemoApplication;
import com.example.dagger2_demo.bean.Test3;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 实现一些提供的实例构造器
 */
@Module
public class AndroidModule {

    private final DemoApplication application;

    public AndroidModule(DemoApplication application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Context ApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    LocationManager providerLocationManager() {
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }


}
