package com.lj.concurrency.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Fox
 * @Desc: 线程池批量提交任务示例
 **/
public class InvokeAllDemo {

    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            tasks.add(new Task(i));
        }

        //创建线程池
        ExecutorService threadpool = Executors.newFixedThreadPool(5);
        try {
            //批量提交任务
//            List<Future<Integer>> futures = threadpool.invokeAll(tasks);
//            for (Future<Integer> future : futures){
//                System.out.println(future.get());
//            }

            Integer i = threadpool.invokeAny(tasks);
            System.out.println(i);



        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
            //关闭线程池
            threadpool.shutdown();
        }


    }


    static class Task implements Callable<Integer> {

        private int index;

        Task(int index) {
            this.index = index;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            return index;
        }
    }

}


