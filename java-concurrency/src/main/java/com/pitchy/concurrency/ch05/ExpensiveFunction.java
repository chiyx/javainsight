package com.pitchy.concurrency.ch05;

import java.math.BigInteger;

/**
 * Created by qianshang on 2017/9/2.
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // after deep thought
        return new BigInteger(arg);
    }
}
