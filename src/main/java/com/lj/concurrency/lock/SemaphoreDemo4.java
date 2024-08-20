package com.lj.concurrency.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo4 {

    public static void main(String[] args) throws InterruptedException {

        final Semaphore semaphore = new Semaphore(1, true);
        // 定义一个线程
        new Thread(() -> {
            // 获取许可证
            boolean gotPermit = semaphore.tryAcquire();
            // 如果获取成功就休眠5秒的时间
            if (gotPermit) {
                try {
                    System.out.println(Thread.currentThread() + " get one permit.");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放Semaphore的许可证
                    semaphore.release();
                }
            }
        }).start();
        // 短暂休眠1秒的时间，确保上面的线程能够启动，并且顺利获取许可证
        TimeUnit.SECONDS.sleep(1);
        // 主线程在3秒之内肯定是无法获取许可证的，那么主线程将在阻塞3秒之后返回获取许可证失败
        if(semaphore.tryAcquire(3, TimeUnit.SECONDS)){
            System.out.println("get the permit");
        }else {
            System.out.println("get the permit failure.");
        }



    }
}
