/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording.camera

import android.Manifest
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.view.Surface
import androidx.annotation.RequiresPermission

/**
 *
 */
@Suppress("DEPRECATION")
class LegacyCameraDeviceOpener(
    cameraManager: CameraManager,
    previewSurface: Surface,
    recordingSurface: Surface,
    sessionStateCallback: SessionStateCallback,
    private val handler: Handler?
): CameraDeviceOpener(cameraManager, previewSurface, recordingSurface, sessionStateCallback) {

    override fun createSession(camera: CameraDevice, surfaces: List<Surface>) {
        camera.createCaptureSession(surfaces, sessionStateCallback.stateCallback, handler)
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    override fun open(cameraId: String) {
        cameraManager.openCamera(cameraId, stateCallback, handler)
    }
}
