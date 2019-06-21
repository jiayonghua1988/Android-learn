## RxJava2 只看这一篇文章就够了
### 0. 简介
RxJava 其实就是提供一套异步编程的 API，这套 API 是基于观察者模式的，而且是链式调用的，所以使用 RxJava 编写的代码的逻辑会非常简洁。
RxJava 有以下三个基本的元素：

被观察者（Observable）
观察者（Observer）
订阅（subscribe）

下面来说说以上三者是如何协作的：
首先在 gradle 文件中添加依赖：

...
implementation 'io.reactivex.rxjava2:rxjava:2.1.4'
implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
...
