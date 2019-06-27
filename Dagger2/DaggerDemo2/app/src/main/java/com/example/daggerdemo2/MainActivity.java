package com.example.daggerdemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.daggerdemo2.app.DemoApplication;
import com.example.daggerdemo2.di.bean.Test;
import com.example.daggerdemo2.di.bean.component.AppComponent;
import com.example.daggerdemo2.di.bean.component.DaggerAppComponent;
import com.example.daggerdemo2.di.bean.component.DaggerMainActivityComponent;
import com.example.daggerdemo2.di.bean.module.AppModule;
import com.example.daggerdemo2.di.bean.module.MainActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DaggerMainActivityComponent.builder()
//
//                .mainActivityModule(new MainActivityModule((DemoApplication) getApplication()))
//                .build().inject(this);

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .mainActivityModule(new MainActivityModule((DemoApplication) getApplication()))
                .build()
                .inject(this);

        Log.d("Test","test=" + test);
    }
}
