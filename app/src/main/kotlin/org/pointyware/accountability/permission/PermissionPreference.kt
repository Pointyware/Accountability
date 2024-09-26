/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission

import android.content.Context
import android.content.pm.PackageManager
import android.util.AttributeSet
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.core.content.res.use
import androidx.preference.SwitchPreference
import org.pointyware.accountability.R
import timber.log.Timber

/**
 * A preference that represents a single permission.
 *
 * This preference will request the permission when the user attempts to enable it.
 */
open class PermissionPreference(
    context: Context, attrs: AttributeSet
): SwitchPreference(context, attrs) {

    var permission: String? = null
        private set
    var launcher: ActivityResultLauncher<Array<String>>? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.settings_permission_PermissionPreference, 0, 0
        ).use {
            it.getString(R.styleable.settings_permission_PermissionPreference_permission)?.also { value ->
                permission = value
            } ?: run {
                Timber.e("Permission string not set for PermissionPreference(title:$title)")
            }
        }
    }

    override fun onAttached() {
        super.onAttached()

        setOnPreferenceChangeListener { _, newValue ->
            val enableFeature = newValue as Boolean
            if (enableFeature) {
                permission?.let { permissionString ->
                    val granted = ActivityCompat.checkSelfPermission(context, permissionString) == PackageManager.PERMISSION_GRANTED
                    if (granted) {
                        Timber.v("Permission $permission allowed.")
                        permissionGranted(true)
                        true
                    } else {
                        launcher?.launch(arrayOf(permissionString)) ?: run {
                            Timber.e("No launcher provided for preference")
                        }
                        Timber.v("Permission requested $permission.")
                        false
                    }
                } ?: run {
                    Timber.e("Attempting to enable permission will null String")
                    false
                }
            } else {
                true
            }
        }
    }

    open fun permissionGranted(granted: Boolean) {
        Timber.v("Permission granted: $granted")
        sharedPreferences?.edit { putBoolean(key, granted) }
    }
}
