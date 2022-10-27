package com.newrain.concurrency.cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author new Rain
 */
public class AtomicIntegerExample extends Thread {

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(count.incrementAndGet());
        }
    }

    public static void main(String[] args) {
        AtomicIntegerExample atomicIntegerExample = new AtomicIntegerExample();
        new Thread(atomicIntegerExample).start();

    }
}


