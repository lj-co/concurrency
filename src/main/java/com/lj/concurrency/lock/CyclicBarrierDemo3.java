package com.lj.concurrency.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 模拟跟团旅游： 导游得确定所有人都上车了才能发车去下一个旅游景点，到了景点，导游得确认所有人都下车了
 */
public class CyclicBarrierDemo3 {
    public static void main(String[] args)
            throws BrokenBarrierException, InterruptedException {
        // 定义CyclicBarrier，注意这里的parties值为11
        final CyclicBarrier barrier = new CyclicBarrier(11);
        // 创建10个线程
        for (int i = 0; i < 10; i++) {
            // 定义游客线程，传入游客编号和barrier
            new Thread(new Tourist(i, barrier)).start();
        }
        // 主线程也进入阻塞，等待所有游客都上了旅游大巴
        barrier.await();
        System.out.println("导游:所有的游客都上了车.");
        // 主线程进入阻塞，等待所有游客都下了旅游大巴
        barrier.await();
        System.out.println("导游:所有的游客都下车了.");
    }

    private static class Tourist implements Runnable {
        private final int touristID;
        private final CyclicBarrier barrier;

        private Tourist(int touristID, CyclicBarrier barrier) {
            this.touristID = touristID;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.printf("游客:%d 乘坐旅游大巴\n", touristID);
            // 模拟乘客上车的时间开销
            this.spendSeveralSeconds();
            // 上车后等待其他同伴上车
            this.waitAndPrint("游客:%d 上车，等别人上车.\n");
            System.out.printf("游客:%d 到达目的地\n", touristID);
            // 模拟乘客下车的时间开销
            this.spendSeveralSeconds();
            // 下车后稍作等待，等待其他同伴全部下车
            this.waitAndPrint("游客:%d 下车，等别人下车.\n");
        }

        private void waitAndPrint(String message) {
            System.out.printf(message, touristID);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                // ignore
            }
        }

        // random sleep
        private void spendSeveralSeconds() {
            try {
                TimeUnit.SECONDS.sleep(current().nextInt(10));
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}