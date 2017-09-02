package com.pitchy.concurrency.ch05;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持并发的通用缓存设计
 * Created by qianshang on 2017/9/2.
 */
public class Memorizer2<A, V>  implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
