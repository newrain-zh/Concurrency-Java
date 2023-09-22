package com.newrain.concurrency.collections.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newRain
 * @description 阻塞队列实现
 */
@Slf4j
public class BlockingQueue {

    private final List<Object> arrayList;
    /**
     * 容量git branch -M main
     */
    private final int  limit;

    public BlockingQueue(int size) {
        arrayList = new ArrayList<>(size);
        this.limit = size;
    }

    /**
     * 入列操作
     *
     * @param item
     * @throws InterruptedException
     */
    public synchronized void enqueue(Object item) throws InterruptedException {
        //达到arryList上限 调用wait() 阻塞
        while (this.arrayList.size() == this.limit) {
            log.info("add wait()");
            wait();
        }
        //达到arraylist下限 调用notifyAll() 唤醒所有阻塞线程
        if (this.arrayList.isEmpty()) {
            log.info("add notifyAll()");
            notifyAll();
        }
        this.arrayList.add(item);
    }

    public synchronized void dequeue() throws InterruptedException {
        while (this.arrayList.isEmpty()) {
            log.info("dequeue wait()");
            wait();
        }
        if (this.arrayList.size() == this.limit) {
            log.info("dequeue notifyAll()");
            notifyAll();
        }
        this.arrayList.remove(0);
    }


    public static void main(String[] args) throws InterruptedException {
        BlockingQueue blockingQueue = new BlockingQueue(5);
        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                try {
                    blockingQueue.enqueue(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        Thread.sleep(2000);
        new Thread(() -> {
            try {
                blockingQueue.dequeue();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


}