package com.lj.concurrency.jmm;


/**
 * @author Fox
 */
public class AtomicTest {
    private static volatile int counter = 0;
    //private static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    //synchronized (AtomicTest.class) {
                        counter++;
                    //counter.getAndIncrement();
                    //}
                }

            });
            thread.start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //思考counter=？
        System.out.println(counter);

    }

}
