/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording.camera

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.concurrent.Executor

/**
 *
 */
@RequiresApi(Build.VERSION_CODES.P)
class PieSessionStateCallback(
    captureCallback: CameraCaptureSession.CaptureCallback,
    private val executor: Executor
): SessionStateCallback(captureCallback) {
    override fun createRequest(session: CameraCaptureSession, captureRequest: CaptureRequest) {
        session.setSingleRepeatingRequest(captureRequest, executor, captureCallback)
    }
}
