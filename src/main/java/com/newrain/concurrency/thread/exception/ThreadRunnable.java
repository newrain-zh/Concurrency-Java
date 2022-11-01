package com.newrain.concurrency.thread.exception;

/**
 * @author newRain
 * @description 线程自定义异常模拟
 */
public class ThreadRunnable implements Runnable {

    @Override
    public void run() {
        //模拟异常
        int parseInt = Integer.parseInt("TTTT");
    }

    public static void main(String[] args) {
        ThreadRunnable threadRunnable = new ThreadRunnable();
        Thread thread = new Thread(threadRunnable);
        //设置线程的异常类
        thread.setUncaughtExceptionHandler(new ExceptionHandlerThread());
        thread.start();
    }
}
