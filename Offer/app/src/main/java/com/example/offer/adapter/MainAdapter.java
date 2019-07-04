package com.example.offer.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.offer.R;
import com.example.offer.bean.Question;

import java.util.List;

public class MainAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {
    public MainAdapter(int layoutResId, @Nullable List<Question> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Question item) {
        helper.setText(R.id.question,"问题" + item.getId() +":" + item.getQuestion());
    }
}
