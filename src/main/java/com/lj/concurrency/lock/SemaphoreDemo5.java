package com.lj.concurrency.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo5 {
    public static void main(String[] args) throws InterruptedException{

        // 创建一个permit为1的Semaphore
        final Semaphore semaphore = new Semaphore(1, true);
        // 主线程抢先得到仅有的一个许可证
        semaphore.acquire();
        // 创建线程，获取permit
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+"被中断");
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        TimeUnit.SECONDS.sleep(5);
        // 执行线程thread的中断
        thread.interrupt();


        // 定义许可证数量为5的Semaphore
//        final Semaphore semaphore = new Semaphore(5, true);
//        // 尝试获取5个许可证，成功
//        assert semaphore.tryAcquire(5) : "acquire permit successfully.";
//        // 此时Semaphore中已经没有可用的许可证了，尝试获取将会失败
//        assert !semaphore.tryAcquire() : "acquire permit failure.";

    }  
}