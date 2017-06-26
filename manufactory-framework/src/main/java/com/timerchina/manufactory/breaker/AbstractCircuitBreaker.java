package com.timerchina.manufactory.breaker;

import com.timerchina.manufactory.breaker.status.AbstractCircuitBreakerState;
import com.timerchina.manufactory.breaker.status.CircuitBreakerCloseState;
import com.timerchina.manufactory.breaker.status.CircuitBreakerHalfOpenState;
import com.timerchina.manufactory.breaker.status.CircuitBreakerOpenState;

/**
 * @author will.ma
 * @date 2017/6/25
 */
public abstract class AbstractCircuitBreaker {

    private long timeout;

    private final AbstractCircuitBreakerState closeState    = new CircuitBreakerCloseState(this);
    private final AbstractCircuitBreakerState openState     = new CircuitBreakerOpenState(this);
    private final AbstractCircuitBreakerState halfOpenState = new CircuitBreakerHalfOpenState(this);

    private AbstractCircuitBreakerState currentState = closeState;

    public AbstractCircuitBreaker(long timeout) {
        if (timeout < 1) {
            throw new IllegalArgumentException();
        }
        this.timeout = timeout;
    }

    public boolean isClosed() {
        return currentState == closeState;
    }

    public boolean isOpen() {
        return currentState == openState;
    }

    public boolean isHalfOpen() {
        return currentState == halfOpenState;
    }

    public void close() {
        close(true);
    }

    public void open() {
        open(true);
    }

    public synchronized void close(boolean force) {
        if ((force || reachSuccessThreshold()) && currentState != closeState) {
            currentState = closeState.init();
            System.out.println("Change to Close state.");
        }
    }

    public synchronized void open(boolean force) {
        if ((force || reachFailureThreshold()) && currentState != openState) {
            currentState = openState.init();
            System.out.println("Change to Open state.");
        }
    }

    public synchronized void halfOpen() {
        if (currentState != halfOpenState) {
            currentState = halfOpenState.init();
            System.out.println("Change to Half Open state.");
        }
    }

    public abstract boolean reachFailureThreshold();

    public abstract boolean reachSuccessThreshold();

    public abstract void failure();

    public abstract void success();

    public abstract void resetFailure();

    public abstract void resetSuccess();

    public long getTimeout() {
        return timeout;
    }

    public void run(Runnable task) {
        currentState.before();

        try {
            task.run();
        } catch (Exception e) {
            currentState.failure();
            return;
        }
        currentState.after();
    }

    public String status() {
        return isClosed() ? "Close" : (isOpen() ? "Open" : "HalfOpen");
    }
}
