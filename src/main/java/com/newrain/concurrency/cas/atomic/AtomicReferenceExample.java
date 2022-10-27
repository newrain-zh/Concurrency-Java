package com.newrain.concurrency.cas.atomic;

import com.newrain.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author newRain
 * @description AtomicReference 使用示例
 */
@Slf4j
@ThreadSafe
public class AtomicReferenceExample {

    public static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 5);
        log.debug("count:{}", count.get());
    }
}
