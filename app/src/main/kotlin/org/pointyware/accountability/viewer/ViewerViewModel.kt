/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

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
import timber.log.Timber
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
            configurationRepository.getCallingConfiguration()?.let { config ->
                config.contactNumber.takeIf { config.callOnStart }?.let { number ->
                    startFriendlyCall(number)
                }
            }
        }
    }

    fun startFriendlyCall(number: String) {
        Timber.v("Calling Friend")
        viewModelScope.launch {
            if (number.isNotBlank()) {
                startCallUseCase.invoke(true, number)
            } else {
                startCallUseCase.invoke(false)
            }
        }
    }

    fun startEmergencyCall(number: String) {
        Timber.v("Dialing Emergency Services")
        viewModelScope.launch {
            startCallUseCase.invoke(false, number)
        }
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
