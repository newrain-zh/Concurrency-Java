package com.newrain.concurrency.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author newRain
 * @description 生产者消费者示例
 */
public class ProducerConsumerExample {

    public static void main(String[] args) {
        //Creating shared object
        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();

        //Creating Producer and Consumer Thread
        Thread prodThread = new Thread(new Producer(sharedQueue));
        Thread consThread = new Thread(new Consumer(sharedQueue));

        //Starting producer and Consumer thread
        prodThread.start();
        consThread.start();
    }
}


/**
 * 生产者
 */
class Producer implements Runnable {

    private final BlockingQueue<Integer> sharedQueue;

    public Producer(BlockingQueue<Integer> sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Produced: " + i);
                sharedQueue.put(i);
            } catch (InterruptedException ex) {

            }
        }
    }

}

/**
 * 消费者
 */
class Consumer implements Runnable {

    private final BlockingQueue<Integer> sharedQueue;

    public Consumer(BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumed: " + sharedQueue.take());
            } catch (InterruptedException ex) {

            }
        }
    }
}
