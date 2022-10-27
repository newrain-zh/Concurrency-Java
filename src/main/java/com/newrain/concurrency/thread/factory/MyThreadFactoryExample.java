package com.newrain.concurrency.thread.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author newRain
 * @description ThreadFactory example
 */
public class MyThreadFactoryExample implements ThreadFactory {

    private int counter;
    private String name;
    private List<String> stats;

    public MyThreadFactoryExample(String name) {
        counter = 0;
        this.name = name;
        stats = new ArrayList<>();
    }

    @Override
    public Thread newThread(Runnable run) {
        Thread t = new Thread(run, name + "-Thread-" + counter);
        counter++;
        stats.add(String.format("Created thread %d with name %s on%s\n", t.getId(), t.getName(), new Date()));
        return t;
    }
    public String getStas() {
        StringBuilder buffer = new StringBuilder();
        for (String stat : stats) {
            buffer.append(stat);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new MyThreadFactoryExample("demo1-pool-%d");


        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
        singleThreadPool.shutdown();
    }
}
