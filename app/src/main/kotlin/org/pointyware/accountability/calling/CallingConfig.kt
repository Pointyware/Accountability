/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.calling

/**
 * Configuration for calling from the Viewer.
 *
 * @property emergencyNumber The number to call in case of emergency.
 * @property contactNumber The number to call of a friend.
 * @property callOnStart Whether to call the contact number on start.
 */
data class CallingConfig (
    val emergencyNumber: String?,
    val contactNumber: String?,
    val callOnStart: Boolean
)
