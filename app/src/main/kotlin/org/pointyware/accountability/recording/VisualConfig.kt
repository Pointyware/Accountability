/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

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
