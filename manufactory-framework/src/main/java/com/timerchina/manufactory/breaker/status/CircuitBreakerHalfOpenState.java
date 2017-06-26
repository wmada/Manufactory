package com.timerchina.manufactory.breaker.status;

import com.timerchina.manufactory.breaker.AbstractCircuitBreaker;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author will.ma
 * @date 2017/6/26
 */
public class CircuitBreakerHalfOpenState extends AbstractCircuitBreakerState {

    private final AtomicBoolean locked = new AtomicBoolean(false);

    public CircuitBreakerHalfOpenState(AbstractCircuitBreaker breaker) {
        super(breaker);
        init();
    }

    public AbstractCircuitBreakerState init() {
        breaker.resetSuccess();
        locked.set(false);
        return this;
    }

    @Override
    public void before() {
        // 为避免半开状态下多线程修改状态的问题，仅支持单线程调用。
        if (!locked.compareAndSet(false, true)) {
            super.before();
        }
    }

    @Override
    public void failure() {
        super.failure();

        locked.compareAndSet(true, false);
        breaker.open(true);
    }

    @Override
    public void after() {
        super.after();
        System.out.println("Success! " + breaker.status());

        locked.compareAndSet(true, false);
        if (breaker.reachSuccessThreshold()) {
            breaker.close(false);
        }
    }
}
