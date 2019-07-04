package com.example.module_main.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.common_base.base.BaseMVPFragment;
import com.example.module_main.R;
import com.example.module_main.bean.ProjectPageItem;
import com.example.module_main.contract.ProjectContract;
import com.example.module_main.presenter.ProjectPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ProjectFragment extends BaseMVPFragment<ProjectPresenter> {

    private View mFakeStatusBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View rootView) {
        mFakeStatusBar = rootView.findViewById(R.id.fake_status_bar);
        tabLayout = rootView.findViewById(R.id.tl_project);
        viewPager = rootView.findViewById(R.id.vp_project_page);
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
