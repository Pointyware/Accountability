/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.content.SharedPreferences
import android.content.res.Resources
import org.pointyware.accountability.R
import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.contact.ContactPreference
import javax.inject.Inject

/**
 */
class CallingOptionsAndroidDataSource @Inject constructor(
    resources: Resources,
    private val sharedPreferences: SharedPreferences
): CallingOptionsDataSource {

    private val contactKey = resources.getString(R.string.pCallingContact)
    private val numberKey = resources.getString(R.string.pCallingNumber)
    private val callOnStartKey = resources.getString(R.string.pCallingOnStart)

    override val callingConfig: CallingConfig
        get() = CallingConfig(
            emergencyNumber = "911", // TODO: get from resources
            contactNumber = getContactNumber(),
            callOnStart = sharedPreferences.getBoolean(callOnStartKey, false)
        )

    private fun getContactNumber(): String? {
        return if (sharedPreferences.getBoolean(contactKey, false)) {
            sharedPreferences.getString(numberKey, null)
        } else {
            null
        }
    }

    override fun setEmergencyEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(callOnStartKey, enabled).apply()
    }

    override fun setContactEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(contactKey, enabled).apply()
    }

    override fun setContact(number: String) {
        sharedPreferences.edit().putString(numberKey, number).apply()
    }
}
