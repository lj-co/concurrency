package com.lj.concurrency.base.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author Fox
 * 类说明：线程休眠辅助工具类
 */
public class SleepTools {

    /**
     * 按秒休眠
     *
     * @param seconds 秒数
     */
    public static final void second(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按毫秒数休眠
     *
     * @param milliseconds 毫秒数
     */
    public static final void ms(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
