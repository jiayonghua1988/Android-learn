package com.example.daggerdemo2.di.bean.component;

import com.example.daggerdemo2.di.bean.Accommodation;
import com.example.daggerdemo2.di.bean.Food;
import com.example.daggerdemo2.di.bean.module.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    /**
     * 将AppModule中的Food、Accommodation暴露出来，以便于其他依赖于AppComponent的Component调用，
     * 也就是让后勤部门把食物和住宿提供出来
     */

    Food getFood();
    Accommodation getAccommodation();
}
