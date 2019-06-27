package com.example.daggerdemo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.daggerdemo3.di.bean.Chef;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Chef chef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
