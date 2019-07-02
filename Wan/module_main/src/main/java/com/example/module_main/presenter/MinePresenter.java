package com.example.module_main.presenter;

import com.example.common_base.mvp.BasePresenter;
import com.example.module_main.contract.MineContract;

public class MinePresenter extends BasePresenter<MineContract.Presenter> implements MineContract.View{

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
