package com.newrain.concurrency.producerconsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newRain
 * @description 生产者消费者示例
 */
public class ProducerAndConsumerTest {


    public static void main(String[] args) {
        //Creating shared object
        List<Integer> list = new ArrayList();

        //Creating Producer and Consumer Thread
        Thread prodThread = new Thread(new ProducerTest(list));
        Thread consThread = new Thread(new ConsumerTest(list));

        //Starting producer and Consumer thread
        prodThread.start();
        consThread.start();
    }
}

@Slf4j
class ProducerTest implements Runnable {

    private final List<Integer> arrayList;

    public ProducerTest(List<Integer> arrayList) {
        this.arrayList = arrayList;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            log.debug("Produced:{}", i);
            arrayList.add(i);
        }
    }

}

@Slf4j
class ConsumerTest implements Runnable {

    private final List<Integer> arrayList;

    public ConsumerTest(List<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < arrayList.size(); i++) {
                log.debug("Consumed:{}", arrayList.get(i));
                arrayList.remove(i);
            }
        }
    }
}
