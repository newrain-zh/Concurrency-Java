package com.newrain.concurrency.thread.interrupt;

/**
 * Created by monster_zzq on 2016/7/7.
 */
public class ThreadInterruptExample3 implements Runnable {
    public void run() {
        while (true) {
            System.out.println(" this thread is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                break;
            }
        }
        System.out.println("this thread exiting under request....");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread();
        Thread ThreadInterruptExample3 = new Thread(thread);
        System.out.println(" start thread .... ");

        ThreadInterruptExample3.start();
        Thread.sleep(3000);

        System.out.println(" interrupt thread .... ");
        thread.interrupt();
        System.out.println("线程是否中断：" + thread.isInterrupted());
        System.out.println(" stop application ....");
    }
}
