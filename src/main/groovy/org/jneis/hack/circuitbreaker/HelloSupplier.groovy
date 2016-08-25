package org.jneis.hack.circuitbreaker

import org.springframework.stereotype.Service

@Service
class HelloSupplier implements Supplier<String, String> {

    @Override
    String get(String name) {
        if ('failure'.equals(name)) {
            throw new RuntimeException('hello failed')
        }

        "Hello, ${name}!"
    }

}
