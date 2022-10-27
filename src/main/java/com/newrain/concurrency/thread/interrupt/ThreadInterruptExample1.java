package com.newrain.concurrency.thread.interrupt;

/**
 * @author newRain
 * @description
 */
public class ThreadInterruptExample1 implements Runnable {
    @Override
    public void run() {
        boolean stop = false;
        while (!stop) {
            System.out.println(" this thread is running...");
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 1000) {
            }
        }
        System.out.println("this thread exiting under request....");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadInterruptExample1(), "Interrupt thread");
        System.out.println(" start thread .... ");
        thread.start();
        Thread.sleep(3000);
        System.out.println(" interrupt thread .... ");
        //注意此处线程不会中断
        thread.interrupt();
        System.out.println("线程是否中断：" + thread.isInterrupted());
        //    Thread.sleep(3000);
        System.out.println("application over ....");
    }
}
