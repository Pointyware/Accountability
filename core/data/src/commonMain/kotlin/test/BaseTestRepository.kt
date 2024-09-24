package org.pointyware.placeholder.core.data.test

import kotlinx.coroutines.delay

/**
 * Defines behaviors to facilitate common testing scenarios.
 */
interface BaseTestRepository {
    suspend fun <T> stubResponse(delay: Long = 0, block: suspend () -> T): T {
        delay(delay)
        return block()
    }
}
