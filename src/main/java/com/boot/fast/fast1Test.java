package com.boot.fast;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class fast1Test {

    static private CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) {
        fast1 fast1 = new fast1();

        /**
         * 2.模拟多线程压测 vs jmeter(http请求)
         *      1.这里的for或者stream都是 伪压测  ==> 使用countdownLotch来实现真正的同时到达
         */
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {

                /**
                 * 实现真真的同时到达
                 */
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Map<String, Object> stringObjectMap = fast1.queryOrderInfo("1");
                System.out.println("thread name "+Thread.currentThread().getName() + ", result is "+stringObjectMap);

            }).start();
        }

        System.out.println("main end");
    }
}
