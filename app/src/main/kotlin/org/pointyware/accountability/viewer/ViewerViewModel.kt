package org.pointyware.accountability.viewer

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.pointyware.accountability.calling.StartCallUseCase
import org.pointyware.accountability.recording.StartRecordingUseCase
import org.pointyware.accountability.recording.StopRecordingUseCase
import org.pointyware.accountability.settings.ConfigurationRepository
import javax.inject.Inject

/**
 */
@HiltViewModel
class ViewerViewModel @Inject constructor(
    private val startRecordingUseCase: StartRecordingUseCase,
    private val stopRecordingUseCase: StopRecordingUseCase,
    private val startCallUseCase: StartCallUseCase,
    private val configurationRepository: ConfigurationRepository
) : ViewModel() {

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

    val configurationState: StateFlow<ViewerUiState> get() = flow {
        configurationRepository.getCallingConfiguration()?.let { callingConfig ->
            emit(
                ViewerUiState(
                    friendlyCallState = callingConfig.contactNumber?.let {
                        CallButtonUiState.Enabled(it)
                    } ?: CallButtonUiState.Disabled,
                    emergencyCallState = callingConfig.emergencyNumber?.let {
                        CallButtonUiState.Enabled(it)
                    } ?: CallButtonUiState.Disabled
                )
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ViewerUiState()
    )

    /**
     * Starts active recording.
     */
    @RequiresPermission(Manifest.permission.CAMERA)
    fun startRecording() {
        viewModelScope.launch {
            startRecordingUseCase()
        }
    }

    /**
     * Stops active recording.
     */
    fun stopRecording() {
        viewModelScope.launch {
            stopRecordingUseCase()
        }
    }

    /**
     *
     */
    fun onViewerOpened() {
        viewModelScope.launch {
            /*
            TODO: call on viewer opened
            1. Get call on start state
            2. If enabled, start call use case
             */


            /* if we should call on start - onStart would trigger a call
            every time without storing state somehow. ew.
             */
//            if (viewModel.callOnStart) {
//                startFriendlyCall()
//            }
        }
    }

    fun startFriendlyCall(number: String) {
        TODO("Not yet implemented")
    }

    fun startEmergencyCall(number: String) {
        TODO("Not yet implemented")
    }
}

sealed interface CallButtonUiState {
    data object Disabled : CallButtonUiState
    data class Enabled(
        val number: String
    ): CallButtonUiState
}

data class ViewerUiState(
    val friendlyCallState: CallButtonUiState = CallButtonUiState.Disabled,
    val emergencyCallState: CallButtonUiState = CallButtonUiState.Disabled,
)
