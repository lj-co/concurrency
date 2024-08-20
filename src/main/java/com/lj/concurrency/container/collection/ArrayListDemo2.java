package com.lj.concurrency.container.collection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: Fox
 * @Desc: 模拟了多个线程同时对CopyOnWriteArrayList进行读写操作的情况
 **/
public class ArrayListDemo2 {

    public static void main(String[] args) {

        // 创建一个CopyOnWriteArrayList实例
        List<String> list = new CopyOnWriteArrayList<>();

        // 启动一个线程向列表中添加元素
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                list.add("Item " + i);
                try {
                    // 模拟耗时操作，让其他线程有机会读取数据
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Writer Thread Finished!");
        }).start();

        // 启动多个线程从列表中读取元素
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    // 读取列表中的元素
                    System.out.println("Reader Thread " + Thread.currentThread().getName() + " is reading: " + list);
                    try {
                        // 模拟耗时操作
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }
}
