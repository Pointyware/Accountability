/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.MediaRecorder
import android.util.Size
import org.pointyware.accountability.recording.VisualConfig
import javax.inject.Inject

/**
 * Provides camera options using the Android [android.hardware.camera2.CameraManager].
 */
class CameraOptionsManagerDataSource @Inject constructor(
    private val cameraManager: CameraManager
): CameraOptionsDataSource {

    override val visualConfig: VisualConfig? get() = TODO("Not yet implemented")

    private val outputClass = MediaRecorder::class.java

    override suspend fun getAvailableCameras(): List<String> {
        return cameraManager.cameraIdList.toList()
    }

    /**
     * @see CameraOptionsDataSource.getAvailableResolutions
     * @throws IllegalArgumentException
     * @throws android.hardware.camera2.CameraAccessException
     */
    override suspend fun getAvailableResolutions(camera: String): List<Size> {
        val characteristics = cameraManager.getCameraCharacteristics(camera)
        val map = characteristics[CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP]
            ?: throw IllegalStateException("Could not retrieve the stream configuration map for camera ($camera).")
        val sizes = map.getOutputSizes(outputClass)
            ?: throw IllegalArgumentException("Could not get output sizes for given output class ($outputClass).")
        return sizes.toList()
    }

    override fun setEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setSelectedCamera(newCamera: String) {
        TODO("Not yet implemented")
    }

    override fun setResolution(targetResolution: Size) {
        TODO("Not yet implemented")
    }
}
