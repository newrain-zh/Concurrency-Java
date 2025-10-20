package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture thenAccept后续处理demo
 * accept（）三个方法只做最终结果的消费，注意此时返回的CompletableFuture是空返回
 */
@Slf4j
public class CompletableFutureExample7 {

    public static void main(String[] args) {
        runErrorExample();
        runRightExample();
    }

    private static void runRightExample() {
        // 开启一个异步方法
        CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
            List<String> list = new ArrayList<>(2);
            list.add("语文");
            list.add("数学");
            // 获取得到今天的所有课程
            return list;
        });
        future.thenRun(() -> log.info("runRightExample->执行完成"));
    }

    private static void runErrorExample() {
        // 开启一个异步方法
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            //模拟异常
            String s = null;
            return s.length();
        });
        // thenAccept最终处理
        future.thenRun(() -> log.info("runErrorExample->执行完成"));

    }
}