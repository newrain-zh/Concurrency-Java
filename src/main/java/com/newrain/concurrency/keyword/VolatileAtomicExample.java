package com.newrain.concurrency.keyword;

/**
 * @author newRain
 * @description volatile 不能保证原子性代码示例
 */
public class VolatileAtomicExample extends Thread {
    public volatile Integer v = 100;

    public void addCount() {
        for (int i = 0; i < 100; i++) {
            v++;
        }
        System.out.println("count=" + v);
    }

    public static void main(String[] args) {
        VolatileAtomicExample v = new VolatileAtomicExample();
        Vo vo = new Vo(v);
        Vo vo1 = new Vo(v);
        Vo vo2 = new Vo(v);
        vo.start();
        vo1.start();
        vo2.start();
        System.out.println(v.v);
        try {
            Thread.sleep(3000L);
            System.out.println("最终结果:" + v.v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class Vo extends Thread {
    VolatileAtomicExample volatileAtomicExample;

    public Vo(VolatileAtomicExample volatileAtomicExample) {
        this.volatileAtomicExample = volatileAtomicExample;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            volatileAtomicExample.addCount();
        }
    }

}
