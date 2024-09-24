package org.pointyware.accountability.core.common

/**
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}
