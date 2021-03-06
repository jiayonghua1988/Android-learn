## Dagger2 学习总结
- dagger2是什么
> dagger2 是一个依赖注入框架 在编译期间自动生成代码，负责依赖对象的创建
> Dagger2通过APT技术，通过在编译的时候生成注入的工厂类代码，解决了性能问题
### 注入原理
> 编译时扫描注解，生成对应的builder和factory
### @Inject
> 通常在需要依赖的地方使用这个注解。换句话说，你用它告诉Dagger这个类或者字段需要依赖注入。这样，Dagger就会构造一个这个类的实例并满足他们的依赖。
### @Module
> Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的时候，
> 就知道从哪里去找到需要的 依赖。modules的一个重要特征是它们设计为分区并组合在一起（比如说，在我们的app中
> 可以有多个组成在一起的modules）。

### @Provide
> 在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖
### @Component
> Components从根本上来说就是一个注入器，也可以说是@Inject和@Module的桥梁，它的主要作用就是连接这两个部分。
> Components可以提供所有定义了的类型的实例，比如：我们必须用@Component注解一个接口然后列出所有的@Modules组成该组件，
> 如 果缺失了任何一块都会在编译的时候报错。所有的组件都可以通过它的modules知道依赖的范围。

### @Scope
> Scopes可是非常的有用，Dagger2可以通过自定义注解限定注解作用域。后面会演示一个例子，这是一个非常强大的特点，因为就如前面说的一样
> ，没 必要让每个对象都去了解如何管理他们的实例。在scope的例子中，我们用自定义的@PerActivity注解一个类，所以这个对象存活时间就和 activity的一样
> 。简单来说就是我们可以定义所有范围的粒度(@PerFragment, @PerUser, 等等)。

### @Qualifier
>当类的类型不足以鉴别一个依赖的时候，我们就可以使用这个注解标示。例如：在Android中，我们会需要不同类型的context，
> 所以我们就可以定义 qualifier注解“@ForApplication”和“@ForActivity”，这样当注入一个context的时候，我们就可以告诉 
> Dagger我们想要哪种类型的context。


## 个人通俗理解
> @Inject 构造实例
> @Module  提供依赖   Modules类里面的方法专门提供依赖  返回提供的实例构造


## 遇到的问题
> Test 构造函数加了一个context  并且提供了 module 一直编译不过去  由于  在别的类中也有引用 没有 引用module

## 参考：
> https://cloud.tencent.com/developer/article/1059726


