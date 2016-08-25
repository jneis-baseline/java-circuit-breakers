package org.jneis.hack.circuitbreaker.homemade

import org.jneis.hack.circuitbreaker.Supplier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Autowired
    Supplier<String, String> helloSupplier

    @Bean
    CircuitBreaker<String, String> circuitBreaker() {
        new CircuitBreaker<>(
                supplier: helloSupplier,
                fallback: 'Hello!',
                failureThreshold: 3,
                timeUntilRetry: 10000
        )
    }

}
