package com.newrain.concurrency.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture
 * 后续处理handle方法 demo
 * handle方法 捕获结果或异常并返回新结果
 */
@Slf4j
public class CompletableFutureExample4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        errorExample();
    }

    private static void rightExample(){
        // 开启一个异步方法
        CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
            List<String> list = new ArrayList<>();
            list.add("语文");
            list.add("数学");
            // 获取得到今天的所有课程
            return list;
        });
        // 使用handle()方法接收list数据和error异常
        CompletableFuture<Integer> future2 = future.handle((list, error) -> {
            log.error("error:", error);
            // 如果报错，就打印出异常
            // 如果不报错，返回一个包含Integer的全新的CompletableFuture
            return list.size();
            // 注意这里的两个CompletableFuture包含的返回类型不同
        });
    }
    private  static void errorExample() throws ExecutionException, InterruptedException {
        // 开启一个异步方法
        CompletableFuture<List> future = CompletableFuture.supplyAsync(() -> {
            return null;
        });
        // 使用handleAsync接受异常处理
        CompletableFuture<String> future2 = future.handleAsync((list, error) -> {
            log.error("error:", error);
            if (error != null){
                return "错误";
            }
            return "正确";
        });
        log.info(future2.get());
    }
}