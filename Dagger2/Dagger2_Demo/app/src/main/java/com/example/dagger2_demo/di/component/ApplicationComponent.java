package com.example.dagger2_demo.di.component;

import android.location.LocationManager;


import com.example.dagger2_demo.MainActivity;
import com.example.dagger2_demo.app.DemoApplication;
import com.example.dagger2_demo.di.module.AndroidModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {

    void inject(DemoApplication application);

    void inject(MainActivity activity);

    LocationManager getLocationManager();
}
