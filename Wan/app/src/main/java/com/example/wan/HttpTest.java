package com.example.wan;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpTest {


    /**
     * 网络请求的流程
     * 1.客户端和服务器端建立连接 OkHttpClient
     * 2.客户端向服务器端发送请求
     * 3.服务器端处理请求返回响应到客户端
     * 4.客户端关闭连接
     */
    private void httpData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
