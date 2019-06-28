## Retrofit 的基本用法

- Retrofit将Http请求抽象成Java接口，并在接口里面采用注解来配置网络请求参数。用动态代理将该接口的注解“翻译”成一个Http请求，最后再执行 Http请求

- APi接口中的最后一个注释，Responsebody是Retrofit网络请求回来的原始数据类，没经过Gson转换什么的，如果你不想转换，比如我就想看看接口返回的json字符串，那就像注释中说的，把Call的泛型定义为ResponseBody：Call<ResponseBody>
  
- GET注解 说白了就是我们的GET请求方式

- @Query  @Query("num")String num, @Query("page")String page；
- ?num=10&page=1

- 拼接到接口中(就是不带？分隔符)，那就不用Query注解了，而是使用Path注解




## 参考
- https://www.jianshu.com/p/0fda3132cf98