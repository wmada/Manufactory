package com.timerchina.manufactory.breaker.status;

import com.timerchina.manufactory.breaker.AbstractCircuitBreaker;

/**
 * @author will.ma
 * @date 2017/6/25
 */
public class AbstractCircuitBreakerState {

    AbstractCircuitBreaker breaker;

    AbstractCircuitBreakerState(AbstractCircuitBreaker breaker) {
        this.breaker = breaker;
    }

    public AbstractCircuitBreakerState init() {
        return this;
    }

    public void before() {
        if (breaker.isOpen()) {
            throw new RuntimeException("Service Currently Unavailable");
        }
    }

    public void after() {
        breaker.success();
    }

    public void failure() {
        breaker.failure();
        System.out.println("Failure! " + breaker.status());
    }

}
