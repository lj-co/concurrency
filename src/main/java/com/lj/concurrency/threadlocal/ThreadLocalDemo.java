package com.lj.concurrency.threadlocal;

/**
 * ThreadLocal测试demo
 */
public class ThreadLocalDemo {
    private String content;

    private String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public  void run() {
                    synchronized (demo) {
                        demo.setContent(Thread.currentThread().getName() + "的数据");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "--->" + demo.getContent());
                    }
                }
            });
            thread.setName("线程" + i);
            thread.start();
        }
    }
}