package com.lj.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池例子
 */
@Slf4j
public class ThreadPoolExample2 {
  
    public static void main(String[] args) {
        // 线程池的核心线程数  
        int corePoolSize = 3;
        // 线程池的最大线程数  
        int maximumPoolSize = 5;
        // 线程池的任务队列  
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3);
        // 线程池中工作线程（默认是非核心线程）保持空闲的时间
        long keepAliveTime = 60L;  
        // 时间单位  
        TimeUnit unit = TimeUnit.SECONDS;
        //线程工厂
        ThreadFactory threadFactory = new CustomThreadFactory();
        // 线程池的拒绝策略  
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
  
        // 创建线程池  
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,  
                maximumPoolSize,  
                keepAliveTime,  
                unit,  
                workQueue,
                handler  
        );
  
        // 提交任务到线程池  
        for (int i = 0; i < 9; i++) {
            final int taskId = i;  
            executor.execute(() -> {
                try {
                    //模拟业务办理时间
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                int x = 1/0;

                log.info("Task " + taskId + " is running by " + Thread.currentThread().getName());

            });
        }  
  
        // 关闭线程池  
//        executor.shutdown();
//        try {
//            // 等待所有任务完成，超时时间为60秒
//            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//                // 如果超时后任务仍未完成，则强制关闭线程池
//                executor.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            // 如果等待过程中被中断，也强制关闭线程池
//            executor.shutdownNow();
//        }
//
//        System.out.println("All tasks are done or interrupted.");
    }  
}