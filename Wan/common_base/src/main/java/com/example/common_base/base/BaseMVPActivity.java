package com.example.common_base.base;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * mvp 架构
 */
public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity implements IView {

    protected P presenter;
    private Unbinder unbinder;

    @Override
    protected void initData() {
        super.initData();
        presenter = createPresenter();
        // presenter 绑定 view
        if (presenter != null) {
            presenter.attachView(this);
        }
        unbinder = ButterKnife.bind(this);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity 销毁时取消所有的订阅
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        // 取消ButterKnife
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
