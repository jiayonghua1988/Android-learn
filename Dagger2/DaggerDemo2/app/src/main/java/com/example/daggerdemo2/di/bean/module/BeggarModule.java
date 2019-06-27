package com.example.daggerdemo2.di.bean.module;

import com.example.daggerdemo2.di.bean.Accommodation;
import com.example.daggerdemo2.di.bean.Employee;
import com.example.daggerdemo2.di.bean.Food;

import dagger.Module;
import dagger.Provides;

@Module
public class BeggarModule {

    @Provides
    public Employee provideEmployee(Food food, Accommodation accommodation) {
        return new Employee(food,accommodation);
    }
}
