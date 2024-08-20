package com.lj.concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程休眠的案例
 */
@Slf4j
public class ThreadSleepDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("执行完成");

            }
        },"t1");
        t1.start();

        log.debug("线程t1的状态："+t1.getState());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.debug("线程t1的状态："+t1.getState());

        t1.interrupt();

    }
}
