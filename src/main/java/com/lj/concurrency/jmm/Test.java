package com.lj.concurrency.jmm;

public class Test {


    public static void main(String[] args) throws InterruptedException {
        int  i=0,j=0;
        abc:
        for (;;){
            //System.out.println(j);
            for (;;){
                j++;
                if(j !=3){
                    continue abc;
                }
                System.out.println("内层循环："+j);
                break ;
            }
            i++;
            if(i ==3){
                break ;
            }
        }
        System.out.println("主线程执行i="+i+",j="+j);
    }
}
