package com.pitchy.concurrency.ch07;

import com.pitchy.concurrency.common.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by qianshang on 2017/10/6.
 */
public class TimedRunViaJoinDemo {

    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        try {
            timedRun(() -> {
                while (true) {
                    System.out.println("hello world.");
                }
            }, 1, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("sleep over.");
        } catch (Throwable e) {
           e.printStackTrace();
        }

    }

    public static void timedRun(final Runnable r, long timeout,
                                TimeUnit unit) throws InterruptedException {

        class RethrowableTask implements Runnable {

            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable e) {
                    t = e;
                }
            }

            void rethrow() {
                if (t != null) {
                    throw Utils.launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(taskThread::interrupt, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }


}
