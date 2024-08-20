package com.lj.concurrency.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author: Fox
 * @Desc:
 **/
public class CFDemo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        CompletableFuture<Void> future = CompletableFuture
//                .supplyAsync(() -> {
//                    int number = new Random().nextInt(10);
//                    System.out.println("第一阶段：" + number);
//                    return number;
//                }).thenAccept(number ->
//                        System.out.println("第二阶段：" + number * 5));
//
//        System.out.println("最终结果：" + future.get());

//        CompletableFuture<Integer> futrue1 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                int number = new Random().nextInt(3) + 1;
//                try {
//                    TimeUnit.SECONDS.sleep(number);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("第一阶段：" + number);
//                return number;
//            }
//        });
//
//        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
//            @Override
//            public Integer get() {
//                int number = new Random().nextInt(3) + 1;
//                try {
//                    TimeUnit.SECONDS.sleep(number);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("第二阶段：" + number);
//                return number;
//            }
//        });
//
//        futrue1.thenAcceptBoth(future2, new BiConsumer<Integer, Integer>() {
//            @Override
//            public void accept(Integer x, Integer y) {
//                System.out.println("最终结果：" + (x + y));
//            }
//        }).join();

//        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
//            int number = new Random().nextInt(10);
//            System.out.println("第一阶段：" + number);
//            return number;
//        }).thenRun(() ->
//                System.out.println("thenRun 执行"));
//        System.out.println("最终结果：" + future.get());


        CompletableFuture<Integer> future1 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        System.out.println("第一阶段：" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> future2 = CompletableFuture
                .supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int number = new Random().nextInt(10);
                        System.out.println("第二阶段：" + number);
                        return number;
                    }
                });
        CompletableFuture<Integer> result = future1
                .thenCombine(future2, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer x, Integer y) {
                        return x + y;
                    }
                });
        System.out.println("最终结果：" + result.get());


    }
}
