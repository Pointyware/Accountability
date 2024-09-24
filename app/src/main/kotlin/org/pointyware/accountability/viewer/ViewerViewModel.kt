package org.pointyware.accountability.viewer

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.pointyware.accountability.recording.StartRecordingUseCase
import org.pointyware.accountability.recording.StopRecordingUseCase
import javax.inject.Inject

/**
 */
@HiltViewModel
class ViewerViewModel @Inject constructor(
    private val startRecordingUseCase: StartRecordingUseCase,
    private val stopRecordingUseCase: StopRecordingUseCase,
): ViewModel() {

    val callingEnabled: Boolean
    get() = true // TODO: replace with config repo or use cases

    val friendEnabled: Boolean
    get() = true // TODO: replace with config repo or use cases

    val contactNumber: String?
    get() = ""

    val policeEnabled: Boolean
    get() = true // TODO: replace with config repo or use cases

    val callOnStart: Boolean
    get() = false // TODO: replace with config repo or use cases

    val policeNumber: String
    get() = ""

    @RequiresPermission(Manifest.permission.CAMERA)
    fun startRecording() {
        viewModelScope.launch {
            startRecordingUseCase()
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            stopRecordingUseCase()
        }
    }
}
