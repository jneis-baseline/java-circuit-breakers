package org.jneis.hack.circuitbreaker.homemade

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('homemade')
class Client {

    @Autowired
    CircuitBreaker<String, String> circuitBreaker

    @RequestMapping(path = 'hello', method = RequestMethod.GET)
    String hello(@RequestParam(value = 'name', defaultValue = '') String name) {
        circuitBreaker.get(name)
    }

}
