package com.newrain.concurrency.thread.create;

/**
 * @author newRain
 * @description 使用Runnable创建线程示例
 */
public class CreateThreadByRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(" thread run " + i);
        }
    }


    public static void main(String[] args) {
        System.out.println("main thread start ....");
        CreateThreadByRunnable createThreadByInterface = new CreateThreadByRunnable();
        Thread thread = new Thread(createThreadByInterface);
        thread.start();
        System.out.println("main thread end ....");

    }
}
