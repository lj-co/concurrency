package com.lj.concurrency.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟抢票场景
 */
public class ReentrantLockDemo {

    private final ReentrantLock lock = new ReentrantLock();//默认非公平
    private static  int tickets = 8; // 总票数

    public  void  buyTicket() throws InterruptedException{

        //加锁   这是一个阻塞方法
        lock.lock();
        try{
            //购票逻辑
            if (tickets > 0) {
                // 休眠10ms,模拟业务执行时间
                TimeUnit.MILLISECONDS.sleep(10);

                System.out.println(Thread.currentThread().getName() + "购买了第" + tickets-- + "张票");

                //模拟异常场景
                //throw new RuntimeException();
            } else {
                System.out.println("票已经卖完了，" + Thread.currentThread().getName() + "抢票失败");
            }
        }finally {
            //解锁
            lock.unlock();
        }

    }


    public static void main(String[] args) {
        ReentrantLockDemo ticketSystem = new ReentrantLockDemo();
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    ticketSystem.buyTicket(); // 抢票
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }, "线程" + i);
            // 启动线程
            thread.start();
        }


        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("剩余票数：" + tickets);
    }
}

