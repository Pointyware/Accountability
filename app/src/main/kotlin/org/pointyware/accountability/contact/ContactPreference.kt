package org.pointyware.accountability.contact

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.AttributeSet
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.preference.Preference.SummaryProvider
import org.pointyware.accountability.R
import org.pointyware.accountability.permission.PermissionPreference
import timber.log.Timber

/**
 * This preference will trigger a contact selection activity and receive the result.
 * It additionally provides a button to clear the current contact.
 */
class ContactPreference(
    context: Context,
    attrs: AttributeSet
): PermissionPreference(context, attrs), PickNumberResultCallback.ContactReceiver {

    companion object {
        fun numberKeyFromRoot(key: String): String {
            return "$key:uri"
        }
    }

    var contactLauncher: ActivityResultLauncher<Unit>? = null

    init {
        this.summaryProvider = SummaryProvider<ContactPreference> {
            context.resources.getString(R.string.summary_emergency_contact_template, numberKeyFromRoot(key))
        }
    }

    override fun permissionGranted(granted: Boolean) {
        super.permissionGranted(granted)
        Timber.i("granted $granted")

        if (granted) {
            requestContactSelection()
        } else {
            // permission denied - clear contact
            // TODO: disable in repository
        }
    }

    private fun requestContactSelection() {

        // We want to read the contact info, so we need permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            contactLauncher?.launch(Unit) ?: run {
                Timber.e("Attempting to launch contact picker but it hasn't been set.")
            }
        } else {
            TODO("Need to request permissions and fail gracefully if not granted")
        }
    }

    override fun onContactSelected(uri: Uri?) {
        Timber.i("contact: $uri")
        preferenceManager.sharedPreferences?.edit {
            putString(numberKeyFromRoot(key), uri.toString())
            apply()
        }
    }
}
