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
 * CompletableFuture thenCompose-后续处理
 * thenCompose->接收返回值并生成新的任务
 */
@Slf4j
public class CompletableFutureExample8 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        runErrorExample();
        //runRightExample();
        runRightExample1();
    }

    private static void runRightExample() throws ExecutionException, InterruptedException {
        // 开启一个异步方法
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello").thenCompose(str -> CompletableFuture.supplyAsync(() -> str + " world"));
        CompletableFuture<String> stringCompletableFuture = future.thenCompose(str -> CompletableFuture.supplyAsync(() -> str + " world"));
        log.info(future.get());
        log.info(stringCompletableFuture.get());
    }
    private static void runRightExample1() throws ExecutionException, InterruptedException {
        CompletableFuture<List<String>> total = CompletableFuture.supplyAsync(() -> {
            // 第一个任务获取美术课需要带的东西，返回一个list
            List<String> stuff = new ArrayList<>();
            stuff.add("画笔");
            stuff.add("颜料");
            return stuff;
        }).thenCompose(list -> {
            // 向第二个任务传递参数list(上一个任务美术课所需的东西list)
            return CompletableFuture.supplyAsync(() -> {
                List<String> stuff = new ArrayList<>();
                // 第二个任务获取劳技课所需的工具
                stuff.add("剪刀");
                stuff.add("折纸");
                // 合并两个list，获取课程所需所有工具
                return Stream.of(list, stuff).flatMap(Collection::stream).collect(Collectors.toList());
            });
        }).thenCompose(list -> CompletableFuture.supplyAsync(() -> {
            List<String> stuff = new ArrayList<>();
            // 第三个任务
            stuff.add("排球");
            stuff.add("篮球");
            // 合并list，获取课程所需所有工具
            return Stream.of(list, stuff).flatMap(Collection::stream).collect(Collectors.toList());
        }));

        log.info(total.join().toString());
    }

    private static void runErrorExample() {
        // 开启一个异步方法
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            //模拟异常
            String s = null;
            return s.length();
        });
        // thenCompose
        CompletableFuture<String> stringCompletableFuture = future.thenCompose(str -> CompletableFuture.supplyAsync(() -> {
            log.info("thenCompose");
            return str + " world";
        }));
    }
}