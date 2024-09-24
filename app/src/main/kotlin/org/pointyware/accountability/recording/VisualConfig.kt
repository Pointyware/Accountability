package org.pointyware.accountability.recording

/**
 * Configuration for visual portion of a recording.
 */
data class VisualConfig(
    val cameraId: String,
    val width: Int,
    val height: Int,
    // TODO: more properties
)
