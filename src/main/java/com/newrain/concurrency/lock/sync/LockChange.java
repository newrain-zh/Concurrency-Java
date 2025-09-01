package com.newrain.concurrency.lock.sync;

/**
 * Created by newrain-zh on 2017-6-3.
 * 锁对象的改变
 */
public class LockChange {

    private String lock = "123";

    public void testMethod() {
        try {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " begin" + System.currentTimeMillis());
                lock = "456";
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " end" + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockChange lockChange = new LockChange();
        ThreadA    threadA    = new ThreadA(lockChange);
        threadA.setName("A");
        ThreadB threadB = new ThreadB(lockChange);
        threadB.setName("B");
        threadA.start();
        Thread.sleep(50);
        threadB.start();
    }


}

class ThreadA extends Thread {
    private LockChange lockChange;

    public ThreadA(LockChange lockChange) {
        this.lockChange = lockChange;
    }

    @Override
    public void run() {
        lockChange.testMethod();
    }
}

class ThreadB extends Thread {

    private LockChange lockChange;

    public ThreadB(LockChange lockChange) {
        this.lockChange = lockChange;
    }

    @Override
    public void run() {
        lockChange.testMethod();
    }
}