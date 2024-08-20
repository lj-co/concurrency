package com.lj.concurrency.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo6 {

    public static void main(String[] args) throws Exception {

        // 定义只有一个许可证的Semaphore
        final Semaphore semaphore = new Semaphore(1, true);
        // 创建线程t1
        Thread t1 = new Thread(() -> {
            try {
                // 获取Semaphore的许可证
                semaphore.acquire();
                System.out.println("The thread t1 acquired permit from semaphore.");
                // 霸占许可证一个小时
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("The thread t1 is interrupted");
            } finally {
                // 在finally语句块中释放许可证
                semaphore.release();
            }

        });
        // 启动线程t1
        t1.start();
        // 为确保线程t1已经启动，在主线程中休眠1秒稍作等待
        TimeUnit.SECONDS.sleep(1);
        // 创建线程t2
        Thread t2 = new Thread(() -> {
//            try {
//                // 阻塞式地获取一个许可证
//                semaphore.acquire();
//                System.out.println("The thread t2 acquired permit from semaphore.");
//            } catch (InterruptedException e) {
//                System.out.println("The thread t2 is interrupted");
//
//            }finally {
//                // 在finally语句块中释放已经获取的许可证
//                semaphore.release();
//            }
            try{
                // 获取许可证
                semaphore.acquire();
            } catch (InterruptedException e){
                System.out.println("The thread t2 is interrupted");
                // 若出现异常则不再往下进行
                return;
            }
            // 程序运行到此处，说明已经成功获取了许可证，因此在finally语句块中对其进行释放就是理所当然的了
            try {
                System.out.println("The thread t2 acquired permit from semaphore.");
            } finally{
                semaphore.release();
            }

        });
        // 启动线程t2
        t2.start();
        // 休眠2秒后
        TimeUnit.SECONDS.sleep(2);
        // 对线程t2执行中断操作
        t2.interrupt();
        // 主线程获取许可证
        semaphore.acquire();
        System.out.println("The main thread acquired permit.");

    }

}
