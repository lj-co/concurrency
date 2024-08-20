package com.lj.concurrency.container.map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Fox
 * @Desc:
 **/
public class WordTest {


    public static void main(String[] args) throws IOException {
        //生成测试文件
        WordTest test = new WordTest();
        test.produceData();
    }


    /**
     * 生成测试文件
     * @throws IOException
     */
    public void produceData() throws IOException {
        //定义26个字母的字符串
        String data="abcdefghijklmnopqrstuvwxyz";
        List<String> list=new ArrayList<>();
        //循环遍历26个字母，每个字母循环200次，最后将5200个字母放入集合
        for (int i = 0; i < data.length(); i++) {
            for (int j = 0; j < 200; j++) {
                list.add(String.valueOf(data.charAt(i)));
            }
        }
        //将集合打乱
        Collections.shuffle(list);
        //遍历26次。每次取出集合中的200个元素加上“换行符”放入文件中
        for (int i = 0; i < 26; i++) {
            try(FileWriter fw=new FileWriter((i+1)+".txt")){
                fw.write(list.subList(i*200,(i+1)*200).stream().collect(Collectors.joining("\n")));
            }
        }
    }


}
