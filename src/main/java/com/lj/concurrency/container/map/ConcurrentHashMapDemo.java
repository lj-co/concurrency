package com.lj.concurrency.container.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 *
 */
public class ConcurrentHashMapDemo {


    public static void main(String[] args) {
        // 换成ConcurrentHashMap可以吗？
//        deal(() -> new ConcurrentHashMap<String, Integer>(), (map, words) -> {
//            for (String word : words) {
//                Integer counter = map.get(word);
//                int newValue = counter == null ? 1 : counter + 1;
//                map.put(word, newValue);
//            }
//        });


//        //正确的实现1
//        deal(() -> new ConcurrentHashMap<String, LongAdder>(), (map, list) -> {
//            //遍历集合内容
//            list.forEach(str -> {
//                //单词数累加：map中没有str的key则new LongAdder，有则进行加1
//                map.computeIfAbsent(str, (key) -> new LongAdder()).increment();
//            });
//
//        });
//        //正确的实现2
        deal(() -> new ConcurrentHashMap<String, Integer>(), (map, list) -> {
            //遍历集合内容
            list.forEach(str -> {
                //单词数累加：map中没有str的key则set(str,1)，有则set(str,Integer.sum(oldvalue,1))
                map.merge(str, 1, Integer::sum);
            });

        });
    }


    /**
     * 定义读文件的方法
     */
    private static void read(List list, int i) {
        //创建输入缓冲字符流
        try (BufferedReader bf = new BufferedReader(new FileReader((i + 1) + ".txt"))) {
            String data;
            //读取每行数据，判断是否为空
            while ((data = bf.readLine()) != null) {
                //将字母加入到集合中
                list.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 定义26个线程读26个文件并将结果放入map。map由函数式接口作为参数提供，放入map由Consumer函数式接口处理。
     *
     * @param supplier 提供者：提供map集合存放单词计数
     * @param consumer 消费者：对list（第二个参数）进行计数并存入map（第一个参数）中
     */
    private static <T> void deal(Supplier<Map<String, T>> supplier, BiConsumer<Map<String, T>, List<String>> consumer) {
        //获得map集合，用于存放单词计数
        Map<String, T> map = supplier.get();
        //利用闭锁保证26个线程都执行完任务
        CountDownLatch count = new CountDownLatch(26);
        //循环创建26个线程，读取26个文件的内容，并进行计数操作
        for (int i = 0; i < 26; i++) {
            int j = i;
            new Thread(() -> {
                List<String> list = new ArrayList();
                //读取文件
                read(list, j);
                consumer.accept(map, list);

                count.countDown();
            }).start();
        }
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }


}
