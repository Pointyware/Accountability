/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.content.SharedPreferences
import android.content.res.Resources
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.MediaRecorder
import android.util.Size
import org.pointyware.accountability.R
import org.pointyware.accountability.recording.VisualConfig
import timber.log.Timber
import javax.inject.Inject

/**
 * Provides camera options using the Android [android.hardware.camera2.CameraManager].
 */
class CameraOptionsManagerDataSource @Inject constructor(
    private val cameraManager: CameraManager,
    resources: Resources,
    private val sharedPreferences: SharedPreferences
): CameraOptionsDataSource {

    private val videoEnabledKey = resources.getString(R.string.pAVVideo)
    private val cameraKey = resources.getString(R.string.pAVCamera)
    private val resolutionKey = resources.getString(R.string.pAVCameraResolution)

    private val outputClass = MediaRecorder::class.java

    override suspend fun getVisualConfig(): VisualConfig? {
        return if (getEnabled()) {
            val camera = getSelectedCamera() ?: getAvailableCameras().firstOrNull() ?: run {
                Timber.e("No cameras available.")
                return null
            }
            val resolution = getResolution() ?: getAvailableResolutions(camera).firstOrNull() ?: run {
                Timber.e("No resolutions available for camera ($camera).")
                return null
            }
            VisualConfig(camera, resolution)
        } else { null }
    }

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

    private fun getEnabled(): Boolean {
        return sharedPreferences.getBoolean(videoEnabledKey, false)
    }
    override fun setEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(videoEnabledKey, enabled).apply()
    }

    private fun getSelectedCamera(): String? {
        return sharedPreferences.getString(cameraKey, null)
    }
    override fun setSelectedCamera(newCamera: String) {
        sharedPreferences.edit().putString(cameraKey, newCamera).apply()
    }

    private fun Size.serialize(): String {
        return "${width}x$height"
    }

    private fun String.toSize(): Size {
        val parts = split("x")
        return Size(parts[0].toInt(), parts[1].toInt())
    }

    private fun getResolution(): Size? {
        return sharedPreferences.getString(resolutionKey, null)?.toSize()
    }
    override fun setResolution(targetResolution: Size) {
        sharedPreferences.edit().putString(resolutionKey, targetResolution.serialize()).apply()
    }
}
