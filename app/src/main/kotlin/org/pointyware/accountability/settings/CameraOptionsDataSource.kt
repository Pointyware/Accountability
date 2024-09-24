package org.pointyware.accountability.settings

import android.util.Size
import org.pointyware.accountability.recording.VisualConfig
import org.pointyware.clean.Adapter

/**
 * Provides access to a variety of camera configuration options.
 */
@Adapter
interface CameraOptionsDataSource {

    val visualConfig: VisualConfig?

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
