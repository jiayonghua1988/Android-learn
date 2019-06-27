package com.example.dagger2_demo.di.module;

import android.location.LocationManager;

import com.example.dagger2_demo.bean.Test5;
import com.example.dagger2_demo.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class CModule {

    @PerActivity
    @Provides
    Test5 providerTest(LocationManager locationManager) {
        return new Test5(locationManager);
    }
}
