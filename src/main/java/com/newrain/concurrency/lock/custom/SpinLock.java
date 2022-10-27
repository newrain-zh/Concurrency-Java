package com.newrain.concurrency.lock.custom;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * SpinLock是不支持重入的，即当一个线程第一次已经获取到了该锁，
 * 在锁没有被释放之前，如果又一次重新获取该锁，
 * 第二次将不能成功获取到。
 *
 * @author newRain
 * @description 自定义不可重入自旋锁
 */
public class SpinLock implements Lock {

    private final AtomicReference<Thread> owner = new AtomicReference<>();


    @Override
    public void lock() {
        Thread currentThread = Thread.currentThread();
        while (owner.compareAndSet(null, currentThread)) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (thread == owner.get()) {
            owner.set(null);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
