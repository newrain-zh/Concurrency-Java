package com.newrain.concurrency.lock.sync.string;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zzqno on 2017-6-3.
 */
@Slf4j
public class StringService {

    public static void print(String strParm) {
        try {
            synchronized (strParm) {
                while (true) {
                    log.info("当前线程 name={}", Thread.currentThread().getName());
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StringService stringService = new StringService();
        ThreadB threadB = new ThreadB(stringService);
        threadB.setName("B");
        threadB.start();
        ThreadA threadA = new ThreadA(stringService);
        threadA.setName("A");
        threadA.start();

    }
}

class ThreadB extends Thread {

    private StringService stringService;

    public ThreadB(StringService stringService) {
        super();
        this.stringService = stringService;
    }

    @Override
    public void run() {
        StringService.print("AA");
    }
}

class ThreadA extends Thread {
    private StringService stringService;

    public ThreadA(StringService stringService) {
        super();
        this.stringService = stringService;
    }

    @Override
    public void run() {
        StringService.print("AA");
    }
}