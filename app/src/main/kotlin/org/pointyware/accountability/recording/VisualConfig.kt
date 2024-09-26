/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording

import android.util.Size


/**
 * Configuration for visual portion of a recording.
 */
data class VisualConfig(
    val cameraId: String,
    val size: Size
) {
    constructor(cameraId: String, width: Int, height: Int) : this(cameraId, Size(width, height))

    val width: Int get() = size.width
    val height: Int get() = size.height
}
