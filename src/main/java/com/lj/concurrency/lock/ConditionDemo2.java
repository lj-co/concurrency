package com.lj.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Fox
 * @Desc:
 **/
@Slf4j
public class ConditionDemo2 {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            log.debug("t1开始执行....");
            lock.lock();
            try {
                log.debug("t1获取锁....");
                // 让线程在condition上一直等待下去
                condition.await();
                log.debug("t1执行完成....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();

            }
        }, "t1").start();

        new Thread(() -> {
            log.debug("t2开始执行....");
            lock.lock();
            try {
                log.debug("t2获取锁....");
                // 让线程在condition上一直等待下去
                condition.await();
                log.debug("t2执行完成....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();

            }
        }, "t2").start();

        // 主线程两秒后执行
        Thread.sleep(2000);
        log.debug("准备获取锁，去唤醒 condition上阻塞的线程");
        lock.lock();
        try {
            // 唤醒condition上所有阻塞的线程
            condition.signalAll();
            log.debug("唤醒condition上阻塞的线程");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
}
