package com.example.module_main.fragment;

import android.view.View;

import com.example.common_base.base.BaseMVPFragment;
import com.example.module_main.contract.MineContract;
import com.example.module_main.presenter.MinePresenter;

public class MineFragment extends BaseMVPFragment<MinePresenter>  {
    @Override
    protected MinePresenter createPresenter() {
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
