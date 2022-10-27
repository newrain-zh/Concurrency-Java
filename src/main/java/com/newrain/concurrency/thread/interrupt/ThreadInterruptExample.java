package com.newrain.concurrency.thread.interrupt;

/**
 * @author newRain
 * @description 中断线程代码运行示例
 */
public class ThreadInterruptExample extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.println(" thread running");
            if (isInterrupted()) {
                System.out.println(" thread interrupt ....");
                return;
            }
        }
    }


    public static void main(String[] args) {
        Thread thread = new ThreadInterruptExample();
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();//发出中断信号
    }
}
