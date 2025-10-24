package com.newrain.concurrency.thread.threadpool;

// 模拟线程复用
public class PoolDemo {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getId() + " runnable");
        Worker   worker   = new Worker(runnable);
        Thread   t        = worker.thread;
        t.start();
    }

    static class Worker implements Runnable {

        Runnable firstTask;
        final Thread thread;

        public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
            this.thread    = new Thread(this); // 传入 worker对象
        }

        public void run() {
            Runnable task = this.firstTask;
            this.firstTask = null;
            while (task != null) {
                System.out.println(Thread.currentThread().getId() + " worker run");
                task.run();
                task = null;
            }
        }
    }

}