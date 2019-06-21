package com.yhjia.rxjava_helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 这里是一个HelloWorld
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hello World
        findViewById(R.id.btn_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handlerClick();
//                handlerClick2();
                handleJust();
            }
        });
        // create 操作符 作用创建一个被观察者
        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerCreate();
            }
        });
    }

    /**
     * 链式编程
     */
    private void handlerClick2() {
        // create() 创建一个被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 这里是被观察者
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG,"===============subscribe");
                Log.d(TAG,"===============current Thread name:" + Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"===============onSubscribe");
            }
            // 发送事件时会执行
            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"===============onNext" + integer);
            }

            // 发生错误时会执行
            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"===============onError");
            }
            // 完成时会执行
            @Override
            public void onComplete() {
                Log.d(TAG,"===============onComplete");
            }
        });
    }

    /**
     * 一般写法 也可以 链式编程
     */
    private void handlerClick() {
        // 基于观察者模式  分为三步  数据发生变化更新界面ß
        // 1.  创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                // 执行顺序：2  主线程

                Log.d(TAG,"============currentThread name:" + Thread.currentThread().getName());
                e.onNext(1);
                Log.d(TAG,"==================11111");
                e.onNext(2);
                Log.d(TAG,"==================222");
                e.onNext(3);
                Log.d(TAG,"==================333");
                e.onComplete();
            }
        });
        // 2.  创建观察者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"==================onSubscribe");
                // 执行顺序：1
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"==================onNext:" + integer);
                // 执行顺序：3  执行三次 由于三个onNext()
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"==================onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"==================onComplete");
                // 执行顺序：4
            }
        };
        // 3.  订阅
        observable.subscribe(observer);
    }

    /**
     * 演示create操作符
     */
    private void handlerCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello Observer");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe=====");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext=====" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError=====");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete=====");
            }
        });
    }

    /**
     * just 操作符
     */
    private void handleJust() {
        // just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9, T item10) {
        // 底层调用的也是  fromArray
        Observable.just(1,2,3,4,5,6,7,8,9)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"============onSubscribe");
                        // 执行顺序： 1
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"============onNext=" + integer);
                        // 执行顺序： 2
                        // 执行顺序： 3
                        // 执行顺序： 4
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"============onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"============onComplete");
                        // 执行顺序： 5
                    }
                });
    }
}
