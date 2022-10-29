package com.newrain.concurrency.lock.sync;

/**
 * Created by zzqno on 2017-6-3.
 */
public class MyThread2 extends Thread {

    private HalfSynchronizedExample halfSynchronizedExample;

    public MyThread2(HalfSynchronizedExample halfSynchronizedExample) {
        this.halfSynchronizedExample = halfSynchronizedExample;
    }

    @Override
    public void run() {
        super.run();
        this.halfSynchronizedExample.doLongTimeTask();
    }
}
