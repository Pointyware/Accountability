/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording.camera

import android.Manifest
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.OutputConfiguration
import android.hardware.camera2.params.SessionConfiguration
import android.os.Build
import android.view.Surface
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import java.util.concurrent.Executor

/**
 *
 */
@RequiresApi(Build.VERSION_CODES.P)
class PieCameraDeviceOpener(
    cameraManager: CameraManager,
    previewSurface: Surface,
    recordingSurface: Surface,
    sessionStateCallback: SessionStateCallback,
    private val executor: Executor,
): CameraDeviceOpener(cameraManager, previewSurface, recordingSurface, sessionStateCallback) {

    override fun createSession(camera: CameraDevice, surfaces: List<Surface>) {

        val outputConfigurations = surfaces.map {
            /*
             TODO: read OutputConfiguration docs to gather more details about
              possible dynamic resolution/switching
             */
            OutputConfiguration(it)
        }
        val sessionConfiguration = SessionConfiguration(
            SessionConfiguration.SESSION_REGULAR,
            outputConfigurations,
            executor,
            sessionStateCallback.stateCallback
        )
        camera.createCaptureSession(sessionConfiguration)
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    override fun open(cameraId: String) {
        cameraManager.openCamera(cameraId, executor, stateCallback)
    }
}
