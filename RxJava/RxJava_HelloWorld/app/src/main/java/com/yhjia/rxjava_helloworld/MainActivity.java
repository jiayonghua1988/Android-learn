package com.yhjia.rxjava_helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 这里是一个HelloWorld
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Integer i = 100;

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
//                handleJust();
//                fromArray();
//                handlerFromCallable();
//                handlerFromCallable();
//                handlerFromFuture();
//                handlerFromIterable();
//                handlerDefer();
//                handlerTimer();
//                handlerInterval();
//                handlerIntervalRange();
//                handlerRange();
//                handlerRangeLong();
//                handlerEmpty();
//                handlerNever();
//                handlerError();
//                handlerMap();
                handleMap2();
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

    /**
     * 创建被观察者 并发送事件  和just类似 但是可以发送超过10个 可以传入一个数组
     */
    private void fromArray() {
        Integer [] arrays = {1,2,3,4,5,6,7,8,9,10,11};
        Observable.fromArray(arrays)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.....");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.....=" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError.......");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    private void logPrint(String msg) {
        Log.d(TAG,msg);
    }


    /**
     * 这里的 Callable 是 java.util.concurrent 中的
     * Callable，Callable 和 Runnable 的用法基本一致，
     * 只是它会返回一个结果值，这个结果值就是发给观察者的。
     * 异步请求
     */
    private void handlerFromCallable() {
        logPrint("11111111111");
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                logPrint("call.............");
                Thread.sleep(10 * 1000);
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                logPrint("accept......=" + integer);
            }
        });
        logPrint("22222222");
    }

    /**
     * fromFuture
     */
    private void handlerFromFuture() {
        Observable.fromFuture(new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                logPrint("call.......");
                return "返回结果";
            }
        }))
                // 只有订阅时才会发送事件
                .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                logPrint("doOnSubscribe  accept........");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                logPrint("subscribe.....accept...=" + s);
            }
        });
    }

    /**
     * fromIterable 直接发送一个 List 集合数据给观察者
     */
    private void handlerFromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.fromIterable(list)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("integer:" + integer);
                    }
                });
    }


    /**
     * defer()
     * 这个方法的作用就是直到被观察者被订阅后才会创建被观察者
     * 因为 defer() 只有观察者订阅的时候才会创建新的被观察者，所以每订阅一次就会打印一次，并且都是打印 i 最新的值。
     */
    private void handlerDefer() {
        i = 100;
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i = 200;
       Observer<Integer> observer = new Observer<Integer>() {
           @Override
           public void onSubscribe(Disposable d) {

           }

           @Override
           public void onNext(Integer integer) {
                logPrint("onNext..............=" + integer);
           }

           @Override
           public void onError(Throwable e) {

           }

           @Override
           public void onComplete() {

           }
       };
        observable.subscribe(observer);
        i = 300;
        observable.subscribe(observer);
    }

    /**
     * timer();
     * 当到指定时间后就会发送一个 0L 的值给观察者
     * 定时任务
     */
    private void handlerTimer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        logPrint("onNext: === " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * interval()
     * 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字  不限制的重复执行下去
     */
    private void handlerInterval() {
        Observable.interval(4,TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("=========onSubscribe");
                        // 执行订阅 就会执行
                    }

                    @Override
                    public void onNext(Long aLong) {
                        logPrint("========onNext=" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 可以指定发送事件的开始值和数量，其他与 interval() 的功能一样
     */
    private void handlerIntervalRange() {
        Observable.intervalRange(0,5,0,2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.................");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        logPrint("onNext.............aLong=" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError......................");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete...............");
                    }
                });
    }

    /**
     * range()
     * 同时发送一定范围的事件序列
     * 特点：同时   ，一定范围
     */
    private void handlerRange() {
        Observable.range(2,5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe................");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext..........integer=" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError.............");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete...............");
                    }
                });
    }

    /**
     * 同时发送一定范围的事件序列 long
     * 用法同range() int
     * rangeLong() long
     */
    private void handlerRangeLong() {
        Observable.rangeLong(2,5)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.........");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        logPrint("onNext......=" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError.........");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     * empty()  直接发送onComplete()事件
     */
    private void handlerEmpty() {
        Observable.empty()
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe......");
                    }

                    @Override
                    public void onNext(Object o) {
                        logPrint("onNext......");
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError......");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     * never(): 不发送任何事件
     */
    private void handlerNever() {
        Observable.never()
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe......");
                    }

                    @Override
                    public void onNext(Object o) {
                        logPrint("onNext......");
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError......");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     * error():发送onError()事件
     */
    private void handlerError() {
       Observable.error(new Throwable(){})
               .subscribe(new Observer<Object>() {
                   @Override
                   public void onSubscribe(Disposable d) {
                       logPrint("onSubscribe......");
                   }

                   @Override
                   public void onNext(Object o) {
                       logPrint("onNext......");
                   }

                   @Override
                   public void onError(Throwable e) {
                       logPrint("onError......");
                   }

                   @Override
                   public void onComplete() {
                       logPrint("onComplete......");
                   }
               });
    }

    /**
     * map可以将被观察者发送的数据类型转变成其他的类型
     * 将 integer 转成String
     */
    private void handlerMap() {
        Observable.just(1,2,3)
                .map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "I'm " + integer;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.......");
                    }

                    @Override
                    public void onNext(String s) {
                        logPrint("onNext.....=" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError.....");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.......");
                    }
                });
    }

    private void handleMap2() {
        Observable.just("1","2","3")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.parseInt(s);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("integer=" + integer);
                        logPrint("integer=" + integer.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
