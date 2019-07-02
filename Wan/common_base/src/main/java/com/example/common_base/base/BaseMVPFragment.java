package com.example.common_base.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;

public abstract class BaseMVPFragment<P extends IPresenter> extends BaseFragment implements IView {
    protected Context mContext;
    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    protected abstract P createPresenter();


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

}
