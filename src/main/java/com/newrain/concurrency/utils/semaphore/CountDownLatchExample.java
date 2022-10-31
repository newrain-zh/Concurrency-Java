package com.newrain.concurrency.utils.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @author newRain
 */
@Slf4j
public class CountDownLatchExample {


    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(100);
        //模拟100辆车进入停车场
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    log.debug("{}:来到停车场", Thread.currentThread().getName());
                    log.debug("{}: 成功进入停车场", Thread.currentThread().getName());
                    countDownLatch.countDown();
                    Thread.sleep(10000);//模拟车辆在停车场停留的时间
                    log.debug("{}:驶出停车场", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.error("error", e);
                }
            }, i + "号车");
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("完成");
    }
}
