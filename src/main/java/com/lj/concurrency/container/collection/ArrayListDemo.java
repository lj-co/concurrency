package com.lj.concurrency.container.collection;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 迭代器弱一致性:返回迭代器后，其他线程对list的增删改查对迭代器是不可见的
 */
public class ArrayListDemo {

    public static void main(String[] args) throws InterruptedException {

        //ArrayList<String> list = new ArrayList();
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList();
        list.add("fox");
        list.add("mark");
        list.add("周瑜");

        // 保证在修改线程启动前获取迭代器
        Iterator<String> itr = list.iterator();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                list.add("诸葛");
            }
        });
        t1.start();
        //t1.join();

//        while(itr.hasNext()) {
//            System.out.println(itr.next());
//        }

        for(String str: list){
            System.out.println(str);
        }

    }
}
