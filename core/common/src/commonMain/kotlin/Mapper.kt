package org.pointyware.placeholder.core.common

/**
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}
