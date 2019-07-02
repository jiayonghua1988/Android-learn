package com.example.module_main.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_base.annotation.BindEventBus;
import com.example.common_base.base.BaseActivity;
import com.example.module_main.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = "/main/_MainActivity")
public class _MainActivity extends BaseActivity {

    private Unbinder unbinder;


    @BindView(R.id.rg_radio_group)

//    @BindView(R.id.rg_radio_group) RadioGroup radioGroup;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
            unbinder = null;
        }
    }
}
