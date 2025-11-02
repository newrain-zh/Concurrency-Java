package com.newrain.concurrency.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class TestCompareAndSwap {


    static class OptimisticLockingPlus {

        private static final int THREAD_COUNT = 100;

        private volatile int value;

        private static Unsafe unsafe;

        // value的内存偏移 相对于对象头部的偏移 不是绝对偏移
        private static final long valueOffset;

        private static final AtomicLong failure = new AtomicLong(0);

        static {
            try {
                // 通过反射获取Unsafe实例
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                unsafe      = (Unsafe) field.get(null);
                valueOffset = unsafe.objectFieldOffset(OptimisticLockingPlus.class.getDeclaredField("value"));
                System.out.println("valueOffset:" + valueOffset);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        // CAS 原子操作
        public final boolean unSafeCompareAndSwapInt(int oldValue, int newValue) {
            return unsafe.compareAndSwapInt(this, valueOffset, oldValue, newValue);
        }

        public void selfPlus() {
            while (!unSafeCompareAndSwapInt(value, value + 1)) {
                failure.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) {
        OptimisticLockingPlus cas            = new OptimisticLockingPlus();
        CountDownLatch        countDownLatch = new CountDownLatch(OptimisticLockingPlus.THREAD_COUNT);
        for (int i = 0; i < OptimisticLockingPlus.THREAD_COUNT; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    cas.selfPlus();
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("value:" + cas.value);
        System.out.println("错误次数:" + OptimisticLockingPlus.failure.get());
        int count = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 1000; j++) {
                count++;
            }
        }
        System.out.println("count=" + count);

    }

}