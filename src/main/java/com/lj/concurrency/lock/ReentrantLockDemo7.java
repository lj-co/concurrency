package com.lj.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断的例子
 */
@Slf4j
public class ReentrantLockDemo7 {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            log.debug("t1启动...");
            try {
                lock.lockInterruptibly();
                try {
                    log.debug("t1获得了锁");
                    // 模拟一些工作
                    Thread.sleep(2000);
                } finally {
                    lock.unlock();
                    log.debug("t1释放了锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("t1等锁的过程中被中断");
                // 清理工作，例如释放其他资源
                // ...
                // 重新抛出中断异常，以便上层调用者知道线程被中断了
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // 处理其他可能的异常
                e.printStackTrace();
            }

        }, "t1");

        lock.lock();
        try {
            log.debug("main线程获得了锁");
            //先让线程t1执行
            t1.start();

            Thread.sleep(1000);
            //中断t1
            t1.interrupt();
            log.debug("线程t1执行中断");

            // 等待t1线程结束或超时
            t1.join(5000);
        } finally {
            lock.unlock();
            log.debug("main线程释放了锁");
        }

    }

}
