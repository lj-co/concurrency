package com.lj.concurrency.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class SemaphoreByLockDemo {
    public static void main(String[] args) {
        final TryLock tryLock = new TryLock();
        // 启动一个线程，尝试获取tryLock，如果获取不成功则将进行其他的操作，该线程不用进入阻塞状态
        new Thread(() -> {
            boolean gotLock = tryLock.tryLock();
            if (!gotLock) {
                System.out.println(currentThread() + "can't get the lock, will do other thing.");
                return;
            }
            try {
                simulateWork();
            } finally {
                tryLock.unlock();
            }
        }).start();
        // main线程也会参与trylock的争抢，同样，如果抢不到trylock，则main线程不会进入阻塞状态
        boolean gotLock = tryLock.tryLock();
        if (!gotLock) {
            System.out.println(currentThread() + " can't get the lock, will do other thing.");

        } else {
            try {
                //TODO 模拟业务执行
                simulateWork();
            } finally {
                tryLock.unlock();
            }
        }
    }

    // 定义trylock类
    private static class TryLock {
        // 定义permit为1的semaphore
        private final Semaphore semaphore = new Semaphore(1);

        public boolean tryLock() {
            return semaphore.tryAcquire();
        }

        public void unlock() {
            semaphore.release();
            System.out.println(currentThread() + " release lock");
        }
    }

    private static void simulateWork() {
        try {
            System.out.println(currentThread() + " get the lock and do working...");
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            // ignore
        }
    }
}