package com.newrain.concurrency.condition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author newRain
 * @description Condition 生产者 消费者示例
 */
public class ConditionExample {
    /**
     * 容器
     */
    private final LinkedList<String> buffer;
    /**
     * 容器的最大size
     */
    private final int maxSize;
    private final Lock lock;
    /**
     * 容器已满信号量
     */
    private final Condition fullCondition;
    /**
     * 容器未满信号量
     */
    private final Condition notFullCondition;

    ConditionExample(int maxSize) {
        this.maxSize = maxSize;
        buffer = new LinkedList<>();
        lock = new ReentrantLock();
        fullCondition = lock.newCondition();
        notFullCondition = lock.newCondition();
    }

    public void set(String string) throws InterruptedException {
        lock.lock();//获取锁
        try {
            while (maxSize == buffer.size()) {
                notFullCondition.await();//满了，添加的线程进入等待状态
            }
            buffer.add(string);
            fullCondition.signal();
        } finally {
            lock.unlock();//记得释放锁
        }
    }

    public String get() throws InterruptedException {
        String string;
        lock.lock();
        try {
            while (buffer.size() == 0) {
                fullCondition.await();
            }
            string = buffer.poll();
            notFullCondition.signal();
        } finally {
            lock.unlock();
        }
        return string;
    }
}
