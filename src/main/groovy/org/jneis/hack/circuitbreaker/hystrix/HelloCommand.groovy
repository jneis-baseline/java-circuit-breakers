package org.jneis.hack.circuitbreaker.hystrix

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommand.Setter
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandProperties
import org.jneis.hack.circuitbreaker.HelloSupplier

class HelloCommand extends HystrixCommand<String> {

    private final HelloSupplier supplier = new HelloSupplier()

    private String fallback
    private String name

    HelloCommand(Integer failureThreshold, Integer timeUntilRetry, String fallback, String name) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey('hello.command.group'))
                .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
                        .withCircuitBreakerRequestVolumeThreshold(failureThreshold)
                        .withCircuitBreakerSleepWindowInMilliseconds(timeUntilRetry)))
        this.fallback = fallback
        this.name = name
    }

    @Override
    protected String run() throws Exception {
        supplier.get(name)
    }

    @Override
    protected String getFallback() {
        fallback
    }
}
