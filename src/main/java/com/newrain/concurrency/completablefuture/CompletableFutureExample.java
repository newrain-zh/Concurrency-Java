package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture 使用异步线程
 *
 * @author newrain
 */
@Slf4j
public class CompletableFutureExample {


    public static void main(String[] args) {
        //使用线程池
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info("hello future");
        }, executor);
        try {
            log.info("future result ={}",future.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error("error", e);
        }
        executor.shutdown();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            log.error("hello future1");
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("hello future2");
            return "hello future2";
        });
    }
}