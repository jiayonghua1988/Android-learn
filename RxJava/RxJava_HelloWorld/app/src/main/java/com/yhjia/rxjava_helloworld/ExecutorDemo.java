package com.yhjia.rxjava_helloworld;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 演示线程池  ExecutorService
 *
 *
 * 2、Java 线程池
 * Java通过Executors提供四种线程池，分别为：
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class ExecutorDemo {

    public static void main(String [] args) {
        ExecutorDemo executorDemo = new ExecutorDemo();
//        executorDemo.cachedDemo();
//        executorDemo.fixedThreadPool();
        executorDemo.scheduleThreadPool();
    }

    /**
     * newCachedThreadPool
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     */
    private void cachedDemo() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i =0;i < 10;i++) {
            final int index = i;
            System.out.println("111:current Thread=" + Thread.currentThread().getName());
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("222:current Thread=" + Thread.currentThread().getName());
                        Thread.sleep(index * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(index);
                }
            });
        }

    }

    /**
     * 定长线程池  可控制线程最大并发数，超出的线程会在队列中等待
     */
    private void fixedThreadPool() {
       ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
       for (int i =0; i < 10;i++) {
           final int index = i;
           fixedThreadPool.execute(new Runnable() {
               @Override
               public void run() {
                       try {
                           System.out.println(index + "   current:" + Thread.currentThread().getName());
                           Thread.sleep(2000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }

               }
           });
       }
    }

    /**
     * 创建一个定长线程池 支持定时以及周期性任务执行
     */
    private void scheduleThreadPool() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 3 second");
            }
        },3, TimeUnit.SECONDS);
    }
}
