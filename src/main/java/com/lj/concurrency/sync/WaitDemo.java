package com.lj.concurrency.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Fox
 * @Desc:
 **/
@Slf4j
public class WaitDemo {
    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            log.debug("t1开始执行....");
            synchronized (obj) {
                log.debug("t1获取锁....");
                try {
                    // 让线程在obj上一直等待下去
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t1执行完成....");
            }
        },"t1").start();

        new Thread(() -> {
            log.debug("t2开始执行....");
            synchronized (obj) {
                log.debug("t2获取锁....");
                try {
                    // 让线程在obj上一直等待下去
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2执行完成....");
            }
        },"t2").start();

        // 主线程两秒后执行
        Thread.sleep(2000);
        log.debug("准备获取锁，去唤醒 obj 上阻塞的线程");
        synchronized (obj) {
            // 唤醒obj上一个线程
            //obj.notify();
            // 唤醒obj上所有等待线程
            obj.notifyAll();
            log.debug("唤醒 obj 上阻塞的线程");
        }


    }

}
