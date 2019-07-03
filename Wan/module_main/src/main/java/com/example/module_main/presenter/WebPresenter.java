package com.example.module_main.presenter;

import com.example.common_base.mvp.BasePresenter;
import com.example.module_main.contract.WebContract;

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {

    @Override
    public void addArticleFavorite(int id, String title, String author, String link) {

    }
}
