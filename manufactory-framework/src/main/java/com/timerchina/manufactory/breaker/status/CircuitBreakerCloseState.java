package com.timerchina.manufactory.breaker.status;

import com.timerchina.manufactory.breaker.AbstractCircuitBreaker;

/**
 * @author will.ma
 * @date 2017/6/26
 */
public class CircuitBreakerCloseState extends AbstractCircuitBreakerState {
    public CircuitBreakerCloseState(AbstractCircuitBreaker breaker) {
        super(breaker);
        init();
    }

    public AbstractCircuitBreakerState init() {
        breaker.resetFailure();
        return this;
    }

    @Override
    public void failure() {
        super.failure();

        if (breaker.reachFailureThreshold()) {
            breaker.open(false);
        }
    }
}
