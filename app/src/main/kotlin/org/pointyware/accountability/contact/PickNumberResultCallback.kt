package org.pointyware.accountability.contact

import android.net.Uri
import androidx.activity.result.ActivityResultCallback

/**
 */
class PickNumberResultCallback(
    private val contactReceiver: ContactReceiver
): ActivityResultCallback<Uri?> {
    override fun onActivityResult(result: Uri?) {
        contactReceiver.onContactSelected(result)
    }

    interface ContactReceiver {
        fun onContactSelected(uri: Uri?)
    }
}
