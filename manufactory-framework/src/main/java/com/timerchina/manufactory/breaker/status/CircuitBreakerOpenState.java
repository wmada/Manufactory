package com.timerchina.manufactory.breaker.status;

import com.timerchina.manufactory.breaker.AbstractCircuitBreaker;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author will.ma
 * @date 2017/6/26
 */
public class CircuitBreakerOpenState extends AbstractCircuitBreakerState {

    public CircuitBreakerOpenState(AbstractCircuitBreaker breaker) {
        super(breaker);
    }

    public AbstractCircuitBreakerState init() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                breaker.halfOpen();
                timer.cancel();
            }
        }, breaker.getTimeout());
        return this;
    }
}
