package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture thenAccept后续处理demo
 * accept（）三个方法只做最终结果的消费，注意此时返回的CompletableFuture是空返回
 */
@Slf4j
public class CompletableFutureExample5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        runErrorExample();
    }

    private static void runRightExample() {
        // 开启一个异步方法
        CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
            List<String> list = new ArrayList<>();
            list.add("语文");
            list.add("数学");
            // 获取得到今天的所有课程
            return list;
        });
        // thenAccept最终处理
        future.thenAccept((list) -> {
            log.info("thenAccept={}", list.size());
        });
    }

    private static void runErrorExample() throws ExecutionException, InterruptedException {
        // 开启一个异步方法
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            //模拟异常
            String s = null;
            return s.length();
        });
        // thenAccept最终处理
        future.thenAccept((result) -> {
            log.info("thenAccept={}", result);//无输出
        });
        //执行完成是以抛出异常的形式完成，那么可以通过completeExceptionally方法实现。
        // 当调用get方法后，会抛出一个异常。
        future.completeExceptionally(new Throwable("执行错误"));
        //异常处理 该处可以错误日志 并给出一个默认值
        CompletableFuture<Integer> exceptionally = future.exceptionally(throwable -> {
            log.error(throwable.getMessage());
            return -1;
        });
        log.info("runErrorExample result={}", exceptionally.get());
    }
}