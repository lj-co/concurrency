package com.lj.concurrency.blockingqueue;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue死锁场景
 */
public class SynchronousQueueDeadlockDemo {
    public static void main(String[] args) {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Thread thread1 = new Thread(() -> {
            try {
                // 线程1尝试将数据放入队列
                int data = 42;
                queue.put(data);
                System.out.println("线程1放入数据：" + data);

                // 接着，线程1尝试从队列中获取数据，但此时没有其他线程来获取
                int result = queue.take();
                System.out.println("线程1获取数据：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                // 接着，线程2尝试将数据放入队列，但此时没有其他线程来获取
                int result = 100;
                queue.put(result);
                System.out.println("线程2放入数据：" + result);


                // 线程2尝试从队列中获取数据，但此时没有数据可用
                int data = queue.take();
                System.out.println("线程2获取数据：" + data);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}

