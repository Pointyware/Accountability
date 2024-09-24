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
