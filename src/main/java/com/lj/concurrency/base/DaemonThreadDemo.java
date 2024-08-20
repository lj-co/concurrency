package com.lj.concurrency.base;

import com.lj.concurrency.base.utils.SleepTools;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Fox
 * @Desc: 守护线程例子
 **/
@Slf4j
public class DaemonThreadDemo {

    public static void main(String[] args) {

        log.debug("开始运行...");
        Thread t1 = new Thread(() -> {
            log.debug("开始运行...");
            SleepTools.second(3);
            log.debug("运行结束...");
        }, "t1");

        // 设置t1线程为守护线程
        t1.setDaemon(true);
        t1.start();
        SleepTools.second(1);
        log.debug("运行结束...");

    }
}
