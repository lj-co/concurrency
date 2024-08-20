package com.lj.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: Fox
 * @Desc:
 **/
public class CFDemo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture.supplyAsync(() -> {
            System.out.println("执行有返回值的异步任务");
            return "Hello World";
        });

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            System.out.println(Thread.currentThread().getName()+"一阶段：" + result);
            return result;
        }).thenApplyAsync(number -> {
            int result = number * 3;
            System.out.println(Thread.currentThread().getName()+"二阶段：" + result);
            return result;
        });

        System.out.println("最终结果：" + future.get());


    }
}
