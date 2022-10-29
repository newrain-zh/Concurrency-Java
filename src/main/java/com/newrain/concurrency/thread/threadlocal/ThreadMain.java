package com.newrain.concurrency.thread.threadlocal;

/**
 * ThreadLocal为每个使用该变量的线程提供独立的变量副本
 * 实现了变量访问在不同的线程中隔离，避免了线程安全问题。
 * <p>
 * ThreadLocal的原理
 * 构建了一个ThreadLocalMap 键为当前线程，值为你设定的值。
 *
 * @author newRain
 * @description ThreadLocal 原理
 */
public class ThreadMain {

    /**
     * 通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定其初始值
     */
    private static ThreadLocal<Integer> seqNum = ThreadLocal.withInitial(() -> 0);

    public ThreadLocal<Integer> getThreadLocal() {
        return seqNum;
    }

    /**
     * .获取下个序列值
     *
     * @return
     */
    public int getNextNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }

    public static void main(String[] args) {
        ThreadMain threadMain = new ThreadMain();
        //3个线程共享 threadMain 各自产生序列号
        TestClient t1 = new TestClient(threadMain);
        TestClient t2 = new TestClient(threadMain);
        TestClient t3 = new TestClient(threadMain);
        t1.start();
        t2.start();
        t3.start();
    }

    /**
     * 线程类
     */
    private static class TestClient extends Thread {
        private ThreadMain sn;

        public TestClient(ThreadMain sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            //4.每个线程打印出3个序列值
            for (int i = 0; i < 3; i++) {
                System.out.println("thread[" + Thread.currentThread().getName() + "] -->sn[" + sn.getNextNum() + "]");
            }
            //显示调用该方法清除线程的局部变量并不是必须的操作 但可以加快内存回收的速度
            sn.getThreadLocal().remove();//每个线程用完的时候要记得删除
        }
    }

}
