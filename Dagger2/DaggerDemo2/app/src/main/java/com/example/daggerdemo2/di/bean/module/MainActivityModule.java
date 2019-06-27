package com.example.daggerdemo2.di.bean.module;

import com.example.daggerdemo2.app.DemoApplication;
import com.example.daggerdemo2.di.bean.Test;
import com.example.daggerdemo2.di.bean.scope.PerActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class MainActivityModule {
    private DemoApplication application;

    public MainActivityModule(DemoApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Test providerTest() {
        return new Test(application.getApplicationContext());
    }

}
