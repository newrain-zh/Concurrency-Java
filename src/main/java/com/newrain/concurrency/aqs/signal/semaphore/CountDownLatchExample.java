package com.newrain.concurrency.aqs.signal.semaphore;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {


    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        //模拟100辆车进入停车场
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println("====" + Thread.currentThread().getName() + "来到停车场");
                    System.out.println(Thread.currentThread().getName() + "成功进入停车场");
//                    Thread.sleep(new Random().nextInt(10000));//模拟车辆在停车场停留的时间
                    Thread.sleep(10000);//模拟车辆在停车场停留的时间
                    System.out.println(Thread.currentThread().getName() + "驶出停车场");
                    countDownLatch.countDown();//释放令牌，腾出停车场车位
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
