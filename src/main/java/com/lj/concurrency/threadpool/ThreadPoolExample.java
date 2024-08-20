package com.lj.concurrency.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池例子
 */
@Slf4j
public class ThreadPoolExample {
  
    public static void main(String[] args) {  
        // 创建一个固定大小的线程池  
        ExecutorService executor = Executors.newFixedThreadPool(5);
  
        // 提交任务到线程池  
        for (int i = 0; i < 10; i++) {
            final int taskId = i;  
            executor.execute(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("Task " + taskId + " is running by " + Thread.currentThread().getName());
            });
        }


//       // 关闭线程池
        executor.shutdown();
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("xxxxx");
//            }
//        });
        while (!executor.isTerminated()) {
            // 等待所有任务完成
        }
        System.out.println("All tasks are done.");
    }  
}