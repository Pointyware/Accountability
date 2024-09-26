package org.pointyware.accountability.settings

import android.util.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.permission.PermissionVerifier
import org.pointyware.accountability.recording.RecordingConfig
import org.pointyware.accountability.storage.StorageLocation
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configurationRepository: ConfigurationRepository,
    private val permissionChecker: PermissionVerifier
): ViewModel() {

    val state: StateFlow<SettingsUiState?> = flow {

        val recordingConfig = viewModelScope.async { configurationRepository.getRecordingConfiguration() }
        val storageLocation = viewModelScope.async { configurationRepository.getStorageLocation() }
        val callingConfig = viewModelScope.async { configurationRepository.getCallingConfiguration() }

        emit(SettingsUiState(
            audioVideoSettings = AudioVideoSettingsUiState(
                recordingConfig.await()
            ),
            callingSettings = CallingSettingsUiState(
                callingConfig.await()
            ),
            storageSettings = StorageSettingsUiState(
                storageLocation.await()
            )
        ))
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        null
    )

    private val _recordingConfig = MutableStateFlow<RecordingConfig?>(null)
    val recordingConfig: StateFlow<RecordingConfig?> = _recordingConfig

    fun setAudioEnabled(enabled: Boolean) {
        viewModelScope.launch { configurationRepository.setAudioEnabled(enabled) }
    }

    fun setVideoEnabled(enabled: Boolean) {
        viewModelScope.launch { configurationRepository.setCameraEnabled(enabled) }
    }

    fun setCamera(cameraId: String) {
        viewModelScope.launch { configurationRepository.setSelectedCamera(cameraId) }
    }

    fun setResolution(size: Size) {
        viewModelScope.launch { configurationRepository.setCameraResolution(size) }
    }

    private val _storageLocation = MutableStateFlow<StorageLocation?>(null)
    val storageLocation: StateFlow<StorageLocation?> = _storageLocation

    fun setStorageLocation(location: StorageLocation) {
        viewModelScope.launch { configurationRepository.setStorageLocation(location) }
    }

    private val _callingConfig = MutableStateFlow<CallingConfig?>(null)
    val callingConfig: StateFlow<CallingConfig?> = _callingConfig

    fun setCallingEnabled(enabled: Boolean) {
        viewModelScope.launch { configurationRepository.setCallingEnabled(enabled) }
    }

    fun setEmergencyNumber(number: String?) {
        viewModelScope.launch {
            number?.also { newNumber ->
                configurationRepository.setEmergencyNumber(newNumber)
                configurationRepository.setEmergencyNumberEnabled(true)
            } ?: run {
                configurationRepository.setEmergencyNumberEnabled(false)
            }
        }
    }

    fun setContactNumber(number: String?) {
        viewModelScope.launch {
            number?.also { newNumber ->
                configurationRepository.setContactNumber(newNumber)
                configurationRepository.setContactNumberEnabled(true)
            } ?: run {
                configurationRepository.setContactNumberEnabled(false)
            }
        }
    }
}
