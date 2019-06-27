package com.example.daggerdemo2.app;

import android.app.Application;
import android.location.LocationManager;
import android.util.Log;

import com.example.daggerdemo2.di.bean.component.DaggerApplicationComponent;
import com.example.daggerdemo2.di.bean.module.AndroidModule;

import javax.inject.Inject;

public class DemoApplication extends Application {


    @Inject
    LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build().
                inject(this);

        Log.d("Test","locationManager=" +locationManager);
    }
}
