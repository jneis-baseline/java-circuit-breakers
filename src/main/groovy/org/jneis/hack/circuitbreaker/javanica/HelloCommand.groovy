package org.jneis.hack.circuitbreaker.javanica

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.jneis.hack.circuitbreaker.HelloSupplier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HelloCommand {

    @Autowired
    private HelloSupplier supplier

    private String fallback

    @HystrixCommand(fallbackMethod = "helloNotFound", commandProperties = [
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
    ])

    String hello(String name) {
        supplier.get(name)
    }

    @Override
    protected String helloNotFound() {
        fallback
    }
}
