package com.newrain.concurrency.lock.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description synchronized 使用示例
 */
@Slf4j
public class SyncExample {

    synchronized public void addByKey() {
        log.debug("{}:addByKey start...", Thread.currentThread().getName());
        System.out.println("" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("{}:addByKey end...", Thread.currentThread().getName());
    }

    public void addByThis() {
        /*   synchronized (this){
            System.out.println("addByThis");
        }*/
        log.debug("{}: addByThis start", Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("{}: addByThis end", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        SyncExample syncTest = new SyncExample();
        TestA testA = new TestA(syncTest);
        testA.setName("A");
        TestB testB = new TestB(syncTest);
        testB.setName("B");
        testB.start();
        testA.start();

    }

}

class TestA extends Thread {
    private SyncExample syncTest;

    TestA(SyncExample syncTest) {
        this.syncTest = syncTest;
    }

    @Override
    public void run() {
        syncTest.addByKey();
    }
}

class TestB extends Thread {
    private SyncExample syncTest;

    TestB(SyncExample syncTest) {
        this.syncTest = syncTest;
    }

    @Override
    public void run() {
        syncTest.addByKey();
    }
}
