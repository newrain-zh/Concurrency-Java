package com.newrain.concurrency.utils.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author newRain
 * @description CountDownLatch示例
 */
@Slf4j
public class CountDownLatchExample {
    /**
     * 用于聚合所有的统计指标
     */
    private static final Map<String, Integer> MAP = new ConcurrentHashMap<>();
    //不安全容器
//    private static final Map<String, Integer> MAP = new HashMap<>();
    /**
     * 创建计数器，这里需要统计4个指标
     */
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(4);

    public static void main(String[] args) {


        //记录开始时间
        long startTime = System.currentTimeMillis();
        Thread countUserThread = new Thread(() -> {
            try {
                log.debug("正在统计新增用户数量");
                Thread.sleep(3000);//任务执行需要3秒
                MAP.put("userNumber", 1);//保存结果值
                COUNT_DOWN_LATCH.countDown();//标记已经完成一个任务
                log.debug("统计新增用户数量完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("error:", e);
            }
        });
        Thread countOrderThread = new Thread(() -> {
            try {
                log.debug("正在统计订单数量");
                Thread.sleep(3000);//任务执行需要3秒
                MAP.put("countOrder", 2);//保存结果值
                COUNT_DOWN_LATCH.countDown();//标记已经完成一个任务
                log.debug("统计订单数量完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread countGoodsThread = new Thread(() -> {
            try {
                log.debug("正在商品销量");
                Thread.sleep(3000);//任务执行需要3秒
                MAP.put("countGoods", 3);//保存结果值
                COUNT_DOWN_LATCH.countDown();//标记已经完成一个任务
                log.debug("统计商品销量完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread countMoneyThread = new Thread(() -> {
            try {
                log.debug("正在总销售额");
                Thread.sleep(3000);//任务执行需要3秒
                MAP.put("countmoney", 4);//保存结果值
                COUNT_DOWN_LATCH.countDown();//标记已经完成一个任务
                log.debug("统计销售额完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("error", e);
            }
        });
        //启动子线程执行任务
        countUserThread.start();
        countGoodsThread.start();
        countOrderThread.start();
        countMoneyThread.start();

        try {
            //主线程等待所有统计指标执行完毕
            COUNT_DOWN_LATCH.await();
            //记录结束时间
            long endTime = System.currentTimeMillis();
            log.debug("------统计指标全部完成--------");
            log.debug("统计结果为:{}", MAP);
            log.debug("任务总执行时间为:{}秒", (endTime - startTime) / 1000);
        } catch (InterruptedException e) {
            log.error("error", e);
        }
    }
}
