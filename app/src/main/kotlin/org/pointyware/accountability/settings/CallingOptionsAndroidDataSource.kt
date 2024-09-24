package org.pointyware.accountability.settings

import org.pointyware.accountability.calling.CallingConfig
import javax.inject.Inject

/**
 */
class CallingOptionsAndroidDataSource @Inject constructor(

): CallingOptionsDataSource {
    override val callingConfig: CallingConfig
        get() = TODO("Not yet implemented")

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
