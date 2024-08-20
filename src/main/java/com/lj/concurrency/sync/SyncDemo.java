package com.lj.concurrency.sync;


import lombok.extern.slf4j.Slf4j;

/**
 * @author Fox
 * 思考： 两个线程对初始值为 0 的静态变量一个做自增，一个做自减，各做 5000 次，结果是 0 吗？
 */
@Slf4j
public class SyncDemo {

    private static  int counter = 0;
    //private static AtomicInteger counter = new AtomicInteger(0);
    private Object lock = "";

    public   void increment() {
        synchronized (lock){
            counter++;
        }

        //counter.getAndIncrement();
    }

    public   void decrement() {
        synchronized (this){
            counter--;
        }

        //counter.getAndDecrement();
    }

    public static void main(String[] args) throws InterruptedException {
        SyncDemo syncDemo = new SyncDemo();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                syncDemo.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                syncDemo.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        //思考： counter=？
        log.info("counter={}", counter);


    }
}
