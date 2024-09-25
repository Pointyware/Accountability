/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.viewer

import android.media.ImageReader
import android.view.Surface

/**
 * Provides an [android.media.ImageReader] where video content can be read and an associated
 * [android.view.Surface] as the destination for video output.
 */
interface PreviewSurfaceProvider {
    val surface: Surface
    val imageReader: ImageReader
}
