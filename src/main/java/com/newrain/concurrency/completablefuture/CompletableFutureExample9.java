package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CompletableFuture thenCombine-后续处理
 * thenCombine->组合两个CompletableFuture的结果
 */
@Slf4j
public class CompletableFutureExample9 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        runRightExample();
        runErrorExample();

    }

    private static void runRightExample() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("任务1开始");
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("任务2开始");
            return 2;
        });
        CompletableFuture<Integer> thenCombineAsync = future1.thenCombineAsync(future2, (result1, result2) -> {
            log.info("任务3启动 result1={} result2={}", result1, result2);
            return result1 + result2;
        });
        log.info(String.valueOf(thenCombineAsync.get()));
    }


    private static void runErrorExample() throws ExecutionException, InterruptedException {
        // 开启一个异步方法
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("任务1开始");
            //模拟异常
            String s = null;
            System.out.println(s.length());
            return null;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("任务2开始");
            return 2;
        });
        CompletableFuture<Integer> thenCombineAsync = future1.thenCombineAsync(future2, (result1, result2) -> {
            log.info("任务3启动 result1={} result2={}", result1, result2);
            return result1 + result2;
        });
        log.info(String.valueOf(thenCombineAsync.get()));

    }
}