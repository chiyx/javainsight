package com.pitchy.concurrency.ch07;

import com.pitchy.concurrency.common.Utils;

import java.util.concurrent.*;

public class TimedRunViaFutureDemo {

    private static final ScheduledExecutorService taskExec = Executors.newScheduledThreadPool(2);

    public static void main(String[] args) {
        try {
            timeRun(() -> {
                while (true) {
                    System.out.println("hello world.");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 1, TimeUnit.SECONDS);
            TimeUnit.SECONDS.sleep(2);
            System.out.println("sleep over.");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {

        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            e.printStackTrace();
//            e.printStackTrace();
        } catch (ExecutionException e) {
            throw Utils.launderThrowable(e.getCause());
        } finally {
            task.cancel(true);
        }

    }


}
