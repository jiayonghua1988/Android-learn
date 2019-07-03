package com.example.module_main.contract;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;

public interface WebContract {

    interface View extends IView {
        /**
         * 添加收藏成功回调
         */
        void onFavoriteAdded();

        /**
         * 删除收藏成功回调
         */
        void onFavoriteDeleted();
    }

    interface Presenter extends IPresenter<View> {
        /**
         * 添加收藏
         *
         * @param id
         * @param title
         * @param author
         * @param link
         */
        void addArticleFavorite(int id, String title, String author, String link);
    }
}
