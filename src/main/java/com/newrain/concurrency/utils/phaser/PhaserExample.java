package com.newrain.concurrency.utils.phaser;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/**
 * @author newRain
 * @description Phaser 使用示例
 */
public class PhaserExample {

    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        for (int i = 0; i < 10; i++) {
            phaser.register();                  // 注册各个参与者线程
            new Thread(new Job(phaser), "Thread-" + i).start();
        }
    }


    @Slf4j
    static class Job implements Runnable {
        private final Phaser phaser;

        Job(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            int i = phaser.arriveAndAwaitAdvance();     // 等待其它参与者线程到达
            // do something
            log.debug("{}: 执行完任务，当前phase ={}", Thread.currentThread().getName(), i);
        }
    }
}
