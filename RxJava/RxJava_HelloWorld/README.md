## RxJava HelloWorld
> RxJava 其实就是提供一套异步编程的 API，这套 API 是基于观察者模式的，而且是链式调用的，所以使用 RxJava 编写的代码的逻辑会非常简洁。

### 项目介绍
> 该项目是RxJava的第一个项目 HelloWorld 项目 主要演示 RxJava 库的导入 和最基本的用法

### 创建操作符
- create(): 创建一个被观察者
- just(): 创建一个被观察者并发送事件  发送的事件不能超过10个以上  比create()多了并发送  不能超过10个
- fromArray() 创建被观察者 并发送事件  和just类似 但是可以发送超过10个 可以传入一个数组







### 参考链接：
- https://blog.csdn.net/xinpengfei521/article/details/90345212
背压
- https://blog.csdn.net/carson_ho/article/details/79081407
