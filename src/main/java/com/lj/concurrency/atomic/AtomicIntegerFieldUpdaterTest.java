package com.lj.concurrency.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Fox
 */
public class AtomicIntegerFieldUpdaterTest {


    public static class Candidate {
        //字段必须是volatile类型
         volatile int score = 0;

    }

    public static final AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");


    public static void main(String[] args) throws InterruptedException {

        final Candidate candidate = new Candidate();

        Thread[] t = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            t[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Math.random() > 0.4) {

                        scoreUpdater.incrementAndGet(candidate);

                    }
                }
            });
            t[i].start();
        }
        for (int i = 0; i < 10000; i++) {
            t[i].join();
        }
        System.out.println("AtomicIntegerFieldUpdater Score=" + candidate.score);

    }
}