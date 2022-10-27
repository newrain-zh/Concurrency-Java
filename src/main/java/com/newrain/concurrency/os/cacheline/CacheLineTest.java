package com.newrain.concurrency.os.cacheline;

/**
 * @author newRain
 * @description 缓存行性能测试
 */
public class CacheLineTest {
    static final int LINE_NUM = 2048;
    static final int COLUM_NUM = 2048;

    public static void main(String[] args) {
        test1();
        test2();
    }

    /**
     * 顺序
     */
    public static void test1() {
        long[][] array = new long[LINE_NUM][COLUM_NUM];
        long start = System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COLUM_NUM; j++) {
                array[i][j] = i * 2 + j;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("test1 cache time:" + (end - start));
    }

    /**
     * 乱序
     */
    public static void test2() {
        long[][] array = new long[LINE_NUM][COLUM_NUM];
        long start = System.currentTimeMillis();
        for (int i = 0; i < LINE_NUM; i++) {
            for (int j = 0; j < COLUM_NUM; j++) {
                array[j][i] = i * 2 + j;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("test2 cache time:" + (end - start));
    }

}
