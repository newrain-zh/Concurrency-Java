package com.newrain.concurrency.utils.exchanger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * @author newRain
 * @description exchanger 两个线程交换数据示例
 */
public class ExchangerExample {

    public static void main(String[] args) {
        Exchanger<Book> exchanger = new Exchanger<>();
        new Thread(new ExchangerOne(exchanger)).start();
        new Thread(new ExchangerTwo(exchanger)).start();

    }

    @Slf4j
    static class ExchangerOne implements Runnable {

        private final Exchanger<Book> exchanger;

        public ExchangerOne(Exchanger<Book> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            Book book = new Book("book one");
            try {
                Book exchange = exchanger.exchange(book);
                log.debug("book name:{}", exchange.getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Slf4j
    static class ExchangerTwo implements Runnable {

        private final Exchanger<Book> exchanger;

        public ExchangerTwo(Exchanger<Book> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            Book book = new Book("book two");
            try {
                Book exchange = exchanger.exchange(book);
                log.debug("book name:{}", exchange.getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Book {

        private final String name;

        public Book(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
