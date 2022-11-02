package com.newrain.concurrency.lock.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Date;

/**
 * @author newRain
 * @description 死锁代码示例
 */
@Slf4j
public class DeadLockExample {
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
                ThreadInfo threadInfo = mxBean.getThreadInfo(pid, Integer.MAX_VALUE);
                System.out.println(threadInfo);
            }
        }

    }
}

@Slf4j
class LockA implements Runnable {
    @Override
    public void run() {
        try {
            log.debug("LockA 开始执行");
            while (true) {
                synchronized (DeadLockExample.obj1) {
                    log.debug("LockA 锁住 obj1");
                    // 此处等待是给B能锁住机会
                    Thread.sleep(3000);
                    synchronized (DeadLockExample.obj2) {
                        log.debug("LockA 锁住 obj2");
                        // 为测试，占用了就不放
                        Thread.sleep(60 * 1000);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

@Slf4j
class LockB implements Runnable {
    @Override
    public void run() {
        try {
            log.debug("LockB 开始执行");
            while (true) {
                synchronized (DeadLockExample.obj2) {
                    log.debug("LockB 锁住 obj2");
                    System.out.println(new Date().toString() + " ");
                    // 此处等待是给A能锁住机会
                    Thread.sleep(3000);
                    synchronized (DeadLockExample.obj1) {
                        log.debug("LockB 锁住 obj1");
                        // 为测试，占用了就不放
                        Thread.sleep(60 * 1000);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
