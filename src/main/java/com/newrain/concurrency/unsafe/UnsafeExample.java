package com.newrain.concurrency.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author newRain
 * @description Unsafe使用实例
 */
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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UnsafeExample unsafeExample = new UnsafeExample();
        boolean b = unsafe.compareAndSwapLong(unsafeExample, stateOffset, 0, 1);
        System.out.println(b);
        System.out.println(stateOffset);
        unsafe.putObject(unsafeExample, objectNameOffset, "haha");//直接修改
        unsafe.putObject(UnsafeExample.class, staticNameOffset, "hehe");//直接修改
        System.out.println(unsafeExample.objectName);
        System.out.println(UnsafeExample.staticName);
    }
}
