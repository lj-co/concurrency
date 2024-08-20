package com.lj.concurrency.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author  Fox
 *  基于AQS实现一把独占锁
 */
public class TulingLock extends AbstractQueuedSynchronizer{

    @Override
    protected boolean tryAcquire(int unused) {
        //cas 加锁  state=0
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }

        return false;
    }

    @Override
    protected boolean tryRelease(int unused) {
        //释放锁
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    public void lock() {
        //获取资源
        acquire(1);
    }

    public boolean tryLock() {
        return tryAcquire(1);
    }

    public void unlock() {
        //释放资源
        release(1);
    }

    public boolean isLocked() {
        return getState() != 0;
    }


}