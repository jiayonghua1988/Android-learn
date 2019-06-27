package com.example.daggerdemo2.di.bean;

import com.example.daggerdemo2.di.bean.component.AppComponent;
import com.example.daggerdemo2.di.bean.component.BeggarComponent;
import com.example.daggerdemo2.di.bean.component.DaggerAppComponent;
import com.example.daggerdemo2.di.bean.component.DaggerBeggarComponent;
import com.example.daggerdemo2.di.bean.module.AppModule;
import com.example.daggerdemo2.di.bean.module.BeggarModule;

import javax.inject.Inject;

public class Mansion {

    @Inject
    Food food;

    @Inject
    Accommodation accommodation;

    @Inject
    Employee employee;


    private void test() {
        AppModule appModule = new AppModule();
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
        BeggarModule beggarModule = new BeggarModule();
      DaggerBeggarComponent.builder()
                .appComponent(appComponent)
                .beggarModule(beggarModule)
                .build()
                .inject(this);
    }


    public static void main(String [] args) {
        Mansion mansion = new Mansion();
        mansion.test();
    }
}
