package com.example.module_main.contract;

import com.example.common_base.mvp.IView;
import com.example.module_main.bean.ProjectResult;

public interface ProjectPageContract {

    interface View extends IView {
        void onProjectList(ProjectResult projectResult);
    }

    interface Presenter {
        void getProjects(int id, int page);
    }
}
