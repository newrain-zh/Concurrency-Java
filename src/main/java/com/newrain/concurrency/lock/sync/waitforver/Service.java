package com.newrain.concurrency.lock.sync.waitforver;

import lombok.extern.slf4j.Slf4j;

/**
 * @author newRain
 * @description 用不同的监视器对象解决无限等待问题
 */
@Slf4j
public class Service {

    Object obj = new Object();

    public void methodA() {
        synchronized (obj) {
            log.debug("methodA begin");
            boolean isContinueRun = true;
            while (isContinueRun) {

            }
            log.debug("methodA end");
            System.out.println("");
        }
    }

    Object obj2 = new Object();

    public void methodB() {
        synchronized (obj2) {
            log.debug("methodB begin");
            System.out.println("");
            log.debug("methodB end");
        }
    }

    public static void main(String[] args) {
        Service service = new Service();
        ThreadA threadA = new ThreadA(service);
        threadA.start();

        ThreadB threadB = new ThreadB(service);
        threadB.start();
    }
}


class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.methodA();
    }
}

class ThreadB extends Thread {
    private Service service;

    public ThreadB(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.methodB();
    }
}