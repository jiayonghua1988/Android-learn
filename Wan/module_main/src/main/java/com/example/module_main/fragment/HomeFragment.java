package com.example.module_main.fragment;

import android.view.View;

import com.example.common_base.base.BaseMVPFragment;
import com.example.module_main.contract.HomeContract;
import com.example.module_main.presenter.HomePresenter;

public class HomeFragment extends BaseMVPFragment<HomePresenter>{

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
