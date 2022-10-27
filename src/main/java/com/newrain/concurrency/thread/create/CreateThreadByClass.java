package com.newrain.concurrency.thread.create;

/**
 * @author newRain
 * @description 使用Thead类创建线程
 */
public class CreateThreadByClass extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(" run " + i);
        }
    }

    public static void main(String[] args) {
        System.out.println("main thread start ....");
        CreateThreadByClass threadByClass = new CreateThreadByClass();
        threadByClass.start();
        System.out.println("main thread end ....");
    }
}
