package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture
 * runAsync、supplyAsync 方法 使用demo
 */
@Slf4j
public class CompletableFutureExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("result={}", future.get());

        CompletableFuture<java.lang.String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "success";
        });
        Thread.sleep(8000L);
        log.info("future1 result={}", future1.getNow("error"));
    }
}