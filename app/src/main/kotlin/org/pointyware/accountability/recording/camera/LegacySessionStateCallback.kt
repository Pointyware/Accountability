package org.pointyware.accountability.recording.camera

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.os.Handler

/**
 *
 */
class LegacySessionStateCallback(
    captureCallback: CameraCaptureSession.CaptureCallback,
    private val handler: Handler
): SessionStateCallback(captureCallback) {
    override fun createRequest(session: CameraCaptureSession, captureRequest: CaptureRequest) {
        session.setRepeatingRequest(captureRequest, captureCallback, handler)
    }
}
