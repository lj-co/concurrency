package com.lj.concurrency.base.communication;

/**
 * 等待通知机制例子
 */

public class WaitDemo {

    public static void main(String[] args) throws InterruptedException {
        Object locker = new Object();

        Thread t1 = new Thread(() -> {
            try {

                synchronized (locker) {
                    System.out.println("wait开始");
                    locker.wait();
                }
                System.out.println("wait结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();

        //保证t1先启动，wait()先执行
        Thread.sleep(1000);

        Thread t2 = new Thread(() -> {
            synchronized (locker) {
                System.out.println("notify开始");
                locker.notifyAll();
                System.out.println("notify结束");
            }
        });

        t2.start();

    }
}