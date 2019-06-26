package com.example.dagger2_demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dagger2_demo.bean.Test3;
import com.example.dagger2_demo.di.component.DaggerHelloActivityComponent;

import javax.inject.Inject;

public class HelloActivity extends AppCompatActivity {

    @Inject
    Test3 test3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHelloActivityComponent.builder().build().inject(this);
    }
}
