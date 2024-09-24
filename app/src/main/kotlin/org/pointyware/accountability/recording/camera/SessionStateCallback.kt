package org.pointyware.accountability.recording.camera

import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CaptureRequest

/**
 * TODO: rename
 */
abstract class SessionStateCallback(
    protected val captureCallback: CameraCaptureSession.CaptureCallback
) {

    abstract fun createRequest(session: CameraCaptureSession, captureRequest: CaptureRequest)

    val stateCallback = object: CameraCaptureSession.StateCallback() {
        override fun onConfigured(session: CameraCaptureSession) {
            // TODO: gather info for capture request
            val captureRequestBuilder = session.device.createCaptureRequest(CameraDevice.TEMPLATE_RECORD)
            val captureRequest = captureRequestBuilder.build()
            createRequest(session, captureRequest)
        }
        override fun onConfigureFailed(session: CameraCaptureSession) {
            TODO("Not yet implemented")
        }
    }
}
