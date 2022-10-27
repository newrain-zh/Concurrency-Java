package com.newrain.concurrency.lock.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Date;

/**
 * @author newRain
 * @description 死锁代码示例
 */
public class LockExample {
    public static String obj1 = "obj1";
    public static String obj2 = "obj2";
    public static ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();

    public static void main(String[] args) throws InterruptedException {
        LockA la = new LockA();
        new Thread(la).start();
        LockB lb = new LockB();
        new Thread(lb).start();
        Thread.sleep(5000);
        long[] deadlockedThreads = mxBean.findDeadlockedThreads();
        if (deadlockedThreads.length > 0) {
            for (long pid : deadlockedThreads) {
                ThreadInfo threadInfo = mxBean.getThreadInfo(pid,Integer.MAX_VALUE);
                System.out.println(threadInfo);
            }
        }

    }
}

class LockA implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(new Date().toString() + " LockA 开始执行");
            while (true) {
                synchronized (LockExample.obj1) {
                    System.out.println(new Date().toString() + " LockA 锁住 obj1");
                    Thread.sleep(3000); // 此处等待是给B能锁住机会
                    synchronized (LockExample.obj2) {
                        System.out.println(new Date().toString() + " LockA 锁住 obj2");
                        Thread.sleep(60 * 1000); // 为测试，占用了就不放
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class LockB implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println(new Date().toString() + " LockB 开始执行");
            while (true) {
                synchronized (LockExample.obj2) {
                    System.out.println(new Date().toString() + " LockB 锁住 obj2");
                    Thread.sleep(3000); // 此处等待是给A能锁住机会
                    synchronized (LockExample.obj1) {
                        System.out.println(new Date().toString() + " LockB 锁住 obj1");
                        Thread.sleep(60 * 1000); // 为测试，占用了就不放
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
