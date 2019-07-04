package com.example.module_main.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common_base.base.BaseMVPFragment;
import com.example.common_base.constants.Constants;
import com.example.common_base.util.StatusBarUtil;
import com.example.common_base.widget.LinearItemDecoration;
import com.example.common_base.widget.gridviewpager.GridRecyclerAdapter;
import com.example.common_base.widget.gridviewpager.GridViewPager;
import com.example.common_base.widget.gridviewpager.GridViewPagerAdapter;
import com.example.module_main.R;
import com.example.module_main.activity._MainActivity;
import com.example.module_main.adapter.HomeArticleAdapter;
import com.example.module_main.bean.BannerResult;
import com.example.module_main.bean.HomeArticleResult;
import com.example.module_main.bean.WeChatAuthorResult;
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
    private List<BannerResult> bannerResults;


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

        homeArticleAdapter = new HomeArticleAdapter(R.layout.item_home_article,new ArrayList<>());

        homeArticleAdapter.addHeaderView(headerView);
        recyclerView.setAdapter(homeArticleAdapter);

        homeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gotoWebViewActivity((HomeArticleResult.DatasBean)adapter.getItem(position));
            }
        });

        setListener();
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        // 请求 banner 数据
        presenter.getBanner();
        // 请求首页文章列表
        presenter.getHomeArticles(page);
        // 请求微信公众号列表
        presenter.getWeChatAuthors();

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
                presenter.getWeChatAuthors();
                presenter.getBanner();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                presenter.getHomeArticles(page);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });


        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("Test","banner:" +position);
                if (bannerResults != null && bannerResults.size() > position) {
                    BannerResult bannerResult = bannerResults.get(position);
                    gotoWebViewActivityFromBanner(bannerResult);
                }
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
        this.bannerResults = bannerResults;
        banner.setImages(getImages(bannerResults));
        banner.start();

    }

    @Override
    public void onWeChatAuthors(List<WeChatAuthorResult> weChatAuthorResults) {
        if (weChatAuthorResults == null) {
            return;
        }
        gridViewPager.setOnGridItemClickListener(new GridViewPager.OnGridItemClickListener() {
            @Override
            public void onGridItemClick(int position, View view) {
                gotoWeChatArticleListActivity(weChatAuthorResults.get(position));
            }
        });

        gridViewPager.setAdapter(new GridViewPagerAdapter<WeChatAuthorResult>(weChatAuthorResults) {

            @Override
            public void bindData(GridRecyclerAdapter.ViewHolder viewHolder, WeChatAuthorResult weChatAuthorResult, int position) {
                ShapeDrawable shapeDrawable = new ShapeDrawable();
                shapeDrawable.setShape(new OvalShape());
                shapeDrawable.getPaint().setColor(colors[position % colors.length]);
                viewHolder.setText(R.id.tv_home_author_icon, String.valueOf(weChatAuthorResult.getName().charAt(0)))
                        .setText(R.id.tv_home_author_name, weChatAuthorResult.getName())
                        .setBackground(R.id.tv_home_author_icon, shapeDrawable);
            }
        });
    }

    @Override
    public void onHomeArticles(HomeArticleResult result) {
        page++;
        if (result != null) {
            List<HomeArticleResult.DatasBean> datas = result.getDatas();
            if (datas != null && datas.size() > 0) {
                    if (page == 0){
                        homeArticleAdapter.setNewData(datas);
                    } else {
                        homeArticleAdapter.addData(datas);
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

    /**
     * 跳转至微信公众号文章列表页面
     *
     * @param weChatAuthorResult
     */
    private void gotoWeChatArticleListActivity(WeChatAuthorResult weChatAuthorResult) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("WeChatAuthorResult", weChatAuthorResult);
        ARouter.getInstance()
                .build("/wechat/WeChatArticleListActivity")
                .withBundle("bundle", bundle)
                .navigation();
    }

    private void gotoWebViewActivity(HomeArticleResult.DatasBean datasBean) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, datasBean.getLink());
        bundle.putInt(Constants.ID, datasBean.getId());
        bundle.putString(Constants.AUTHOR, datasBean.getAuthor());
        bundle.putString(Constants.TITLE, datasBean.getTitle());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }
}
