package com.lj.concurrency.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Fox
 * @Desc:
 **/
@Slf4j
public class SyncDemo3 {

    //private static int counter = 0;
    private  static AtomicInteger counter = new AtomicInteger(0);


    public void increment() {
//        synchronized (this) {
//            counter++;
//        }
        counter.getAndIncrement();
    }
    public  void decrement() {
//        synchronized (this){
//            counter--;
//        }
        counter.getAndDecrement();
    }

    public static void main(String[] args) throws InterruptedException {

        SyncDemo3 syncDemo = new SyncDemo3();

        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                syncDemo.increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
           for (int i = 0; i < 10000000; i++) {
                syncDemo.decrement();
           }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        long end = System.currentTimeMillis();
        log.info("执行时间：{}ms,counter={}", end - start, counter);

    }
}
