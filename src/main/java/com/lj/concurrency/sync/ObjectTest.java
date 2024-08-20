package com.lj.concurrency.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author  Fox
 *
 * 关闭指针压缩（-XX:-UseCompressedOops）
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {

        Object obj = new Test();
        //查看对象内部信息
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

    }
}

class Test{
    private long  p;
}
