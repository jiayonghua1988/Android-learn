package com.yhjia.rxjava_helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yhjia.rxjava_helloworld.bean.Person;
import com.yhjia.rxjava_helloworld.bean.Plain;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * 这里是一个HelloWorld
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private Integer i = 100;
    // 用于保存Subscription对象
    private Subscription mSubscription;

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
//                handleMap2();
//                handlerFlatMap1();
//                handlerFlatMap2();
//                handlerFlatMap3();
//                handlerBuffer();
//                handlerGroupBy();
//                handlerScan();
//                handlerWindow();
//                handlerConcat();
//                handlerConcatArray();
//                handlerMerge1();
//                handlerZip();
//                handlerCombineLatest();
//                handlerReduce();
//                handlerCollect();
//                handlerStartWith();
//                handlerCount();
//                handlerDelay();
//                handlerDoOneEach();
//                handlerDoOnNext();
//                handlerDoAfterNext();
//                handlerDoOnComplete();
//                handlerDoOnError();
//                handlerDoOnSubscribe();
//                handlerDoOnDispose();
//                handlerDoOnLifecycle();
//                handlerDoTerminate();
//                handlerDoFinally();
//                handlerOnErrorReturn();
//                handlerOnErrorResumeNext();
//                handlerOnExceptionResumeNext();
//                testPress();
//                handlerPress3();
//                handlerPress4();
//                handlerPress5();
//                handlerPress6();
//                handlerPress7();
//                handlerPress8();
//                handlerPress9();
//                handlerPress10();
//                handlerPress11();
//                handlerPress12();
//                handlerPress13();
                handlerPress14();
            }
        });
        // create 操作符 作用创建一个被观察者
        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerCreate();
            }
        });

        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSubscription.request(2);
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

    /**
     * 这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者。
     * flatMap()
     * 需求：要将Person集合中的每个元素中的Plan的action打印出来
     * 首先用map
     */
    private void handlerFlatMap1() {
        // 先使用fromInterable()
        List<Person> persons = new ArrayList<>();
        Plain plain = new Plain();
        plain.setContent("aaaa");
        plain.setActionList(Arrays.asList("a","b","c"));
        List<Plain> plains = new ArrayList<>();
        plains.add(plain);
        Plain plain2 = new Plain();
        plain2.setContent("bbbbb");
        plain2.setActionList(Arrays.asList("a2","b2","c2"));
        plains.add(plain2);
        Person person = new Person("aaaa",plains);
        persons.add(person);
        Observable.fromIterable(persons)
                .map(new Function<Person, List<Plain>>() {
                    @Override
                    public List<Plain> apply(Person person) throws Exception {
                        return person.getPlanList();
                    }
                })
                .subscribe(new Observer<List<Plain>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Plain> plains) {
                        for (Plain plain1 : plains) {
                            List<String> plainActionList = plain1.getActionList();
                            for (String action : plainActionList) {
                                logPrint("action:" + action);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handlerFlatMap2() {
        // 先使用fromInterable()
        List<Person> persons = new ArrayList<>();
        Plain plain = new Plain();
        plain.setContent("aaaa");
        plain.setActionList(Arrays.asList("a", "b", "c"));
        List<Plain> plains = new ArrayList<>();
        plains.add(plain);
        Plain plain2 = new Plain();
        plain2.setContent("bbbbb");
        plain2.setActionList(Arrays.asList("a2", "b2", "c2"));
        plains.add(plain2);
        Person person = new Person("aaaa", plains);
        persons.add(person);

        Observable.fromIterable(persons)
                .flatMap(new Function<Person, ObservableSource<Plain>>() {
                    @Override
                    public ObservableSource<Plain> apply(Person person) throws Exception {
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .flatMap(new Function<Plain, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Plain plain) throws Exception {
                        return Observable.fromIterable(plain.getActionList());
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                logPrint("onNext......:" + s);
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
     * 验证flatMap 无须
     */
    private void handlerFlatMap3() {
        List<Person> persons = new ArrayList<>();
        Plain plain = new Plain();
        plain.setContent("aaaa");
        plain.setActionList(Arrays.asList("a", "b", "c"));
        List<Plain> plains = new ArrayList<>();
        plains.add(plain);
        Plain plain2 = new Plain();
        plain2.setContent("bbbbb");
        plain2.setActionList(Arrays.asList("a2", "b2", "c2"));
        plains.add(plain2);
        Person person = new Person("aaaa", plains);
        persons.add(person);

        Observable.fromIterable(persons)
                .flatMap(new Function<Person, ObservableSource<Plain>>() {
                    @Override
                    public ObservableSource<Plain> apply(Person person) throws Exception {
                            if ("aaaa".equals(person.getName())) {
                               return Observable.fromIterable(person.getPlanList()).delay(10,TimeUnit.SECONDS);
                            }
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .subscribe(new Observer<Plain>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Plain plain) {
                        logPrint("name:" + plain.getContent());
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
     * concatMap 用法同 flatMap() 有序的
     */


    /**
     * 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出
     * buffer 有两个参数，一个是 count，另一个 skip。count 缓冲区元素的数量，skip 就代表缓冲区满了之后，
     * 发送下一次事件序列的时候要跳过多少元素。这样说可能还是有点抽象
     *
     * 从结果可以看出，每次发送事件，指针都会往后移动一个元素再取值，直到指针移动到没有元素的时候就会停止取值
     */
    private void handlerBuffer() {
        Observable.just(1,2,3,4,5)
                .buffer(2,1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        logPrint("========缓存区大小：" + integers.size());
                        for (Integer i :integers) {
                            logPrint("元素........=" +i);
                        }
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
     * groupBy():将发送的数据进行分组，每个分组都会返回一个被观察者
     * 在 groupBy() 方法返回的参数是分组的名字，每返回一个值，那就代表会创建一个组，以上的代码就是将1~10的数据分成3组
     */
    private void handlerGroupBy() {
        Observable.just(5,2,3,4,1,6,8,9,7,10)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer % 3;
                    }
                })
                .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("====================onSubscribe");
                    }

                    @Override
                    public void onNext(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                        logPrint("====================onNext");
                        integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "====================GroupedObservable onSubscribe ");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.d(TAG, "====================GroupedObservable onNext  groupName: " + integerIntegerGroupedObservable.getKey() + " value: " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                logPrint("====================GroupedObservable onError");
                            }

                            @Override
                            public void onComplete() {
                                logPrint("====================GroupedObservable onComplete ");
                            }
                        });
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
     * 将数据以一定的逻辑聚合起来
     * 1+2+3+4+5
     */
    private void handlerScan() {
        Observable.just(1,2,3,4,5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        logPrint("====================apply");
                        logPrint("====================integer " + integer);
                        logPrint("====================integer2 " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("====================accept=" + integer);
                    }
                });
    }

    /**
     * window():
     * 发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量，
     * 例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组
     */
    private void handlerWindow() {
        Observable.just(1,2,3,4,5)
                .window(2)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("=====================onSubscribe");
                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        integerObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                logPrint("=====================integerObservable onSubscribe");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                logPrint("=====================integerObservable onNext " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                logPrint("=====================integerObservable onError");
                            }

                            @Override
                            public void onComplete() {
                                logPrint("=====================integerObservable onComplete ");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("=====================onError ");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("=====================onComplete");
                    }
                });
    }

    /**
     * concat()
     * 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat() 最多只可以发送4个事件
     */
    private void handlerConcat() {
        Observable.concat(
                Observable.just(1,2),
                Observable.just(3,4),
                Observable.just(5,6),
                Observable.just(7,8))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("================onNext " + integer);
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
     * concatArray():
     * 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件
     * 与 concat() 作用一样，不过 concatArray() 可以发送多于 4 个被观察者
     * 串行合并
     */
    private void handlerConcatArray() {
        Observable.concatArray(
                Observable.just(1,2),
                Observable.just(3,4),
                Observable.just(5,6),
                Observable.just(7,8),
                Observable.just(9,10))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("================onNext " + integer);
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
     * 这个方法月 concat() 作用基本一样，知识 concat() 是串行发送事件，而 merge() 并行发送事件
     * 演示 concat() 和 merge() 的区别
     * mergeArray() 与 merge() 的作用是一样的，只是它可以发送4个以上的被观察者
     * 并行合并
     */
    private void handlerMerge1() {
        Observable.merge(
                Observable.interval(1,TimeUnit.SECONDS).map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return "A" + aLong;
                    }
                }),
                Observable.interval(1,TimeUnit.SECONDS).map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return "B" + aLong;
                    }
                })
        ).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                logPrint("=====================onNext" + s);
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
     * zip():
     * 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源 Observable 中最少事件的数量一样
     */
    private void handlerZip() {
        Observable.zip(
                Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
                                logPrint("===================A 发送的事件" + s1);
                                return s1;
                            }
                        }),
                Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
                                logPrint("===================B 发送的事件 " + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                }
        )
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("===================onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        logPrint("===================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("===================onError");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("===================onComplete");
                    }
                });
    }

    /**
     * combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，
     * 当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，这个事件就会和其他 Observable
     * 最近发送的事件结合起来发送
     *
     */
    private void handlerCombineLatest() {
        Observable.combineLatest(
                Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
                                logPrint("===================A 发送的事件  " + s1);
                                return s1;
                            }
                        }),
                Observable.intervalRange(1, 5, 2, 2, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {

                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
                                logPrint("===================B 发送的事件" + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("===================onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        logPrint("===================最终接收到的事件 " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("===================onError ");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("===================onComplete ");
                    }
                });
    }

    /**
     * 与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，这两个的区别在于 scan() 每处理一次数据就会将事件发送给观察者，而 reduce()
     * 会将所有数据聚合在一起才会发送事件给观察者。
     * 特点：所有数据聚合在一起发送
     */
    private void handlerReduce() {
        Observable.just(1,2,3,4,5)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        int res = integer + integer2;

                        logPrint("====================integer " + integer);
                        logPrint("====================integer2 " + integer2);
                        logPrint("====================res " + res);
                        return res;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("==================accept " + integer);
                    }
                });
    }

    /**
     * collect():
     * 将数据收集到数据结构当中
     */
    private void handlerCollect() {
        Observable.just(1,2,3,4)
                .collect(new Callable<ArrayList<Integer>>() {

                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                },
                new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                })
                .subscribe(new Consumer<ArrayList<Integer>>() {
                    @Override
                    public void accept(ArrayList<Integer> integers) throws Exception {
                        logPrint("===============accept" + integers);
                    }
                });

    }

    /**
     * startWith() & startWithArray():
     * 在发送事件之前追加事件，startWith() 追加一个事件，startWithArray() 可以追加多个事件。追加的事件会先发出
     */
    private void handlerStartWith() {
        Observable.just(5,6,7)
                .startWithArray(2,3,4)
                .startWith(1)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("accept.......=" + integer);
                    }
                });
    }

    /**
     * 返回被观察者发送事件的数量
     * count()
     */
    private void handlerCount() {
        Observable.just(1,2,3)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        logPrint("=======================aLong=" + aLong);
                    }
                });
    }

    /**
     * 延迟一段时间发送事件
     */
    private void handlerDelay() {
        logPrint("开始执行.......");
        Observable.just(1,2,3)
                .delay(3,TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("accept......=" + integer);
                    }
                });
    }


    /**
     * doOnEach():
     * Observable 每发送一件事件之前都会先回调这个方法
     */
    private void handlerDoOneEach() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();

            }
        })
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        logPrint("==================doOnEach =" + integerNotification.getValue());

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("\"==================onNext = " + integer);
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
     * Observable 每发送 onNext() 之前都会先回调这个方法
     * doOnNext()
     */
    private void handlerDoOnNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();

            }
        })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("doOnNext......accept = " + integer);

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext = " + integer);

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
     * doAfterNext():
     * Observable 每发送 onNext() 之后都会回调这个方法。
     */
    private void handlerDoAfterNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        logPrint("doAfterNext  accept=" + integer);
                    }
                })
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        logPrint("doOnEach..... =" + integerNotification.getValue());
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext...=" + integer);
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
     * doOnComplete()
     * Observable 每发送 onComplete() 之前都会回调这个方法
     */
    private void handlerDoOnComplete() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doOnComplete.....run");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.....= " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.....");
                    }
                });

    }

    /**
     * doOnError():
     * Observable 每发送 onError() 之前都会回调这个方法
     */
    private void handlerDoOnError() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new NullPointerException());
            }
        })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        logPrint("doOnError  ..accept=" + throwable.getMessage());
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.. =" + integer);
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

    /**
     * doOnSubscribe():
     * Observable 每发送 onSubscribe() 之前都会回调这个方法
     */
    private void handlerDoOnSubscribe() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        logPrint("doOnSubscribe.......accept");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.....");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext....=" + integer);
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

    /**
     * doOnDispose():
     * 当调用 Disposable 的 dispose() 之后回调该方法
     */
    private void handlerDoOnDispose() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doOnDispose........");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    private Disposable d;
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.......");
                        this.d = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.......=" +integer );
                        d.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError........");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     * doOnLifecycle():
     * 在回调 onSubscribe 之前回调该方法的第一个参数的回调方法，可以使用该回调方法决定是否取消订阅
     * doOnLifecycle() 第二个参数的回调方法的作用与 doOnDispose() 是一样的
     */
    private void handlerDoOnLifecycle() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        logPrint("doOnLifecycle.......");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("==================doOnLifecycle Action");
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doOnDispose.......");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    private Disposable d;

                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe......");
                        this.d = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.........");
                        d.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError........");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.......");
                    }
                });
    }

    /**
     * doOnTerminate() & doAfterTerminate()
     * doOnTerminate 是在 onError 或者 onComplete 发送之前回调，而 doAfterTerminate 则是 onError 或者 onComplete 发送之后回调
     */
    private void handlerDoTerminate(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doOnTerminate....");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.......");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext........");
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError..........");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.......");
                    }
                });
    }

    /**
     * doFinally()
     * 在所有事件发送完毕之后回调该方法
     * 这里可能你会有个问题，那就是 doFinally() 和 doAfterTerminate() 到底有什么区别？区别就是在于取消订阅，如果取消订阅之后 doAfterTerminate() 就不会被回调，而 doFinally() 无论怎么样都会被回调，且都会在事件序列的最后
     */
    private void handlerDoFinally() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doFinally............");
                    }
                })
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doOnDispose......");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        logPrint("doAfterTerminate......");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe........");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.....=" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError........");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.....");
                    }
                });
    }

    /**
     * 当接受到一个 onError() 事件之后回调，返回的值会回调 onNext() 方法，并正常结束该事件序列
     * onErrorReturn():
     */
    private void handlerOnErrorReturn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new NullPointerException());
            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        logPrint("==================onErrorReturn ");
                        return 404;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.....");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext........=" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError........");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.......");
                    }
                });
    }

    /**
     * onErrorResumeNext()
     * 当接收到 onError() 事件时，返回一个新的 Observable，并正常结束事件序列
     */
    private void handlerOnErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                logPrint("subscribe........");
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new NullPointerException());
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                        logPrint("onErrorResumeNext.........");
                        return Observable.just(4,5,6);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.....");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext........");
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError.......");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.....");
                    }
                });
    }


    /**
     * onExceptionResumeNext():
     * 与 onErrorResumeNext() 作用基本一致，但是这个方法只能捕捉 Exception
     */
    private void handlerOnExceptionResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Error("404"));

            }
        })
                .onExceptionResumeNext(new Observable<Integer>() {
                    @Override
                    protected void subscribeActual(Observer<? super Integer> observer) {
                        logPrint("onExceptionResumeNext......");
                        observer.onNext(333);
                        observer.onComplete();
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logPrint("onSubscribe.....");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext....=" +integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        logPrint("onError......");
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.....");
                    }
                });
    }

    /**
     * 测试 发送速度 > 接收速度  造成内存溢出
     */
    private void testPress() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i =0;;i++) {
                    Log.d("Test","发送了事件：" +i);
                    Thread.sleep(1);
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()) // 设置被观察者在io线程中
        .observeOn(AndroidSchedulers.mainThread()) // 设置观察者在主线程中
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("Test","开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer integer) {
                try {
                    // 接收事件的速度 5s/个
                    Thread.sleep(5000);
                    Log.d("Test","接收到了事件" + integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Test","对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("Test","onComplete........");
            }
        });
    }

    /**
     * Flowable的基础使用
     * Flowable的基础使用非常类似于 Observable
     */
    private void handlerPress2() {

        /**
         * 创建被观察者
         */
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);
        // 需要传入背压参数 BackpressureStrategy

        /**
         * 创建观察者  Subscriber
         */

        Subscriber<Integer> downstream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                // 不同点：Subscription增加了void request(long n)
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                logPrint("onNext....=" + integer);
            }

            @Override
            public void onError(Throwable t) {
                logPrint("onError......=" + t);
            }

            @Override
            public void onComplete() {
                logPrint("onComplete......");
            }
        };

        /**
         * 建立订阅关系
         */
        upstream.subscribe(downstream);
    }

    private void handlerPress3() {
        Flowable.create(new FlowableOnSubscribe<Integer>(){

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();

            }
        },BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    
    private void handlerPress4() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //一共发送4个事件
                logPrint("发送事件1");
                emitter.onNext(1);
                logPrint("发送事件2");
                emitter.onNext(2);
                logPrint("发送事件3");
                emitter.onNext(3);
                logPrint("发送事件4");
                emitter.onNext(4);
                logPrint("发送完成");
                emitter.onComplete();

            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription

                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError  =" +t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    private void handlerPress5() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //一共发送4个事件
                logPrint("发送事件1");
                emitter.onNext(1);
                logPrint("发送事件2");
                emitter.onNext(2);
                logPrint("发送事件3");
                emitter.onNext(3);
                logPrint("发送事件4");
                emitter.onNext(4);
                logPrint("发送完成");
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe...");
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.....=" +integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError......=" +t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.....");
                    }
                });
    }

    /**
     * 观察者不接收事件的情况下，被观察者继续发送事件至超出缓存区128
     *
     * 执行结果： onError.....=io.reactivex.exceptions.MissingBackpressureException: create: could not emit value due to lack of requests
     */
    private void handlerPress6() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 一共发送 129个事件，超出了缓存区的大小
                for (int i =0;i < 129;i++) {
                    logPrint("发送事件=" +i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe...");
                        // 默认不接收可接收事件大小
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext  收到：" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError.....=" + t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.......");
                    }
                });
    }

    /**
     * 同步订阅 & 异步订阅 的区别在于
     * 同步订阅中，被观察者 & 观察者工作于同1线程
     * 同步订阅关系中没有缓存区
     * 被观察者在发送1个事件后，必须等待观察者接收后，才能继续发下1个事件
     *
     *
     * 对于没有缓存区概念的同步订阅关系来说，单纯采用控制观察者的接收事件数量（响应式拉取）实际上就等于 “单相思”，
     * 虽然观察者控制了要接收3个事件，但假设被观察者需要发送4个事件，还是会出现问题
     */
    private void handlerPress7() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //一共发送3个事件
                logPrint("发送事件1");
                emitter.onNext(1);
                logPrint("发送事件2");
                emitter.onNext(2);
                logPrint("发送事件3");
                emitter.onNext(3);
                logPrint("发送事件4");
                emitter.onNext(4);
                logPrint("发送完成");
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(3);
                        // 每次可接收事件 = 3 二次匹配
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext....=" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError......=" + t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }


    /**
     * 同步情况
     * 在被观察者发送第1个事件后, 就抛出MissingBackpressureException异常 & 观察者没有收到任何事件
     */
    private void handlerPress8() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //一共发送3个事件
                logPrint("发送事件1");
                emitter.onNext(1);
                logPrint("发送事件2");
                emitter.onNext(2);
                logPrint("发送事件3");
                emitter.onNext(3);
                logPrint("发送事件4");
                emitter.onNext(4);
                logPrint("发送完成");
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe.....");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.....=" +integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError.....=" + t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete....");
                    }
                });
    }

    /**
     * 同步订阅问题
     * 下面的例子 = 被观察者根据观察者自身接收事件能力（10个事件），从而仅发送10个事件
     */
    private void handlerPress9() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                long n = emitter.requested();
                logPrint("Flowable.create....subscribe=" + n);
                // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                for (int i =0;i <n;i++) {
                    logPrint("发送了事件:" +i);
                    emitter.onNext(i);
                }
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 首先会执行
                        logPrint("onSubscribe......");
                        // 设置观察者每次能接受10个事件
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext........");
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError.....=" + t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete.......");
                    }
                });
    }


    /**
     * 在同步订阅情况中使用FlowableEmitter.requested()时，有以下几种使用特性需要注意的
     *
     * Subscription.request（a1）；
     * Subscription.request（a2）；
     *
     * FlowableEmitter.requested()的返回值 = a1 + a2
     */
    private void handlerPress10() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                logPrint("观察者可接收事件=" +emitter.requested());
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe.....");
                        // 第1次设置观察者每次能接受10个事件
                        s.request(10);
                        // 第2次设置观察者每次能接受20个事件
                        s.request(20);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("接收到了事件=" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError=" +t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     * 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
     * 即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
     * 仅计算Next事件，complete & error事件不算。
     *
     * Subscription.request（10）；
     * // FlowableEmitter.requested()的返回值 = 10
     * FlowableEmitter.onNext(1);
     * // 发送了1个事件
     * // FlowableEmitter.requested()的返回值 = 9
     */
    private void handlerPress11() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
               //  1. 调用emitter.requested()获取当前观察者需要接收的事件数量
                logPrint("观察者可接收事件数量 =" +emitter.requested());
                // 2. 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
                //  即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
                logPrint("发送了事件 1");
                emitter.onNext(1);
                logPrint("发送了事件1后, 还需要发送事件数量 =" + emitter.requested());
                logPrint("发送了事件 2");
                emitter.onNext(2);
                logPrint("发送事件2后, 还需要发送事件数量 =" + emitter.requested());
                logPrint("发送了事件 3");
                emitter.onNext(3);
                logPrint("发送事件3后, 还需要发送事件数量 = " + emitter.requested());
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe....设置观察者每次能接受10个事件");
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("接收到了事件=" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError...=" +t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     * 当FlowableEmitter.requested()减到0时，则代表观察者已经不可接收事件
     * 此时被观察者若继续发送事件，则会抛出MissingBackpressureException异常
     * 如观察者可接收事件数量 = 1，当被观察者发送第2个事件时，就会抛出异常
     */
    private void handlerPress12() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //  1. 调用emitter.requested()获取当前观察者需要接收的事件数量
                logPrint("观察者可接收事件数量 =" +emitter.requested());
                // 2. 每次发送事件后，emitter.requested()会实时更新观察者能接受的事件
                //  即一开始观察者要接收10个事件，发送了1个后，会实时更新为9个
                logPrint("发送了事件 1");
                emitter.onNext(1);
                logPrint("发送了事件1后, 还需要发送事件数量 =" + emitter.requested());
                logPrint("发送了事件 2");
                emitter.onNext(2);
                logPrint("发送事件2后, 还需要发送事件数量 =" + emitter.requested());
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe....设置观察者每次能接受1个事件");
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("接收到了事件=" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError...=" +t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     *  异步订阅情况
     *  从上面可以看出，由于二者处于不同线程，所以被观察者 无法通过 FlowableEmitter.requested()
     *  知道观察者自身接收事件能力，即 被观察者不能根据 观察者自身接收事件的能力 控制发送事件的速度
     */
    private void handlerPress13() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                logPrint("观察者可接收事件数量 =" + emitter.requested());
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe");
                        s.request(150);

                        // 该设置仅影响观察者线程中的requested，却不会影响的被观察者中的FlowableEmitter.requested()的返回值
                        // 因为FlowableEmitter.requested()的返回值 取决于RxJava内部调用request(n)，而该内部调用会在一开始就调用request(128)
                        // 为什么是调用request(128)下面再讲解
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("onNext.....=" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError......=" + t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete......");
                    }
                });
    }

    /**
     *而在异步订阅关系中，反向控制的原理是：通过RxJava内部固定调用被观察者线程中的request(n) 从而 反向控制被观察者的发送事件速度
     * 那么该什么时候调用被观察者线程中的request(n) & n 的值该是多少呢？请继续往下看
     * 关于RxJava内部调用request(n)（n = 128、96、0）的逻辑如下：
     * 至于为什么是调用request（128） & request（96） & request（0），感兴趣的读者可自己阅读 Flowable的源码
     */
    private void handlerPress14() {
        // 被观察者：一共需要发送500个事件，但真正开始发送事件的前提 = FlowableEmitter.requested()返回值 ≠ 0
        // 观察者：每次接收事件数量 = 48（点击按钮）

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                logPrint("观察者可接收事件数量 = " + emitter.requested());
                boolean flag;
                // 被观察者一共需要发送500个事件
                for (int i =0;i < 500;i++) {
                    flag = false;
                    // 若requested() == 0则不发送
                    while (emitter.requested() == 0) {
                        if (!flag) {
                            logPrint("不再发送");
                            flag = true;
                        }
                    }

                    // requested() ≠ 0 才发送
                    logPrint("发送了事件" + i + "，观察者可接收事件数量 = " +emitter.requested());
                }
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logPrint("onSubscribe.....");
                        mSubscription = s;
                        // 初始状态 = 不接收事件；通过点击按钮接收事件
                    }

                    @Override
                    public void onNext(Integer integer) {
                        logPrint("接收到了事件=" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logPrint("onError...=" +t);
                    }

                    @Override
                    public void onComplete() {
                        logPrint("onComplete........");
                    }
                });
    }
}
