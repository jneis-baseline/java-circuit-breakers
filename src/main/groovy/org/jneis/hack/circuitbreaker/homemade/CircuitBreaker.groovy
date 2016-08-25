package org.jneis.hack.circuitbreaker.homemade

import groovy.util.logging.Slf4j
import org.jneis.hack.circuitbreaker.Supplier

@Slf4j
class CircuitBreaker<I, O> implements Supplier<I, O> {

    private Supplier<I, O> supplier
    private O fallback
    private Integer failureThreshold
    private Integer timeUntilRetry

    private Integer failureCount
    private Long lastOpenedTime
    private String status

    CircuitBreaker() {
        close() // initial state
    }

    @Override
    O get(I arg) {

        if (isClose()) {
            callSupplier(arg)

        } else if (isOpen() & hasWaitTimeExceeded()) {
            log.warn('retrying')
            openHalf()
            callSupplier(arg)

        } else if (isOpen()) {
            fallback

        } else {
            if (!hasFailureThresholdReached()) {
                log.warn('closing circuit')
                close()
            }
            callSupplier(arg)
        }
    }

    private O callSupplier(I arg) {
        try {
            supplier.get(arg)

        } catch(Exception e) {
            log.warn("supplier failure: ${e.message}")

            failureCount++

            if (hasFailureThresholdReached()) {
                log.warn('circuit opening')
                open()
            }

            fallback
        }
    }

    private Boolean hasWaitTimeExceeded() {
        System.currentTimeMillis() - timeUntilRetry > lastOpenedTime
    }

    private Boolean hasFailureThresholdReached() {
        failureCount >= failureThreshold
    }

    private void open() {
        lastOpenedTime = System.currentTimeMillis()
        status = 'open'
    }

    private void openHalf() {
        status = 'half-open'
    }

    private void close() {
        failureCount = 0
        status = 'close'
    }

    private Boolean isOpen() {
        'open'.equals status
    }

    private Boolean isClose() {
        'close'.equals status
    }

}
