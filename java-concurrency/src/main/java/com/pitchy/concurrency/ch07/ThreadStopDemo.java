package com.pitchy.concurrency.ch07;

/**
 * java 线程stop方法测试
 * Created by qianshang on 2017/10/5.
 */
public class ThreadStopDemo {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            while (true) {
                System.out.println("running");
            }
        });
        t.start();
        Thread.sleep(2000L);
        // 尝试打断
        t.interrupt();
        System.out.println("[main] interrupt t done.");
        Thread.sleep(2000L);
        // 尝试停止
        t.stop();
        System.out.println("[main] stop t done.");
        Thread.sleep(5000L);
    }

}
