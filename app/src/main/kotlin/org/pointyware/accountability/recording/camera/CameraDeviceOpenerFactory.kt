/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording.camera

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 *
 */
class CameraDeviceOpenerFactory @Inject constructor(
    private val cameraManager: CameraManager
) {
    fun createOpener(previewSurface: Surface, recordingSurface: Surface): CameraDeviceOpener {

        val captureCallback = object: CameraCaptureSession.CaptureCallback() {
            // TODO: implement?
        }

        return if (Build.VERSION.SDK_INT >= 28) {
            val executor = Executors.newSingleThreadExecutor()
            PieCameraDeviceOpener(
                cameraManager,
                previewSurface, recordingSurface,
                PieSessionStateCallback(captureCallback, executor),
                executor
            )
        } else {
            val simpleName = CameraDeviceOpenerFactory::class.simpleName
            val looperThread = HandlerThread("${simpleName}-HandlerThread")
            val handler = looperThread.let {
                it.start()
                Handler(it.looper)
            }
            LegacyCameraDeviceOpener(
                cameraManager,
                previewSurface, recordingSurface,
                LegacySessionStateCallback(captureCallback, handler),
                handler
            )
        }
    }
}
