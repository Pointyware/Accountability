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
