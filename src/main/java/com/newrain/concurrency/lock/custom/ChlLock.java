package com.newrain.concurrency.lock.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author newRain
 * @description chl锁
 */
@Slf4j
public class ChlLock implements Lock {

    private static ThreadLocal<Node> currNodeLocal = new ThreadLocal<>();

    private AtomicReference<Node> tail = new AtomicReference<>(null);

    public ChlLock() {
        tail.getAndSet(Node.EMPTY);
    }

    @Override
    public void lock() {
        Node currNode = new Node(null, true);
        Node prevNode = tail.get();
        //将当前节点插入队列尾部
        while (!tail.compareAndSet(prevNode, currNode)) {
            prevNode = tail.get();
        }
        //设置前驱节点
        currNode.setPrevNode(prevNode);
        while (currNode.getPrevNode().getPrevNode().isLocked()) {
            Thread.yield();
        }
        log.info("{} 获得锁", Thread.currentThread());
        //保存当前节点 释放锁时要用到
        currNodeLocal.set(currNode);
    }

    @Override
    public void unlock() {
        //获取当前节点
        Node curNode = currNodeLocal.get();
        curNode.setLocked(false);
        curNode.setPrevNode(null);
        //释放当前节点
        currNodeLocal.set(null);

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

    static class Node {

        public Node prevNode;//前驱节点
        /**
         * true:当前线程抢占锁或者已经占有锁
         * false: 当前线程释放锁，下一个线程可以占有锁
         */
        volatile boolean locked;

        public Node(Node prevNode, boolean locked) {
            this.prevNode = prevNode;
            this.locked = locked;
        }

        public static final Node EMPTY = new Node(null, false);

        public Node getPrevNode() {
            return prevNode;
        }

        public void setPrevNode(Node prevNode) {
            this.prevNode = prevNode;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }
    }
}
