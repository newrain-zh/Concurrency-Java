package com.newrain.concurrency.utils.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author newRain
 * @description Semaphore使用示例
 */
@Slf4j
public class SemaphoreExample {
    private static final Semaphore SEMAPHORE = new Semaphore(10, true);

    public static void main(String[] args) {
        //模拟100辆车进入停车场
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    log.debug("{}:来到停车场", Thread.currentThread().getName());
                    SEMAPHORE.acquire();//获取令牌尝试进入停车场
                    log.debug("{}: 成功进入停车场", Thread.currentThread().getName());
                    Thread.sleep(5000);//模拟车辆在停车场停留的时间
                    SEMAPHORE.release();//释放令牌，腾出停车场车位
                    log.debug("{}:驶出停车场", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.error("error:", e);
                }
            }, i + "号车");
            thread.start();
        }
        log.debug("主线程完成");
    }
}
