/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.util.Size
import org.pointyware.accountability.recording.VisualConfig

/**
 * Provides access to a variety of camera configuration options.
 */
interface CameraOptionsDataSource {

    suspend fun getVisualConfig(): VisualConfig?

    /**
     * Returns a list of cameras by their string id.
     */
    suspend fun getAvailableCameras(): List<String>

    /**
     * Returns a list of resolutions available for a given camera.
     */
    suspend fun getAvailableResolutions(camera: String): List<Size>

    fun setEnabled(enabled: Boolean)
    fun setSelectedCamera(newCamera: String)
    fun setResolution(targetResolution: Size)
}
