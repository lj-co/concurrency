package com.lj.concurrency.forkjoin.recursivetask;

import com.lj.concurrency.forkjoin.util.Utils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


/**
 * @author Fox
 * <p>
 * 利用ForkJoinPool计算1亿个整数的和
 */
public class LongSumMain {
    // 获取逻辑处理器数量 12
    static final int NCPU = Runtime.getRuntime().availableProcessors();


    public static void main(String[] args) throws Exception {
        //准备数组
        int[] array = Utils.buildRandomIntArray(4000);
        //构建任务
        LongSum ls = new LongSum(array, 0, array.length);
        // 构建ForkJoinPool
        ForkJoinPool fjp = new ForkJoinPool(2);
        //ForkJoin计算数组的和
        ForkJoinTask<Long> result = fjp.submit(ls);
        System.out.println("forkjoin sum=" + result.get());
        fjp.shutdown();


        // 并行流
        // 使用并行流计算数组的和，转换为long以避免溢出
//        long sum = Arrays.stream(array) // 创建IntStream
//                .parallel()     // 启用并行处理
//                .asLongStream() // 转换为LongStream以避免溢出
//                .sum();         // 计算总和
//
//        // 输出结果
//        System.out.println("并行流 sum: " + sum);


    }

}