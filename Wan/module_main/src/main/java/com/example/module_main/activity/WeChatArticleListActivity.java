package com.example.module_main.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common_base.base.BaseMVPActivity;
import com.example.common_base.constants.Constants;
import com.example.common_base.widget.LinearItemDecoration;
import com.example.module_main.R;
import com.example.module_main.adapter.WeChatArticleAdapter;
import com.example.module_main.bean.WeChatArticleResult;
import com.example.module_main.bean.WeChatAuthorResult;
import com.example.module_main.contract.WeChatArticleListContract;
import com.example.module_main.presenter.WeChatArticlePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

@Route(path = "/wechat/WeChatArticleListActivity")
public class WeChatArticleListActivity extends BaseMVPActivity<WeChatArticlePresenter> implements WeChatArticleListContract.View {

    private RecyclerView recycler;
    private SmartRefreshLayout srl_wechat;
    private Toolbar toolbar;
    private WeChatArticleAdapter weChatArticleAdapter;
    private int id;
    private int page;
    @Override
    protected WeChatArticlePresenter createPresenter() {
        return new WeChatArticlePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wechat_article_list;
    }

    @Override
    protected void initView() {
        recycler = findViewById(R.id.recycler);
        srl_wechat = findViewById(R.id.srl_wechat);
        toolbar = findViewById(R.id.toolbar);
        srl_wechat = findViewById(R.id.srl_wechat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(linearLayoutManager);

        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext);
        itemDecoration.height(1f)
                .margin(10,10)
                .color(Color.parseColor("#66dddddd"));
        recycler.addItemDecoration(itemDecoration);


    }

    @Override
    protected void initData() {
        super.initData();
        srl_wechat.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                presenter.getWeChatArticle(id, page);
            }
        });

        srl_wechat.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                presenter.getWeChatArticle(id, page);
            }
        });

        weChatArticleAdapter = new WeChatArticleAdapter(R.layout.tv_article_title,new ArrayList<>());
        weChatArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WeChatArticleResult.DatasBean datasBean = weChatArticleAdapter.getItem(position);
                gotoWebViewActivity(datasBean);
            }
        });
        recycler.setAdapter(weChatArticleAdapter);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            WeChatAuthorResult weChatAuthorResult = (WeChatAuthorResult) bundle.getSerializable("WeChatAuthorResult");
            id = weChatAuthorResult.getId();
            String name = weChatAuthorResult.getName();
            getSupportActionBar().setTitle(name);
            presenter.getWeChatArticle(id, page);
        }
    }

    @Override
    public void onWeChatArticleList(WeChatArticleResult result) {
        if (result != null && result.getDatas() != null && result.getDatas().size() >0) {
            if (page == 0){
                srl_wechat.finishRefresh();
                weChatArticleAdapter.setNewData(result.getDatas());
            } else {
                srl_wechat.finishLoadMore();
                weChatArticleAdapter.addData(result.getDatas());
            }

        }
    }

    /**
     * 跳转至 webviewactivity
     *
     * @param datasBean
     */
    private void gotoWebViewActivity(WeChatArticleResult.DatasBean datasBean) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, datasBean.getTitle());
        bundle.putString(Constants.AUTHOR, datasBean.getAuthor());
        bundle.putInt(Constants.ID, datasBean.getId());
        bundle.putString(Constants.URL, datasBean.getLink());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }
}
