package com.lj.concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @author  Fox
 * 中断机制案例: 中断 sleep 的线程, 会清空中断状态
 */
@Slf4j
public class ThreadInterruptDemo2 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            while(true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //break;

                    //Thread.currentThread().interrupt();
                }
                log.debug("继续执行");
            }
        }, "t1");
        t1.start();

        Thread.sleep(100);

        //中断线程t1
        t1.interrupt();
        log.debug("中断状态：{}",t1.isInterrupted());

    }
}