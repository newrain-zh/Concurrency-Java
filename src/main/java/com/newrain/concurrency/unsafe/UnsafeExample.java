package com.newrain.concurrency.unsafe;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author newRain
 * @description Unsafe使用实例
 */
@Slf4j
public class UnsafeExample {

    static Unsafe unsafe = null;

    static long stateOffset;

    public volatile long state = 0;

    private static long objectNameOffset = 0;

    private static long staticNameOffset = 0;

    private static String staticName = "qq";

    private String objectName = "123";

    static {
        try {
            //利用反射获取unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //此处报错 不能通过该方法获取Unsafe对象
//            Unsafe unsafe = Unsafe.getUnsafe();
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(UnsafeExample.class.getDeclaredField("state"));
            objectNameOffset = unsafe.objectFieldOffset(UnsafeExample.class.getDeclaredField("objectName"));
            staticNameOffset = unsafe.staticFieldOffset(UnsafeExample.class.getDeclaredField("staticName"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("error", e);
        }
    }

    public static void main(String[] args) {
        UnsafeExample unsafeExample = new UnsafeExample();
        boolean b = unsafe.compareAndSwapLong(unsafeExample, stateOffset, 0, 1);
        log.info("b={}", b);
        log.info("stateOffset={}", stateOffset);
        //修改对象
        unsafe.putObject(unsafeExample, objectNameOffset, "haha");//直接修改
        //修改类
        unsafe.putObject(UnsafeExample.class, staticNameOffset, "hehe");//直接修改
        log.info("unsafeExample.objectName={}", unsafeExample.objectName);
        log.info("UnsafeExample.staticName={}", staticName);
    }
}