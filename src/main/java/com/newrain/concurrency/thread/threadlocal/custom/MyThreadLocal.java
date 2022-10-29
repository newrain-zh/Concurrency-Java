package com.newrain.concurrency.thread.threadlocal.custom;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author newRain
 * @description 自定义ThreadLocal
 */
public class MyThreadLocal<T> {


    private Map<Thread, T> container = Collections.synchronizedMap(new HashMap<>());

    public void set(T value) {
        container.put(Thread.currentThread(), value);
    }

    public T get() {
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if (value == null && !container.containsKey(thread)) {
            value = initialValue();
            container.put(thread, value);
        }
        return value;
    }

    public void remove() {
        container.remove(Thread.currentThread());
    }


    protected T initialValue() {
        return null;
    }
}
