package com.lj.concurrency.future;

import com.lj.concurrency.base.utils.SleepTools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author: Fox
 * @Desc: 改写促销活动中商品信息查询的例子
 **/
public class CFDemo7 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                System.out.println("T1:查询商品基本信息...");
                SleepTools.second(5);
                return "商品基本信息查询成功";
            }
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                System.out.println("T2:查询商品价格...");
                SleepTools.second(10);
                return "商品价格查询成功";
            }
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                System.out.println("T3:查询商品库存...");
                SleepTools.ms(50);
                return "商品库存查询成功";
            }
        });
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                System.out.println("T4:查询商品图片...");
                SleepTools.ms(50);
                return "商品图片查询成功";
            }
        });
        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                System.out.println("T5:查询商品销售状态...");
                SleepTools.ms(50);
                return "商品销售状态查询成功";
            }
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2, future3, future4, future5);
        all.get();

    }
}
