package com.example.module_main.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module_main.R;
import com.example.module_main.bean.WeChatArticleResult;

import java.util.List;

public class WeChatArticleAdapter extends BaseQuickAdapter<WeChatArticleResult.DatasBean, BaseViewHolder> {

    public WeChatArticleAdapter(int layoutResId, @Nullable List<WeChatArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeChatArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_time, item.getNiceDate());
    }
}
