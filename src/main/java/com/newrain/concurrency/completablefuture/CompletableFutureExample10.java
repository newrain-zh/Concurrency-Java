package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CompletableFuture
 * anyof 组合处理
 */
@Slf4j
public class CompletableFutureExample10 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runRightExample();
        runErrorExample();

    }

    private static void runRightExample() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "future1执行结束";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "future2执行结束";
        });
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "future3执行结束";
        });
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(future1, future2, future3);
        //future2 sleep 时间最短 最先执行完成
        log.info((String) objectCompletableFuture.get());

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
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(future1, future2);
        log.info((String) objectCompletableFuture.get());
    }
}