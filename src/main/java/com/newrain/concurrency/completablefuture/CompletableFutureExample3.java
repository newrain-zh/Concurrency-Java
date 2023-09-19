package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture
 * 后续处理whenComplete方法 处理demo
 * whenComplete方法作用感知结果或异常并返回相应信息
 * whenComplete方法能得到异常异常信息 但是不能修改返回信息
 */
@Slf4j
public class CompletableFutureExample3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runErrorExample();

    }

    private static void runRightExample() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 10086;
        });

        //主线程输出
        future.whenComplete((result, error) -> {
            log.info("whenComplete 拨打" + result);
            log.error("whenComplete error:", error);
        });
        future.whenCompleteAsync((result, error) -> {
            log.info("whenCompleteAsync 拨打" + result);
            log.error("whenCompleteAsync error:", error);
        });
        //使用自定义线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        future.whenCompleteAsync((result, error) -> {
            log.info("whenCompleteAsync pool 拨打" + result);
            log.error("whenCompleteAsync pool error:", error);
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
        log.info("executorService shutdown....");
    }

    private static void runErrorExample() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            String s = null;
            return s.length();
        });
        CompletableFuture<Integer> integerCompletableFuture = future.whenComplete((result, error) -> {
            log.error("whenComplete error:" + error);
            log.info("whenComplete result:" + result);
        });
        log.info(String.valueOf(integerCompletableFuture.get()));
    }
}