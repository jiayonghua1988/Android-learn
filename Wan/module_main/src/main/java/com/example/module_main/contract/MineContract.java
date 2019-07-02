package com.example.module_main.contract;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;

public interface MineContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View>, IView {

    }
}
