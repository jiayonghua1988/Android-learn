package com.example.module_main.apiservice;

import com.example.common_base.base.BaseResponse;
import com.example.common_base.mvp.BasePresenter;
import com.example.module_main.bean.BannerResult;
import com.example.module_main.bean.HomeArticleResult;
import com.example.module_main.bean.WeChatArticleResult;
import com.example.module_main.bean.WeChatAuthorResult;

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

    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<WeChatAuthorResult>>> getWeChatAuthors();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<BaseResponse<WeChatArticleResult>> getWeChatArticles(@Path("id") String id,@Path("page") int page);

}
