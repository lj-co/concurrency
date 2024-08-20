package com.lj.concurrency.blockingqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: Fox
 * @Desc:
 **/
public class QueueDemo {

    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<String>();
//        queue.add("1");
//        queue.add("2");
//        String poll = queue.poll();
//        System.out.println(poll);
//        String remove = queue.remove();
//        System.out.println(remove);


        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(2);
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        //blockingQueue.put("1");
                        blockingQueue.offer("1",5,TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("生产者线程正在生产");

                }
            }
        });
        producer.start();
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                String data = null;
                while (true){
                    try {
                        data = blockingQueue.poll(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("消费者线程正在消费："+data);
                }
            }
        });
        consumer.start();

    }
}
