package org.jneis.hack.circuitbreaker.hystrix

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController('hystrix')
@RequestMapping('hystrix')
class Client {

    @RequestMapping(path = 'hello', method = RequestMethod.GET)
    String hello(@RequestParam(value = 'name', defaultValue = '') String name) {
        def failureThreshold = 3
        def timeUntilRetry = 10000
        def fallback = '¡Holaquetal!'

        new HelloCommand(failureThreshold, timeUntilRetry, fallback, name).execute()
    }

}
