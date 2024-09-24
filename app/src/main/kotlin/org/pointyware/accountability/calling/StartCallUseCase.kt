package org.pointyware.accountability.calling

import android.content.Context

/**
 * Opens the System dialer with the given number.
 */
class StartCallUseCase(
    private val context: Context
) {
    suspend fun invoke(callAfterDial: Boolean, number: String = "") {

    }
}
