package com.pitchy.concurrency.ch05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * Exchanger类的使用例子
 * Created by qianshang on 2017/9/2.
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        new Consumer(exchanger).start();
        new Producter(exchanger).start();
    }

}

class Producter extends Thread {

    List<Integer> list = new ArrayList<>();

    Exchanger<List<Integer>> exchanger = null;

    public Producter(Exchanger<List<Integer>> exchanger) {
        super();
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            list.clear();

            System.out.println("exchange " + i);
            list.add(rand.nextInt(10000));
            list.add(rand.nextInt(10000));
            list.add(rand.nextInt(10000));
            list.add(rand.nextInt(10000));
            list.add(rand.nextInt(10000));
            try {
                list = exchanger.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Consumer extends Thread {

    List<Integer> list = new ArrayList<>();
    Exchanger<List<Integer>> exchanger = null;

    public Consumer(Exchanger<List<Integer>> exchanger) {
        super();
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                list = exchanger.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(list);
        }
    }

}
