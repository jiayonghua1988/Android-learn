https://juejin.im/post/5b17560e6fb9a01e2862246f

## RxJava2 只看这一篇文章就够了
### 0. 简介
RxJava 其实就是提供一套异步编程的 API，这套 API 是基于观察者模式的，而且是链式调用的，所以使用 RxJava 编写的代码的逻辑会非常简洁。
RxJava 有以下三个基本的元素：

被观察者（Observable）
观察者（Observer）
订阅（subscribe）

下面来说说以上三者是如何协作的：
首先在 gradle 文件中添加依赖：
```
implementation 'io.reactivex.rxjava2:rxjava:2.1.4'
implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
```
 1.创建被观察者：
 ```
 Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
    @Override
    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onComplete();
    }
});
 ```
2.创建观察者：
```
Observer observer = new Observer<Integer>() {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "======================onSubscribe");
    }

    @Override
    public void onNext(Integer integer) {
        Log.d(TAG, "======================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "======================onError");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "======================onComplete");
    }
};
```
3.订阅
```
observable.subscribe(observer);
```
这里其实也可以使用链式调用：
```
Observable.create(new ObservableOnSubscribe < Integer > () {
    @Override
    public void subscribe(ObservableEmitter < Integer > e) throws Exception {
        Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onComplete();
    }
})
.subscribe(new Observer < Integer > () {
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "======================onSubscribe");
    }

    @Override
    public void onNext(Integer integer) {
        Log.d(TAG, "======================onNext " + integer);
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "======================onError");
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "======================onComplete");
    }
});

```
被观察者发送的事件有以下几种，总结如下表：
其实可以把 RxJava 比喻成一个做果汁，家里有很多种水果（要发送的原始数据），你想榨点水果汁喝一下，这时候你就要想究竟要喝什么水果汁呢？如果你想喝牛油果雪梨柠檬汁，那你就要把这三种水果混在一起榨汁（使用各种操作符变换你想发送给观察者的数据），榨完后，你就可以喝上你想要的果汁了（把处理好的数据发送给观察者）。



https://blog.csdn.net/fengluoye2012/article/details/79297186
https://blog.csdn.net/carson_ho/article/details/79081407
https://www.jianshu.com/p/a406b94f3188