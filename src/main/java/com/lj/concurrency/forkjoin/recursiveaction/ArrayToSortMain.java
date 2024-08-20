package com.lj.concurrency.forkjoin.recursiveaction;

import com.lj.concurrency.forkjoin.util.Utils;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ArrayToSortMain {

    public static void main(String[] args) {
        
        //生成测试数组  用于归并排序
        int[] arrayToSortByMergeSort = Utils.buildRandomIntArray(20000000);
        //int[] arrayToSortByMergeSort = {6,5,3,1,8,7,2,4};
        int threshold = 10000; //数组是否继续拆分的阈值，数组长度低于此阈值就不再进行拆分

        MergeSort mergeSort = new MergeSort(arrayToSortByMergeSort, threshold);
        long startTime = System.nanoTime();
        // 归并排序
        mergeSort.mergeSort();
        long duration = System.nanoTime()-startTime;
        System.out.println("单线程归并排序时间: "+(duration/(1000f*1000f))+"毫秒");


        //生成测试数组  用于forkjoin排序
        int[] arrayToSortByForkJoin = Arrays.copyOf(arrayToSortByMergeSort, arrayToSortByMergeSort.length);
        //获取处理器数量,用于配置forkjoin线程池中的核心线程数
        int processors = Runtime.getRuntime().availableProcessors();
        //int processors = 4;

        //利用forkjoin排序
        MergeSortTask mergeSortTask = new MergeSortTask(arrayToSortByForkJoin, threshold);
        //构建forkjoin线程池，传入核心线程数
        ForkJoinPool forkJoinPool = new ForkJoinPool(processors);
        startTime = System.nanoTime();
        //执行排序任务
        forkJoinPool.invoke(mergeSortTask);
        duration = System.nanoTime()-startTime;
        System.out.println("forkjoin排序时间: "+(duration/(1000f*1000f))+"毫秒");

    }
}
