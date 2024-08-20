package com.lj.concurrency.base.communication;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: Fox
 * @Desc: 等待通知机制例子
 **/
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {

        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ParkThread开始执行");
                // 当没有『许可』时，当前线程暂停运行；有『许可』时，用掉这个『许可』，当前线程恢复运行
                LockSupport.park();
                System.out.println("ParkThread执行完成");
            }
        });
        parkThread.start();

        Thread.sleep(3000);

        System.out.println("唤醒parkThread");
        // 给线程 parkThread 发放『许可』（多次连续调用 unpark 只会发放一个『许可』）
        LockSupport.unpark(parkThread);
    }

}
