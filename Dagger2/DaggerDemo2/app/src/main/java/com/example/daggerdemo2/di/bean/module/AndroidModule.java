package com.example.daggerdemo2.di.bean.module;

import android.content.Context;
import android.location.LocationManager;

import com.example.daggerdemo2.app.DemoApplication;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {

    private DemoApplication application;
    public AndroidModule(DemoApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    LocationManager providerLocationManager() {
        return (LocationManager)application.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    Context providerContext() {
        return application;
    }

}
