package com.newrain.concurrency.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    // 创建非公平锁（默认），若需公平锁：new ReentrantLock(true)
    private final ReentrantLock lock = new ReentrantLock(true);

    public void doSomething() {
        // 加锁（必须手动调用，建议放在 try 外，避免未加锁却解锁）
        try {
            lock.lock();
            // 临界区代码（线程安全操作）
            System.out.println(Thread.currentThread().getName() + " 执行线程安全操作");
            Thread.sleep(1000000);
            // 可重入示例：同一线程再次获取锁
            doReentrantAction();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 解锁（必须在 finally 中调用，确保锁释放，避免死锁）
            lock.unlock();
        }
    }

    private void doReentrantAction() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 重入执行操作");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo    = new ReentrantLockDemo();

        Thread            thread1 = new Thread(demo::doSomething);
        thread1.setName("线程1");

        Thread thread2 = new Thread(demo::doSomething);
        thread2.setName("线程2");

        Thread thread3 = new Thread(demo::doSomething);
        thread3.setName("线程3");

        Thread thread4 = new Thread(demo::doSomething);
        thread4.setName("线程4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("2222");
    }
}