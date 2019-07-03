package com.example.module_main.contract;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;
import com.example.module_main.bean.BannerResult;
import com.example.module_main.bean.HomeArticleResult;
import com.example.module_main.bean.WeChatAuthorResult;

import java.util.List;

public interface HomeContract {

    interface  View extends IView {
        /**
         * banner 数据回调
         */
        void onBanner(List<BannerResult> bannerResults);

        /**
         * 公众号数据回调
         */
        void onWeChatAuthors(List<WeChatAuthorResult> weChatAuthorResults);


        /**
         * 首页文章列表数据回调
         *
         * @param result
         */
        void onHomeArticles(HomeArticleResult result);

    }

    interface Presenter extends IPresenter<View> {
        /**
         * 获取 banner 数据
         */
        void getBanner();

        /**
         * 获取公众号列表
         */
        void getWeChatAuthors();

        /**
         * 获取首页文章列表
         */
        void getHomeArticles(int page);
    }
}
