package com.example.dagger2_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.dagger2_demo.app.DemoApplication;
import com.example.dagger2_demo.bean.Test;
import com.example.dagger2_demo.di.component.DaggerApplicationComponent;
import com.example.dagger2_demo.di.module.AndroidModule;

import javax.inject.Inject;

/**
 * @Component 连接器
 * 用来连接提供方和使用方 是桥梁
 *
 */
public class MainActivity extends AppCompatActivity {
    // 加入注解 标注这个test3是需要注入的
    @Inject
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 使用组件进行构造注入


        DaggerApplicationComponent
                .builder()
                .androidModule(new AndroidModule((DemoApplication) getApplication()))

                .build().inject(this);

        Log.d("Test","test=" + test);

    }
}
