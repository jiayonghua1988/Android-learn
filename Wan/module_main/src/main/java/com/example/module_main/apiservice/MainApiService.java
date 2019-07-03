package com.example.module_main.apiservice;

import com.example.common_base.base.BaseResponse;
import com.example.module_main.bean.BannerResult;
import com.example.module_main.bean.HomeArticleResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainApiService {
    /**
     * 获取首页 banner 数据
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerResult>>> getBanner();

    @GET("/article/list/{page}/json")
    Observable<BaseResponse<HomeArticleResult>> getHomeArticles(@Path("page") int page);

}
