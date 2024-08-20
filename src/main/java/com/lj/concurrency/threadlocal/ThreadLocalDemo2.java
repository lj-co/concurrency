package com.lj.concurrency.threadlocal;

/**
 * ThreadLocal测试demo
 */
public class ThreadLocalDemo2 {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private String content;

    private String getContent() {
        return threadLocal.get();
    }

    private void setContent(String content) {
        threadLocal.set(content);
    }

    public static void main(String[] args) {
        ThreadLocalDemo2 demo = new ThreadLocalDemo2();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 线程1  设置线程1自己的content
                    demo.setContent(Thread.currentThread().getName() + "的数据");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    System.out.println(Thread.currentThread().getName() + "--->" + demo.getContent());
                }
            });
            thread.setName("线程" + i);
            thread.start();
        }
    }
}