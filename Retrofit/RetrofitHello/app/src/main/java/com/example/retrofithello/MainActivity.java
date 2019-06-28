package com.example.retrofithello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.retrofithello.api.Api;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // 创建Retrofit 对象
        Retrofit retrofit = new Retrofit.Builder()
                // 设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                // 结合RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                // 设置网络请求的url地址
                .baseUrl("http://apis.baidu.com/txapi/")
                .build();

        // 创建网络接口实例
        api = retrofit.create(Api.class);
    }
}
