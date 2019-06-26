package com.example.dagger2_demo.di.component;

import com.example.dagger2_demo.activity.HelloActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface HelloActivityComponent {

    void inject(HelloActivity helloActivity);
}
