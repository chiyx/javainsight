package com.pitchy.jvm.ch02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 使用 Unsafe 分配本机内存
 * jvm参数: -xmx20M -XX:-MaxDirectMemorySize=10M
 * jdk8下不起作用，需要调查下
 * Created by qianshang on 2017/7/23.
 */
public class DirectMemoryOOM {

    private static final int UNIT_MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        int i = 0;
        while (true) {
            long rs = unsafe.allocateMemory(UNIT_MB);
            System.out.println(rs);
//            System.out.println((++i) + "MB allocated");
        }


    }

}
