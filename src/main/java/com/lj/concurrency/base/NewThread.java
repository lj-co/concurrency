package com.lj.concurrency.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建和启动线程案例
 */
@Slf4j
public class NewThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"运行线程t1");
            }
        },"t1");
        //启动线程
        t1.start();
//        t1.run();


        Runnable runnable = () -> System.out.println("运行线程t2");
        Thread thread = new Thread(runnable,"t2");
        thread.start();




        // 创建任务对象
        FutureTask<Integer> task3 = new FutureTask<>(() -> {
            log.debug("hello");
            return 100;
        });
        // 参数1 是任务对象; 参数2 是线程名字，推荐
        new Thread(task3, "t3").start();
        // 主线程阻塞，同步等待 task 执行完毕的结果
        Integer result = task3.get();
        log.debug("结果是:{}", result);


    }
}
