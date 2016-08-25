package org.jneis.hack.circuitbreaker.domain

class CircuitBreaker {

    private final String name
    private final Integer failureThreshold
    private final Integer timeUntilRetry

    private Integer failureCount
    private CircuitBreakerStatus status
    private Date lastOpenedTime
    private Integer openCount

    void addFailure() {
        failureCount++
    }

    Boolean hasWaitTimeExceeded() {
        System.currentTimeMillis() - timeUntilRetry > lastOpenedTime.time
    }

    Boolean hasFailureThresholdReached() {
        failureCount >= failureThreshold
    }

    void open() {
        lastOpenedTime = System.currentTimeMillis()
        status = CircuitBreakerStatus.OPEN
        openCount++
    }

    void openHalf() {
        status = CircuitBreakerStatus.HALF_OPEN
    }

    void close() {
        failureCount = 0
        status = CircuitBreakerStatus.CLOSE
    }

    Boolean isOpen() {
        status == CircuitBreakerStatus.OPEN
    }

    Boolean isClose() {
        status == CircuitBreakerStatus.CLOSE
    }

}
