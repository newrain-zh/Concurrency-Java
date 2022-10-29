package com.newrain.concurrency.thread.threadpool;

import java.util.concurrent.*;

/**
 * @author newRain
 */
public class ExceutePoolByThreadFactoryExample {


    private static Integer maxPoolSize = 100;

    static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder("ThreadFactory");


    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        Executor executor = Executors.newFixedThreadPool(10, namedThreadFactory);
        executor.execute(() -> {
            System.out.println("runing");
        });
        ExecutorService executorService = Executors.newFixedThreadPool(1, namedThreadFactory);

    }
}
