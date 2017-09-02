package com.pitchy.concurrency.common;

/**
 * Created by qianshang on 2017/9/2.
 */
public class Utils {

    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else
            throw new IllegalStateException("Not unchecked", t);
    }

}
