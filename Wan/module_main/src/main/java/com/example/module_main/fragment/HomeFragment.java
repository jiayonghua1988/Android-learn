package com.example.module_main.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_base.base.BaseMVPFragment;
import com.example.common_base.constants.Constants;
import com.example.common_base.util.StatusBarUtil;
import com.example.common_base.widget.LinearItemDecoration;
import com.example.common_base.widget.gridviewpager.GridViewPager;
import com.example.module_main.R;
import com.example.module_main.activity._MainActivity;
import com.example.module_main.adapter.HomeArticleAdapter;
import com.example.module_main.bean.BannerResult;
import com.example.module_main.bean.HomeArticleResult;
import com.example.module_main.contract.HomeContract;
import com.example.module_main.imageloader.GlideImageLoader;
import com.example.module_main.presenter.HomePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private int[] colors = {0xffec407a, 0xffab47bc, 0xff29b6f6,
            0xff7e57c2, 0xffe24073, 0xffee8360, 0xff26a69a,
            0xffef5350, 0xff2baf2b, 0xffffa726};
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private View searchLayoutView;
    private TextView loginTxtView;
    private TextView searchTxtView;
    private ImageView logoImgView;
    private View headerView;
    private Banner banner;
    private GridViewPager gridViewPager;
    private LinearLayoutManager linearLayoutManager;
    private int page =0;
    private int searchLayoutHeight;
    private int bannerHeight;
    private HomeArticleAdapter homeArticleAdapter;


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        mContext = getActivity();
        refreshLayout = rootView.findViewById(R.id.srl_home);
        logoImgView = rootView.findViewById(R.id.iv_home_logo);
        recyclerView = rootView.findViewById(R.id.rv_home);
        searchLayoutView = rootView.findViewById(R.id.rl_search_header);
        loginTxtView =  rootView.findViewById(R.id.tv_home_login);
        searchTxtView =  rootView.findViewById(R.id.tv_home_search);


        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        banner =  headerView.findViewById(R.id.banner_home);
        gridViewPager =  headerView.findViewById(R.id.gvp_viewpager);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration linearItemDecoration = new LinearItemDecoration(mContext);
        linearItemDecoration.height(8f).color(Color.parseColor("#66dddddd"));
        recyclerView.addItemDecoration(linearItemDecoration);
        setListener();
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        // 请求 banner 数据
        presenter.getBanner();
        // 请求首页文章列表
        presenter.getHomeArticles(page);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void setListener() {
        loginTxtView.setOnClickListener(this);
        searchTxtView.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                setSearchLayoutAlpha(scrollOffset);
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                page = 0;
                presenter.getHomeArticles(page);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                presenter.getHomeArticles(page);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    private void setSearchLayoutAlpha(int offset) {
        if (searchLayoutHeight == 0) {
            searchLayoutHeight = searchLayoutView.getMeasuredHeight();
        }
        if (bannerHeight == 0) {
            bannerHeight = banner.getMeasuredHeight();
        }
        int maxOffset = bannerHeight - StatusBarUtil.getStatusBarHeight(mContext) - searchLayoutHeight;
        Log.e(TAG, "offset: " + offset + ", maxOffset = " + maxOffset);
        if (offset <= maxOffset) {
            float percent = offset * 1.0f / maxOffset;
            searchLayoutView.getBackground().mutate().setAlpha((int) (255 * percent));
            loginTxtView.setTextColor(getResources().getColor(android.R.color.white));
            logoImgView.setImageResource(R.drawable.ic_home_logo_white);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input));
            ((_MainActivity) getActivity()).setStatusBarTranslucent((int) (255 * percent));
//            ((MainActivity) getActivity()).setStatusBarTextColorBlack();


        } else {
            loginTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
            logoImgView.setImageResource(R.drawable.ic_home_logo_black);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input_dark));
//            ((MainActivity) getActivity()).setStatusBarWhite();
        }
    }

    @Override
    public void onBanner(List<BannerResult> bannerResults) {
        if (bannerResults == null || bannerResults.size() == 0){
            return;
        }
        banner.setImages(getImages(bannerResults));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerResult bannerResult = bannerResults.get(position);
                gotoWebViewActivityFromBanner(bannerResult);
            }
        });
        banner.start();

    }

    @Override
    public void onHomeArticles(HomeArticleResult result) {
        page++;
        if (result != null) {
            List<HomeArticleResult.DatasBean> datas = result.getDatas();
            if (datas != null && datas.size() > 0) {
                if (homeArticleAdapter == null) {
                    homeArticleAdapter = new HomeArticleAdapter(R.layout.item_home_article,datas);
                    homeArticleAdapter.addHeaderView(headerView);
                    recyclerView.setAdapter(homeArticleAdapter);
                } else {
                    if (page == 0){
                        homeArticleAdapter.setNewData(datas);
                    } else {
                        homeArticleAdapter.addData(datas);
                    }
                }



            }
        }
    }

    private List<String> getImages(List<BannerResult> bannerResults) {
        List<String> list = new ArrayList<>();
        if (bannerResults != null) {
            for (BannerResult bannerResult : bannerResults) {
                list.add(bannerResult.getImagePath());
            }
        }
        return list;
    }

    private void gotoWebViewActivityFromBanner(BannerResult bannerResult) {
        if (bannerResult == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, bannerResult.getUrl());
        bundle.putInt(Constants.ID, bannerResult.getId());
        bundle.putString(Constants.AUTHOR, null);
        bundle.putString(Constants.TITLE, bannerResult.getTitle());

        ARouter.getInstance().build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);

    }

    @Override
    public void onClick(View view) {

    }
}
