package com.example.daggerdemo2.di.bean.module;

import com.example.daggerdemo2.di.bean.Accommodation;
import com.example.daggerdemo2.di.bean.Food;
import com.example.daggerdemo2.di.bean.Salary;

import dagger.Module;
import dagger.Provides;

/**
 * 地主的后勤部门
 */
@Module
public class AppModule {

    /**
     * 提供食物
     * @return
     */
    @Provides
    public Food providerFood() {
        return new  Food();
    }

    /**
     * 提供住宿
     * @return
     */
    @Provides
    public Accommodation provideAccommodation() {
        return new Accommodation();
    }

    /**
     * 提供薪水
     * @return
     */
    @Provides
    public Salary provideSalary() {
        return new Salary();
    }
}
