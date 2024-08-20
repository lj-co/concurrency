package com.lj.concurrency.jmm;


public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        int  j = 4;
        while (true) {
            j++;
            if (j == 3) {
                System.out.println("内层循环：" + j);
            }
            if(j==Integer.MAX_VALUE){
                throw new RuntimeException("int 溢出异常");
            }
        }
    }
}
