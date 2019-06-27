package com.example.daggerdemo2.di.bean.component;

import com.example.daggerdemo2.di.bean.Mansion;
import com.example.daggerdemo2.di.bean.module.BeggarModule;

import dagger.Component;

@Component(modules = BeggarModule.class,dependencies = AppComponent.class)
public interface BeggarComponent {
    // 去刘府报名去
    void inject(Mansion mansion);
}
