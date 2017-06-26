package com.timerchina.manufactory.breaker;

/**
 * @author will.ma
 * @date 2017/6/27
 */
public class CountCircuitBreaker extends AbstractCircuitBreaker {

    private int failureCount = 0;
    private int successCount = 0;
    private int failureThreshold;
    private int successThreshold;

    public CountCircuitBreaker(int failureThreshold, int successThreshold, long timeout) {
        super(timeout);
        if (failureThreshold < 1 || successThreshold < 1) {
            throw new IllegalArgumentException();
        }
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
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
            return super.status() + " Failure info(" + failureCount + "/" + failureThreshold + ")";
        } else if (isHalfOpen()) {
            return super.status() + " Success info(" + successCount + "/" + successThreshold + ")";
        } else {
            return super.status();
        }
    }

    public void failure() {
        failureCount++;
    }

    public void success() {
        successCount++;
    }

    public void resetFailure() {
        failureCount = 0;
    }

    public void resetSuccess() {
        successCount = 0;
    }

}
