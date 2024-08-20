package com.lj.concurrency.lock;

/**
 * @author: Fox
 * @Desc:
 **/
public class ReentrantLockDemo1 {
    private static  int sum = 0;
    //private static Lock lock = new ReentrantLock();
     private static TulingLock lock = new TulingLock();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"开始执行");
                //加锁
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获取锁成功");
                try {
                    // 临界区代码
                    // TODO 业务逻辑：多线程读写操作不能保证线程安全
                    for (int j = 0; j < 10000; j++) {
                        sum++;
                    }
                } finally {
                    // 解锁
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName()+"释放锁成功");
                }
            },"t"+i);
            thread.start();
        }

        Thread.sleep(2000);
        System.out.println(sum);
    }

}
