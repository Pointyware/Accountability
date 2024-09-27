/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import org.pointyware.accountability.calling.CallingConfig

interface CallingOptionsDataSource {

    val callingConfig: CallingConfig
    fun setEmergencyEnabled(enabled: Boolean)
    fun setContactEnabled(enabled: Boolean)
    fun setContact(number: String)
}
