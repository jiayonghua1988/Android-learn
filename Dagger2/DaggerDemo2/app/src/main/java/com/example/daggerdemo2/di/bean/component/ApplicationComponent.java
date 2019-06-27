package com.example.daggerdemo2.di.bean.component;

import android.content.Context;

import com.example.daggerdemo2.MainActivity;
import com.example.daggerdemo2.app.DemoApplication;
import com.example.daggerdemo2.di.bean.module.AndroidModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {

    void inject(DemoApplication application);

//    Context getContext();


}
