package com.example.daggerdemo2.di.bean.component;

import com.example.daggerdemo2.MainActivity;
import com.example.daggerdemo2.di.bean.module.MainActivityModule;
import com.example.daggerdemo2.di.bean.scope.PerActivity;

import javax.inject.Singleton;

import dagger.Component;



@Component(modules = MainActivityModule.class,dependencies = AppComponent.class)
@Singleton
public interface MainActivityComponent {

    void inject(MainActivity activity);
}
