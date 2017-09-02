package com.pitchy.concurrency.ch05;

import com.pitchy.concurrency.common.Utils;

import java.util.concurrent.*;

/**
 * Created by qianshang on 2017/9/2.
 */
public class Memorizer<A, V> implements Computable<A, V> {

    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memorizer(Computable<A, V> c) {
        this.c = c;

    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = () -> c.compute(arg);
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
                try {
                    return f.get();
                } catch (CancellationException e) {
                    cache.remove(arg, f);
                } catch (ExecutionException e) {
                    throw Utils.launderThrowable(e);
                }
            }
        }
    }
}
