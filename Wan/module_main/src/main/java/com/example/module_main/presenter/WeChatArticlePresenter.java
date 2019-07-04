package com.example.module_main.presenter;

import com.example.common_base.base.BaseObserver;
import com.example.common_base.mvp.BasePresenter;
import com.example.module_main.apiservice.MainApiService;
import com.example.module_main.bean.WeChatArticleResult;
import com.example.module_main.contract.WeChatArticleListContract;

import java.util.List;

public class WeChatArticlePresenter extends BasePresenter<WeChatArticleListContract.View> implements WeChatArticleListContract.Presenter {
    @Override
    public void getWeChatArticle(int id, int page) {
        addSubscribe(create(MainApiService.class).getWeChatArticles(id + "", page), new BaseObserver<WeChatArticleResult>(getView()) {

            @Override
            protected void onSuccess(WeChatArticleResult data) {
                if (isViewAttached()) {
                    getView().onWeChatArticleList(data);
                }
            }
        });
    }
}
