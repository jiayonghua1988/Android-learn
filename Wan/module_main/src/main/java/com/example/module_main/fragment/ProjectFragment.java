package com.example.module_main.fragment;

import android.view.View;

import com.example.common_base.base.BaseMVPFragment;
import com.example.module_main.bean.ProjectPageItem;
import com.example.module_main.contract.ProjectContract;
import com.example.module_main.presenter.ProjectPresenter;

import java.util.List;

public class ProjectFragment extends BaseMVPFragment<ProjectPresenter> {
    @Override
    protected ProjectPresenter createPresenter() {
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
