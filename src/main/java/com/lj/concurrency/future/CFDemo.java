package com.lj.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: Fox
 * @Desc:
 **/
public class CFDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Runnable runnable = () -> {
            System.out.println("执行无返回结果的异步任务");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        //提交一个异步任务
        CompletableFuture.runAsync(runnable);

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行有返回值的异步任务");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello World";
        });
        String result = future.get();
        System.out.println(result);




        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
