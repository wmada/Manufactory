package com.timerchina.manufactory.breaker.test;

import com.timerchina.manufactory.breaker.AbstractCircuitBreaker;
import com.timerchina.manufactory.breaker.PercentCircuitBreaker;

/**
 * @author will.ma
 * @date 2017/6/26
 */
public class PercentCircuitBreakerTest {

    // 定义熔断器，失败5次进入断开状态
    // 在半断开状态下，连续成功3次，进入闭合状态
    // 1秒后进入半断开状态
    private static AbstractCircuitBreaker breaker = new PercentCircuitBreaker(10, 3, 3, 1000);

    private static class Tester implements Runnable {

        private boolean result;

        private Tester(boolean result) {
            this.result = result;
        }

        @Override
        public void run() {
            if (!result) {
                throw new RuntimeException();
            }
        }
    }

    public static void main(String[] args) {
        showState(breaker);

        //模拟失败2次调用
        attemptCall(false, 2);
        showState(breaker);

        //模拟成功8次调用
        attemptCall(true, 8);
        showState(breaker);

        //模拟失败3次调用
        attemptCall(false, 3);
        showState(breaker);

        //这里如果再调用一次服务，正常会抛出“服务已熔断”的异常
        attemptCall(true, 1);

        //等待熔断器超时，从Open转到HalfOpen
        try {
            System.out.println("等待熔断器超时（2s）。。。");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showState(breaker);

        //模拟成功调用3次
        attemptCall(true, 2);
        //这里如果出现一次调用服务失败，熔断器会马上进入熔断状体，接下来的调用会抛出“服务已熔断”的异常
        showState(breaker);
        attemptCall(false, 1);
        showState(breaker);
        attemptCall(true, 1);

        //等待熔断器超时，从Open转到HalfOpen
        try {
            System.out.println("等待熔断器超时（2s）。。。");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showState(breaker);

        attemptCall(true, 3);
        showState(breaker);
        attemptCall(false, 1);
        showState(breaker);
        attemptCall(true, 1);
    }

    private static void attemptCall(boolean rs, int times) {
        for (int i = 0; i < times; i++) {
            try {
                breaker.run(new Tester(rs));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    private static void showState(AbstractCircuitBreaker breaker) {
        System.out.println("Breaker state is: " + (breaker.isClosed() ? "Close" : (breaker.isOpen() ? "Open" : "HalfOpen")));
    }

}
