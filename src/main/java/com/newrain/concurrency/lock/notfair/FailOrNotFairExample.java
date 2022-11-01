package com.newrain.concurrency.lock.notfair;

import com.newrain.concurrency.lock.utils.IncrementData;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 执行观察结果
 *
 * @author newRain
 * @description 公平和非公平demo
 */
public class FailOrNotFairExample {

    public static void main(String[] args) {
        //true：公平 false：非公平
        Lock lock = new ReentrantLock(true);
        Runnable r = () -> IncrementData.lockAndFastIncrease(lock);
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(r);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
