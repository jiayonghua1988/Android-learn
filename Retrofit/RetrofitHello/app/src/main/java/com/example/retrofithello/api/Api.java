package com.example.retrofithello.api;

import com.example.retrofithello.bean.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    /**
     * @Get 注解的作用：采用Get方法发送网络请求
     * getNews(...)= 接收网络请求数据的方法
     * 其中返回类型是Call<News> News 是接收数据的类
     * 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
     * @param num
     * @param page
     * @return
     */
    @Headers("apikey:81bf9da930c7f9825a3c3383f1d8d766")
    @GET("word/word")
    Call<News> getNews(@Query("num") String num,@Query("page") String page);

}
