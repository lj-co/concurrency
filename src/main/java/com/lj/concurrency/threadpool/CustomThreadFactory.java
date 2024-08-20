package com.lj.concurrency.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Fox
 * @Desc:  实现线程工厂
 **/
public class CustomThreadFactory implements ThreadFactory {

    private final AtomicInteger i = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        //创建线程
        Thread thread = new Thread(r);
        //设置线程名称
        thread.setName("线程"+i.getAndIncrement());
        return thread;
    }
}
