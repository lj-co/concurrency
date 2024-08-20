package com.lj.concurrency.atomic;

import com.lj.concurrency.factory.UnsafeFactory;
import sun.misc.Unsafe;

/**
 * @author Fox
 */
public class CASTest {

    public static void main(String[] args) {
        //对象
        Entity entity = new Entity();

        Unsafe unsafe = UnsafeFactory.getUnsafe();

        //获取x在内存中的偏移地址
        long offset = UnsafeFactory.getFieldOffset(unsafe, Entity.class, "x");
        System.out.println(offset);
        boolean successful;

        // 4个参数分别是：对象实例、字段的内存偏移量、字段期望值、字段更新值
        successful = unsafe.compareAndSwapInt(entity, offset, 0, 3);
        System.out.println(successful + "\t" + entity.x);

        successful = unsafe.compareAndSwapInt(entity, offset, 3, 5);
        System.out.println(successful + "\t" + entity.x);

        successful = unsafe.compareAndSwapInt(entity, offset, 3, 8);
        System.out.println(successful + "\t" + entity.x);

    }


}


class Entity{
    int x;
}
