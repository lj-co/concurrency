package com.lj.concurrency.base;

import lombok.extern.slf4j.Slf4j;

/**
 * @author  Fox
 * 中断机制案例: 中断正常运行的线程, 不会清空中断状态
 */
@Slf4j
public class ThreadInterruptDemo {

    public static void main(String[] args)  {

        Thread t1 = new Thread(()->{
            while(true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if(interrupted) {
                    //处理逻辑
                    log.debug(" 中断状态: {}", interrupted);
                    break;
                }
            }
        }, "t1");
        t1.start();

        //中断线程t1
        t1.interrupt();
        log.debug("中断状态：{}",t1.isInterrupted());

    }
}