package com.newrain.concurrency.collections.queue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * ReentrantLock 实现阻塞队列
 * @author newRain
 * @description 阻塞队列示例
 */
@Slf4j
public class BlockingLockQueue<T> {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    private final List<T> list;

    @Getter
    private int limit;

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public BlockingLockQueue(int limit) {
        this.limit = limit;
        list = new LinkedList<>();
    }


    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        while (this.list.size() == limit) {
            notFull.await();
        }
        while (this.list.isEmpty()) {
            notEmpty.signal();
        }
        list.add(item);
        lock.unlock();
    }

    public void dequeue() throws InterruptedException {
        lock.lock();
        while (this.list.isEmpty()) {
            notEmpty.await();
        }

    }


}