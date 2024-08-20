package com.lj.concurrency.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟抢票场景，使用ReentrantLock的 tryLock方法实现非阻塞尝试获取锁
 */
public class ReentrantLockTryLockDemo {

    private final ReentrantLock lock = new ReentrantLock(); // 默认非公平

    private static int tickets = 1; // 总票数

    public void buyTicket() throws InterruptedException {
        // 尝试获取锁
        if (lock.tryLock()) {
            try {
                // 购票逻辑
                if (tickets > 0) {
                    // 休眠10ms,模拟业务执行时间
                    TimeUnit.MILLISECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "购买了第" + tickets-- + "张票");
                } else {
                    System.out.println("票已经卖完了，" + Thread.currentThread().getName() + "抢票失败");
                }
            } finally {
                // 释放锁
                lock.unlock();
            }
        } else {
            // 没有获取到锁，直接返回或进行其他处理
            System.out.println(Thread.currentThread().getName() + "尝试获取锁失败，放弃抢票");

        }
    }

    public static void main(String[] args) {
        ReentrantLockTryLockDemo ticketSystem = new ReentrantLockTryLockDemo();

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

        // 主线程等待一段时间，让其他线程有机会执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 由于是tryLock，这里无法确保票数是否准确，因为可能同时有多个线程在尝试获取票
        // 但如果票数减少到0，后续线程应该打印出抢票失败的信息
        System.out.println("剩余票数：" + tickets);
    }
}