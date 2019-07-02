package com.example.common_base.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import org.aspectj.lang.annotation.Around;

public class BaseApplication extends Application {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initRouter(this);
    }

    /**
     * 初始化ARouter
     */
    private void initRouter(BaseApplication baseApplication) {
        if (isDebug()) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openDebug();  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openLog();  // 打印日志
        }
        ARouter.init(baseApplication);
    }

    private boolean isDebug() {
        return true;
    }

    public static BaseApplication getApplication() {
        return application;
    }
}
