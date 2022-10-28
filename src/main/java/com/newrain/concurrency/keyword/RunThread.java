package com.newrain.concurrency.keyword;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description volatile 解决死循环问题
 */
@Slf4j
public class RunThread extends Thread {


//    volatile private boolean isRunning = true;

    private boolean isRunning = true;

    @Override
    public void run() {
        log.debug("进入run方法...");
        while (isRunning) {
            log.debug("running...");
        }
        log.debug("线程被停止");
    }


    public static void main(String[] args) throws InterruptedException {
        RunThread runThread = new RunThread();
        runThread.start();
        Thread.sleep(1000);
        runThread.setRunning(false);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
