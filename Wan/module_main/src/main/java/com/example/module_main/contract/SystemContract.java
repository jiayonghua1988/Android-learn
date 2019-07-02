package com.example.module_main.contract;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;

/**
 * 体系 contract
 */
public interface SystemContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
        
    }
}
