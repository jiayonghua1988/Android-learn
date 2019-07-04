package com.example.module_main.contract;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;
import com.example.module_main.bean.WeChatArticleResult;

import java.util.List;

public interface WeChatArticleListContract {
    interface View extends IView {
        void onWeChatArticleList(WeChatArticleResult result);
    }

    interface Presenter extends IPresenter<View> {
        void getWeChatArticle(int id, int page);
    }
}
