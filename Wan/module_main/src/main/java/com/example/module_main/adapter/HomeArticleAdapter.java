package com.example.module_main.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module_main.R;
import com.example.module_main.bean.HomeArticleResult;

import java.util.List;

public class HomeArticleAdapter extends BaseQuickAdapter<HomeArticleResult.DatasBean, BaseViewHolder>{

    public HomeArticleAdapter(int layoutResId) {
        super(layoutResId);
    }


    public HomeArticleAdapter(int layoutResId, @Nullable List<HomeArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title,item.getTitle())
                .setText(R.id.tv_article_author,item.getAuthor())
                .setText(R.id.tv_article_time,item.getNiceDate());
    }

}
