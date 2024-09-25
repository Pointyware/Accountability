package org.pointyware.accountability.recording.camera

import android.Manifest
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.view.Surface
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.accountability.recording.RecordingState

/**
 *
 */
abstract class CameraDeviceOpener(
    protected val cameraManager: CameraManager,
    private val previewSurface: Surface,
    private val recordingSurface: Surface,
    protected val sessionStateCallback: SessionStateCallback
) {

    var camera: CameraDevice? = null

    private val _output = MutableStateFlow(RecordingState.Stopped)
    val output: StateFlow<RecordingState> = _output

    abstract fun createSession(camera: CameraDevice, surfaces: List<Surface>)

    @RequiresPermission(Manifest.permission.CAMERA)
    abstract fun open(cameraId: String)

    fun close() {
        camera?.close()
    }

    protected val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            this@CameraDeviceOpener.camera = camera

            val surfaces = listOf(
                previewSurface,
                recordingSurface
            )
            createSession(camera, surfaces)
        }

        override fun onClosed(camera: CameraDevice) {
            super.onClosed(camera)
            this@CameraDeviceOpener.camera = null
            _output.value = RecordingState.Stopped
        }

        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
            this@CameraDeviceOpener.camera = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            _output.value = RecordingState.Error
            camera.close()
            this@CameraDeviceOpener.camera = null
        }
    }
}
