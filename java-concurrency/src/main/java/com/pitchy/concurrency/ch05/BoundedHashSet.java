package com.pitchy.concurrency.ch05;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 固定大小的hashset
 * Created by qianshang on 2017/9/2.
 */
public class BoundedHashSet<T> {

    private final Set<T> set;

    private final Semaphore sem;

    public BoundedHashSet(Set<T> set, int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean rs = false;
        try {
            rs = set.add(o);
            return rs;
        } finally {
            if (!rs) {
                sem.release();
            }
        }
    }

    public boolean remove(Object o) {
        boolean rs = set.remove(o);
        if (rs) {
            sem.release();
        }
        return rs;
    }


}
