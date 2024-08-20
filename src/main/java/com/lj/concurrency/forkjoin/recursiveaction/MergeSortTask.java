package com.lj.concurrency.forkjoin.recursiveaction;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * 利用fork-join实现数组排序
 */
public class MergeSortTask extends RecursiveAction {

    private final int threshold; //数组是否继续拆分的阈值，数组长度低于此阈值就不再进行拆分
    private int[] arrayToSort; //要排序的数组

    public MergeSortTask(final int[] arrayToSort, final int threshold) {
        this.arrayToSort = arrayToSort;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        //拆分后的数组长度小于阈值，直接进行排序
        if (arrayToSort.length <= threshold) {
            // 调用jdk提供的排序方法
            Arrays.sort(arrayToSort);
            return;
        }

        // 对数组进行拆分
        int midpoint = arrayToSort.length / 2;
        int[] leftArray = Arrays.copyOfRange(arrayToSort, 0, midpoint);
        int[] rightArray = Arrays.copyOfRange(arrayToSort, midpoint, arrayToSort.length);

        MergeSortTask leftTask = new MergeSortTask(leftArray, threshold);
        MergeSortTask rightTask = new MergeSortTask(rightArray, threshold);


        //提交任务
//        leftTask.fork();
//        rightTask.fork();
//        //阻塞当前线程，直到获取任务的执行结果
//        leftTask.join();
//        rightTask.join();

        //调用任务,阻塞当前线程，直到所有子任务执行完成
        invokeAll(leftTask, rightTask);

        //System.out.println("当前线程"+Thread.currentThread().getName());

        // 合并排序结果
        arrayToSort = MergeSort.merge(leftTask.getSortedArray(), rightTask.getSortedArray());
    }

    public int[] getSortedArray() {
        return arrayToSort;
    }
}