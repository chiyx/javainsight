package com.pitchy.jvm.ch03;

/**
 * 大对象直接进入老年代
 * vm参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetail -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 */
public class BigObjectToOldGen {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] alloc1, alloc2, alloc3, alloc4;
        alloc1 = new byte[2 * _1MB];
        System.out.println("alloc1 " + printAddressOf(alloc1));
        alloc2 = new byte[2 * _1MB];
        System.out.println("alloc2 " + printAddressOf(alloc2));
        alloc3 = new byte[2 * _1MB];
        System.out.println("alloc3 " + printAddressOf(alloc3));
        alloc4 = new byte[4 * _1MB];
        System.out.println("alloc4 " + printAddressOf(alloc4));
    }

    private static String printAddressOf(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }


}
