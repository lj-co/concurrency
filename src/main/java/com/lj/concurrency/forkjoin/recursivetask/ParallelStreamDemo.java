package com.lj.concurrency.forkjoin.recursivetask;

import java.util.Arrays;

public class ParallelStreamDemo {

    public static void main(String[] args) {

        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 8};
        //并行流
        Arrays.stream(array).parallel().forEach(i -> {
            System.out.println(Thread.currentThread().getName() + " num:" + i);
        });


    }
}
