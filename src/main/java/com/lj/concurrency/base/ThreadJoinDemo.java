package com.lj.concurrency.base;

import com.lj.concurrency.base.utils.SleepTools;
import lombok.extern.slf4j.Slf4j;

/**
 * join的测试案例
 */
@Slf4j
public class ThreadJoinDemo {

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        log.debug("开始执行");

        Thread t1 = new Thread(() -> {
            log.debug("开始执行");
            SleepTools.second(1);
            count = 5;
            log.debug("执行完成");
        },"t1");
        t1.start();

//        SleepTools.second(1);

        t1.join(500);

        log.debug("结果为:{}", count);
        log.debug("执行完成");


    }
}
