/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording.camera

import android.Manifest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.accountability.recording.RecordingConfig
import org.pointyware.accountability.recording.RecordingManager
import org.pointyware.accountability.recording.RecordingState
import org.pointyware.accountability.viewer.PreviewSurfaceProvider
import java.io.File
import javax.inject.Inject

/**
 * Requires CAMERA permission.
 */
class CameraRecordingManager @Inject constructor(
    private val cameraMediaRecorder: CameraMediaRecorder,
    private val previewSurfaceProvider: PreviewSurfaceProvider,
    private val cameraDeviceOpenerFactory: CameraDeviceOpenerFactory,
): RecordingManager {

    private val _state = MutableStateFlow(RecordingState.Stopped)
    override val state: StateFlow<RecordingState> = _state

    private var cameraDeviceOpener: CameraDeviceOpener? = null

    @RequiresPermission(Manifest.permission.CAMERA)
    override suspend fun startRecording(destination: File, config: RecordingConfig) {
        // configure recorder
        val videoConfig = config.video ?: throw IllegalArgumentException("This RecordingManager requires a video configuration.")
        val recordingSurface = cameraMediaRecorder.configure(destination, config)

        // start capture
        cameraDeviceOpenerFactory.createOpener(previewSurfaceProvider.surface, recordingSurface).let {
            cameraDeviceOpener = it
            it.open(videoConfig.cameraId)
            // TODO: start recording
            _state.value = RecordingState.Recording
        }
    }

    override suspend fun stopRecording() {

        cameraDeviceOpener?.close() ?: throw IllegalStateException("Camera device is not started.")
    }
}
