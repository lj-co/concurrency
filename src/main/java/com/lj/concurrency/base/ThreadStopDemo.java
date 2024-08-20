package com.lj.concurrency.base;


import com.lj.concurrency.base.utils.SleepTools;

/**
 * @author: Fox
 * @Desc: 线程停止案例: 不安全的stop方法
 **/
public class ThreadStopDemo {

    private static final Object lock = new Object();
    private static int account1 = 1000;
    private static int account2 = 0;

    public static void main(String[] args)  {
        Thread threadA = new Thread(new TransferTask(),"threadA");
        threadA.start();

        // 等待线程A开始执行
        SleepTools.ms(50);
        // 假设在转账过程中，我们强制停止了线程A
        threadA.stop();

        //验证锁是否释放
        synchronized (lock){
            System.out.println("主线程加锁成功");
        }
    }

    static class TransferTask implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                try{
                    System.out.println("开始转账...");
                    // 1号账户减少100元
                    account1 -= 100;
                    // 休眠100ms
                    SleepTools.ms(100);
                    // 假设在这里线程被stop了，那么2号账户将不会增加，且锁会被异常释放
                    System.out.println("1号账户余额: " + account1);
                    account2 += 100; // 2号账户增加100元
                    System.out.println("2号账户余额: " + account2);
                    System.out.println("转账结束...");
                }catch (Throwable t){
                    System.out.println("线程A结束执行");
                    t.printStackTrace();
                }

            }
        }
    }
}
