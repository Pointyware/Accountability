package org.pointyware.accountability.settings

import org.pointyware.accountability.calling.CallingConfig

interface CallingOptionsDataSource {

    val callingConfig: CallingConfig?
    fun setEnabled(enabled: Boolean)
    fun setEmergencyEnabled(enabled: Boolean)
    fun setEmergency(number: String)
    fun setContactEnabled(enabled: Boolean)
    fun setContact(number: String)
}
