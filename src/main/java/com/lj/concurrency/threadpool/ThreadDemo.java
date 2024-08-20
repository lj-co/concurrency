package com.lj.concurrency.threadpool;

import java.util.concurrent.*;

/**
 * @author: Fox
 * @Desc:
 **/
public class ThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建任务
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        //创建线程
//        Thread thread1 = new Thread(task1);
//        Thread thread2 = new Thread(task2);
//        Thread thread3 = new Thread(task3);
//       //启动线程
//        thread1.start();
//        thread2.start();
//        thread3.start();


        //创建一个只要一个线程线程池
        ExecutorService threadpool = Executors.newSingleThreadExecutor();
        threadpool.submit(task1);
        threadpool.submit(task2);
        Future<String> future = threadpool.submit(task3, "执行完成");
        System.out.println(future.get());

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return "执行完成";
            }
        };
        Future future2 = threadpool.submit(callable);
        System.out.println(future2.get());



    }
}

class Task implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName());

    }
}
