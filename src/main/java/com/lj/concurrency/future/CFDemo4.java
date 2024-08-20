package com.lj.concurrency.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: Fox
 * @Desc:
 **/
public class CFDemo4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        CompletableFuture<Integer> future = CompletableFuture
//                .supplyAsync(new Supplier<Integer>() {
//                    @Override
//                    public Integer get() {
//                        int number = new Random().nextInt(30);
//                        System.out.println("第一阶段：" + number);
//                        return number;
//                    }
//                })
//                .thenCompose(new Function<Integer, CompletionStage<Integer>>() {
//                    @Override
//                    public CompletionStage<Integer> apply(Integer param) {
//                        return CompletableFuture.supplyAsync(new Supplier<Integer>() {
//                            @Override
//                            public Integer get() {
//                                int number = param * 2;
//                                System.out.println("第二阶段：" + number);
//                                return number;
//                            }
//                        });
//                    }
//                });
//        System.out.println("最终结果: " + future.get());

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> result1 = future.thenApply(param -> param + " World");
        CompletableFuture<String> result2 = future
                .thenCompose(param -> CompletableFuture.supplyAsync(() -> param + " World"));

        System.out.println(result1.get());
        System.out.println(result2.get());

    }
}
