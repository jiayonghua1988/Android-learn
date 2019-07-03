package com.example.module_main.presenter;

import com.example.common_base.base.BaseObserver;
import com.example.common_base.mvp.BasePresenter;
import com.example.module_main.apiservice.MainApiService;
import com.example.module_main.bean.BannerResult;
import com.example.module_main.bean.HomeArticleResult;
import com.example.module_main.bean.WeChatAuthorResult;
import com.example.module_main.contract.HomeContract;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    @Override
    public void getBanner() {
        addSubscribe(create(MainApiService.class).getBanner(), new BaseObserver<List<BannerResult>>(getView()) {
            @Override
            protected void onSuccess(List<BannerResult> data) {
                if (isViewAttached()) {
                    getView().onBanner(data);
                }
            }
        });
    }

    @Override
    public void getWeChatAuthors() {
        addSubscribe(create(MainApiService.class).getWeChatAuthors(), new BaseObserver<List<WeChatAuthorResult>>(getView()) {

            @Override
            protected void onSuccess(List<WeChatAuthorResult> data) {
                if (isViewAttached()) {
                    getView().onWeChatAuthors(data);
                }
            }
        });
    }

    @Override
    public void getHomeArticles(int page) {
        addSubscribe(create(MainApiService.class).getHomeArticles(page), new BaseObserver<HomeArticleResult>(getView()) {

            @Override
            protected void onSuccess(HomeArticleResult data) {
                if (isViewAttached()) {
                    getView().onHomeArticles(data);
                }
            }
        });
    }


}
