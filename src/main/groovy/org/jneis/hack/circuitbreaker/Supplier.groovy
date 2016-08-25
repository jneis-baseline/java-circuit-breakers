package org.jneis.hack.circuitbreaker

interface Supplier<I, O> {

    O get(I arg)

}
