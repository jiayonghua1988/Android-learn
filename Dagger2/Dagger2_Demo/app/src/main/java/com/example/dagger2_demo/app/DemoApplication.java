package com.example.dagger2_demo.app;

import android.app.Application;
import android.location.LocationManager;

import com.example.dagger2_demo.di.component.ApplicationComponent;
import com.example.dagger2_demo.di.component.DaggerApplicationComponent;
import com.example.dagger2_demo.di.module.AndroidModule;

import javax.inject.Inject;

public class DemoApplication extends Application {

    @Inject
    LocationManager locationManager;

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
        component.inject(this);
    }
}
