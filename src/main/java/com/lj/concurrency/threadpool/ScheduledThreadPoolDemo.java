package com.lj.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: Fox
 * @Desc: 定时任务线程池示例
 **/
@Slf4j
public class ScheduledThreadPoolDemo {

    public static void main(String[] args) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                log.info("开始执行");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("任务执行");
            }
        };

        //创建线程池
        ScheduledExecutorService threadpool = Executors.newScheduledThreadPool(2);

//        log.info("提交延时任务");
//        threadpool.schedule(task,5, TimeUnit.SECONDS);


        log.info("提交周期性任务");
        //threadpool.scheduleAtFixedRate(task,5,2, TimeUnit.SECONDS);

        threadpool.scheduleWithFixedDelay(task,5,2, TimeUnit.SECONDS);

    }
}
