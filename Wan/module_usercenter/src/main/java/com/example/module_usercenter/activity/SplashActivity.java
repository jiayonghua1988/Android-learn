package com.example.module_usercenter.activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_base.base.BaseActivity;
import com.example.module_usercenter.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable.timer(2,TimeUnit.SECONDS)
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        gotoMainActivity();
                    }
                });

    }

    private void gotoMainActivity() {
        ARouter.getInstance().build("/main/main").navigation();
    }
}
