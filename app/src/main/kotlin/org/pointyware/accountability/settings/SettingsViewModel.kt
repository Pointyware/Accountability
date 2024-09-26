package org.pointyware.accountability.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.pointyware.accountability.permission.PermissionVerifier
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
}
