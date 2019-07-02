package com.example.module_main.contract;

import com.example.common_base.mvp.IPresenter;
import com.example.common_base.mvp.IView;
import com.example.module_main.bean.ProjectPageItem;

import java.util.List;

public interface ProjectContract {

    interface View extends IView {
        void onProjectTabs(List<ProjectPageItem> projectPageItemList);
    }

    interface  Presenter extends IPresenter<View> {
        void getProjectTabs();
    }
}
