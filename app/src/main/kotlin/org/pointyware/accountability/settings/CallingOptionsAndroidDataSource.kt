/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.content.SharedPreferences
import android.content.res.Resources
import org.pointyware.accountability.R
import org.pointyware.accountability.calling.CallingConfig
import javax.inject.Inject

/**
 */
class CallingOptionsAndroidDataSource @Inject constructor(
    resources: Resources,
    private val sharedPreferences: SharedPreferences
): CallingOptionsDataSource {

    private val contactKey = resources.getString(R.string.pCallingContact)
    private val numberKey = ContactPreference.numberKeyFromRoot(contactKey)
    private val callOnStartKey = resources.getString(R.string.pCallingOnStart)

    override val callingConfig: CallingConfig
        get() = CallingConfig(
            emergencyNumber = "911",
            contactNumber = sharedPreferences.getString(contactKey, null),
            callOnStart = sharedPreferences.getBoolean(callOnStartKey, false)
        )

    override fun setEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setEmergencyEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setEmergency(number: String) {
        TODO("Not yet implemented")
    }

    override fun setContactEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setContact(number: String) {
        TODO("Not yet implemented")
    }
}
