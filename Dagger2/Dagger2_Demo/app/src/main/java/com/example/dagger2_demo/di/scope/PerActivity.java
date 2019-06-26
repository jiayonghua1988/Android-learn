package com.example.dagger2_demo.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 配置一个作用域
 * @Scope的一个注释，这个注释的意思就是作用域，在作用域内保持单例，可以直接理解为单例即可
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
