package com.lj.concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @author  Fox
 * join的测试案例：现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
 */
@Slf4j
public class ThreadJoinDemo2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("线程t1执行完成");
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("线程t2执行完成");
            }
        },"t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("线程t3执行完成");
            }
        },"t3");

        t1.start();
        t2.start();
        t3.start();

    }
}
