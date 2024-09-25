package org.pointyware.accountability.calling

/**
 * Configuration for calling from the Viewer.
 *
 * @property emergencyNumber The number to call in case of emergency.
 * @property contactNumber The number to call of a friend.
 */
data class CallingConfig (
    val emergencyNumber: String?,
    val contactNumber: String?
)
