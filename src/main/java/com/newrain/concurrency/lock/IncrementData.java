package com.newrain.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

/**
 * 用于测试
 *
 * @author newRain
 * @description 加锁工具类
 */
@Slf4j
public class IncrementData {

    public static int sum = 0;

    public static void lockAndFastIncrease(Lock lock) {
        lock.lock();
        try {
            log.info("{} -- 抢占锁", Thread.currentThread().getName());
            sum++;
        } finally {
            lock.unlock();
            log.info("{} -- 释放锁", Thread.currentThread().getName());
        }
    }

    /**
     * 用于演示可中断锁
     *
     * @param lock
     */
    public static void lockInterruptiblyAndIncrease(Lock lock) {
        log.info("{} -- 开始抢占锁", Thread.currentThread());
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            //e.printStackTrace();
            log.info("{} -- 抢占锁失败", Thread.currentThread());
            return;
        }
        try {
            log.info("{} -- 抢到了锁 同步执行一秒", Thread.currentThread());
            Thread.sleep(5000);
            sum++;
            if (Thread.currentThread().isInterrupted()) {
                log.info("{} -- 同步执行被中断", Thread.currentThread());
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
