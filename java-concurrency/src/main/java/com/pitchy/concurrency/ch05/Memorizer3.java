package com.pitchy.concurrency.ch05;

import com.pitchy.concurrency.common.Utils;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by qianshang on 2017/9/2.
 */
public class Memorizer3<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw Utils.launderThrowable(e);
        }
    }
}
