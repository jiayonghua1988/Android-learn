package com.example.module_main.fragment;

import android.view.View;

import com.example.common_base.base.BaseMVPFragment;
import com.example.module_main.presenter.SystemPresenter;

public class SystemFragment extends BaseMVPFragment<SystemPresenter> {
    @Override
    protected SystemPresenter createPresenter() {
        return null;
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
