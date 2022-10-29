package com.newrain.concurrency.lock.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @date 2017-6-3
 * 一半同步一半异步
 */
@Slf4j
public class HalfSynchronizedExample {

    public void doLongTimeTask() {
        for (int i = 0; i < 100; i++) {
            log.debug("no synchronized threadName={},i={}", Thread.currentThread().getName(), i);
        }
        log.debug("-------------");
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                log.debug("synchronized threadName={},i={}", Thread.currentThread().getName(), i);
            }
        }
    }

    public static void main(String[] args) {
        HalfSynchronizedExample halfSynchronizedExample = new HalfSynchronizedExample();
        MyThread1 thread1 = new MyThread1(halfSynchronizedExample);
        MyThread2 thread2 = new MyThread2(halfSynchronizedExample);
        thread1.start();
        thread2.start();
    }

}
