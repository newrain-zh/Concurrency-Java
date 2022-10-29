package com.newrain.concurrency.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author newrain
 * @description 并行流
 */
public class StreamExample {


    public static void main(String[] args) {
        List<Integer> collect = Stream.of(1, 2, 3, 4, 6, 7, 8).collect(Collectors.toList());
        System.out.println("计算开始");
        collect.parallelStream().forEach(v -> {
            try {
                System.out.println("[" + v + "]" + "计算中");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("计算结束");
    }
}
