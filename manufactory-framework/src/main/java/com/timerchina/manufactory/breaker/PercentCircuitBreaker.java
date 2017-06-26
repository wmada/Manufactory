package com.timerchina.manufactory.breaker;

/**
 * @author will.ma
 * @date 2017/6/27
 */
public class PercentCircuitBreaker extends AbstractCircuitBreaker {

    private final boolean[] queue;

    private int pointer = 0;
    private int size;
    private int successThreshold;
    private int failureThreshold;
    private int successCount;
    private int failureCount;

    public PercentCircuitBreaker(int size, int successThreshold, int failureThreshold, long timeout) {
        super(timeout);
        if (size < 1 || successThreshold < 1 || successThreshold > size) {
            throw new IllegalArgumentException();
        }

        this.size = size;
        this.successThreshold = successThreshold;
        this.failureThreshold = failureThreshold;
        successCount = size;
        failureCount = 0;

        queue = new boolean[size];
        for (int i = 0; i < size; i++) {
            queue[i] = true;
        }
    }

    @Override
    public boolean reachFailureThreshold() {
        return failureCount >= failureThreshold;
    }

    @Override
    public boolean reachSuccessThreshold() {
        return successCount >= successThreshold;
    }

    @Override
    public String status() {
        if (isClosed()) {
            return super.status() + " Failure percent(" + failureCount + "/" + failureThreshold + ")";
        } else if (isHalfOpen()) {
            return super.status() + " Success percent(" + successCount + "/" + successThreshold + ")";
        } else {
            return super.status();
        }
    }

    @Override
    public void failure() {
        if (queue[pointer]) {
            queue[pointer] = false;
            successCount--;
            failureCount++;
        }
        pointer = (pointer + 1) % size;
    }

    @Override
    public void success() {
        if (!queue[pointer]) {
            queue[pointer] = true;
            successCount++;
            failureCount--;
        }
        pointer = (pointer + 1) % size;
    }

    @Override
    public void resetFailure() {
        successCount = size;
        failureCount = 0;

        for (int i = 0; i < size; i++) {
            queue[i] = true;
        }
    }

    @Override
    public void resetSuccess() {
        successCount = 0;
        failureCount = size;

        for (int i = 0; i < size; i++) {
            queue[i] = false;
        }
    }
}
