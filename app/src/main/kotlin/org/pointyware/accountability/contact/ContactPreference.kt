package org.pointyware.accountability.contact

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.AttributeSet
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.core.content.res.use
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

    var contactKey: String? = null
        private set
    var contactLauncher: ActivityResultLauncher<Unit>? = null

    init {
        summaryProvider = SummaryProvider<ContactPreference> {
            if (it.isChecked) {
                getPersistedContact(null)?.let { contactUri ->
                    context.resources.getString(R.string.summary_emergency_contact_on_with_contact, contactUri)
                } ?: context.resources.getString(R.string.summary_emergency_contact_on_no_contact)
            } else {
                context.resources.getString(R.string.summary_emergency_contact_off)
            }
        }
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.settings_permission_ContactPreference, 0, 0
        ).use {
            it.getString(R.styleable.settings_permission_ContactPreference_contactKey)?.also { value ->
                contactKey = value
            } ?: run {
                Timber.e("Permission string not set for PermissionPreference(title:$title)")
            }
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

    override fun permissionGranted(granted: Boolean) {
        super.permissionGranted(granted)
        if (granted) {
            requestContactSelection()
        }
    }

    override fun onContactSelected(uri: Uri?) {
        Timber.i("contact: $uri")
        persistContact(uri)
    }


    override fun hasKey(): Boolean {
        return super.hasKey() && !contactKey.isNullOrEmpty()
    }

    /**
     * @see androidx.preference.ListPreference.onSetInitialValue
     */
    override fun onSetInitialValue(defaultValue: Any?) {
        super.onSetInitialValue(defaultValue)

        persistContact(getPersistedContact(null))
    }

    /**
     * Get the persisted contact URI.
     * @see getPersistedString
     * @see getPersistedBoolean
     */
    protected fun getPersistedContact(defaultReturnValue: Uri?): Uri? {
        if (!shouldPersist()) {
            return defaultReturnValue
        }

        return (preferenceDataStore?.getString(contactKey, null)
            ?: sharedPreferences?.getString(contactKey, null))?.let {
            Uri.parse(it)
        } ?: defaultReturnValue
    }

    /**
     * Persist the contact URI.
     * @see persistString
     * @see persistBoolean
     */
    protected fun persistContact(uri: Uri?): Boolean {
        if (!shouldPersist()) {
            return false
        }

        if (uri == getPersistedContact(null)) {
            return true
        }

        val uriString = uri?.toString()
        return preferenceDataStore?.let {
            it.putString(contactKey, uriString)
            true
        } ?: sharedPreferences?.edit()?.let { editor ->
            editor.putString(contactKey, uriString)
            editor.apply()
            true
        } ?: false
    }
}
