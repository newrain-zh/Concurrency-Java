package com.newrain.concurrency.keyword;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description 全局变量控制线程终止
 */
@Slf4j
public class PrintString implements Runnable {

    private boolean isContinuePrint = true;

    @Override
    public void run() {
        while (isContinuePrint) {
            log.debug("run printStringMethod threadName={}", Thread.currentThread().getName());
        }
        log.debug("exit...");
    }

    public static void main(String[] args) throws InterruptedException {
        PrintString printString = new PrintString();
        new Thread(printString).start();
        Thread.sleep(1000);
        log.debug("我要停止它！stopThread={}", Thread.currentThread().getName());
        printString.setContinuePrint(false);
    }

    public boolean isContinuePrint() {
        return isContinuePrint;
    }

    public void setContinuePrint(boolean continuePrint) {
        isContinuePrint = continuePrint;
    }


}
