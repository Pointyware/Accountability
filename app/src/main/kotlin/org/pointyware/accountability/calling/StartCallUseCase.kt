package org.pointyware.accountability.calling

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Opens the System dialer with the given number.
 */
class StartCallUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    suspend fun invoke(callAfterDial: Boolean, number: String = "") {
        val action = if (callAfterDial) { Intent.ACTION_CALL } else { Intent.ACTION_DIAL }
        val intent = Intent(action).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            data = Uri.parse("tel:$number")
        }
        try {
            context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(context,
                "Activity Could Not Be Found to Handle Calling",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
