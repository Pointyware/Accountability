package org.pointyware.accountability.calling

import android.content.Context
import javax.inject.Inject

/**
 * Opens the System dialer with the given number.
 */
class StartCallUseCase @Inject constructor(
    private val context: Context
) {
    suspend fun invoke(callAfterDial: Boolean, number: String = "") {
        /*

    @Deprecated("Use StartCallUseCase instead",
        ReplaceWith("StartCallUseCase.invoke(callAfterDial, number)"))
    private fun startCall(callAfterDial: Boolean, number: String = "") {
        val action = if (callAfterDial) { Intent.ACTION_CALL } else { Intent.ACTION_DIAL }
        val intent = Intent(action).apply {
            data = Uri.parse("tel:$number")
        }
        try {
            startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(baseContext, "Activity Could Not Be Found to Handle Calling", Toast.LENGTH_LONG).show()
        }
    }
         */
    }
}
