package com.lj.concurrency.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo3 {

    public static void main(String[] args) throws InterruptedException {

        // 定义permit=1的Semaphore
        final Semaphore semaphore = new Semaphore(1, true);
        // 主线程直接抢先申请成功
        semaphore.acquire();
        Thread t = new Thread(() -> {
            try {
                // 线程t会进入阻塞，等待当前有可用的permit
                System.out.println("子线程等待获取permit");
                semaphore.acquire();
                System.out.println("子线程获取到permit");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //释放permit
                semaphore.release();
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("主线程释放permit");
        // 主线程休眠5秒后释放permit，线程t才能获取到permit
        semaphore.release();


    }
}
