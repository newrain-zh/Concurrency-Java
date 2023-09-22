package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * CompletableFuture
 * allOf 组合处理
 */
@Slf4j
public class CompletableFutureExample11 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runRightExample();
        runErrorExample();
        Thread.sleep(5000000);

    }

    private static void runRightExample() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("任务1开始");
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("任务2开始");
            return 2;
        });

        List<CompletableFuture<Integer>> completableFutures = Arrays.asList(future1, future2);
        CompletableFuture<Void> resultantCf = CompletableFuture.allOf(future1, future2);
        CompletableFuture<List<Integer>> listCompletableFuture = resultantCf.thenApply(t -> completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        log.info("result" + listCompletableFuture.get());//result[1, 2]

    }


    private static void runErrorExample() throws ExecutionException, InterruptedException {
        // 开启一个异步方法
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            log.info("任务1开始");
            //模拟异常
            String s = null;
            System.out.println(s.length());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 0;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("任务2开始");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 2;
        });
        CompletableFuture<Void> objectCompletableFuture = CompletableFuture.allOf(future1, future2);
    }
}