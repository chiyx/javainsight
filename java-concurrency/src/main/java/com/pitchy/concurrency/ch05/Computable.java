package com.pitchy.concurrency.ch05;

/**
 * Created by qianshang on 2017/9/2.
 */
public interface Computable<A, V> {

    V compute(A arg) throws InterruptedException;

}
