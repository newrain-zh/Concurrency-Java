package com.newrain.concurrency.thread.daemon;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description 守护线程示例
 */
@Slf4j
public class DaemonThreadExample {

    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        //设置为守护线程 必须在线程启动前调用 即 start方法之前
        threadA.setDaemon(true);
        threadB.start();
        threadA.start();
        Thread threadMain = Thread.currentThread();
        threadMain.setDaemon(true);
        log.debug("线程A是不是守护线程:{}", threadA.isDaemon());
        log.debug("线程B是不是守护线程:{}", threadB.isDaemon());
        log.debug("线程main是不是守护线程:{}", threadMain.isDaemon());
        Thread.sleep(50000);
    }

}

class ThreadB extends Thread {
    @Override
    public void run() {
        for (long i = 0; i < 5L; i++) {
            System.out.println("后台线程B第" + i + "次执行");
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadA extends Thread {
    @Override
    public void run() {
        for (long i = 0; i < 10L; i++) {
            System.out.println("后台线程A第" + i + "次执行");
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}