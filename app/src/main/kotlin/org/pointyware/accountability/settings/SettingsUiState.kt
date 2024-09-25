/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.recording.RecordingConfig
import org.pointyware.accountability.storage.StorageLocation
import org.pointyware.accountability.viewer.CallButtonUiState
import org.pointyware.accountability.viewer.CallButtonUiState.Companion.toCallButtonUiState

/**
 *
 */
data class SettingsUiState(
    val audioVideoSettings: AudioVideoSettingsUiState,
    val callingSettings: CallingSettingsUiState,
    val storageSettings: StorageSettingsUiState
)

data class AudioVideoSettingsUiState(
    val recordingConfig: RecordingConfig
) {
    val videoEnabled: Boolean = recordingConfig.video != null
    val audioEnabled: Boolean = recordingConfig.audio != null
    val cameraOrNull: String? = recordingConfig.video?.cameraId
    val resolutionOrNull: Pair<Int, Int>? = recordingConfig.video?.let {
        it.width to it.height
    }
}

data class CallingSettingsUiState(
    val callingConfig: CallingConfig
) {
    val contactNumber: CallButtonUiState = callingConfig.contactNumber.toCallButtonUiState()
    val emergencyNumber: CallButtonUiState = callingConfig.emergencyNumber.toCallButtonUiState()
    val callOnStart: Boolean get() = callingConfig.callOnStart
}

data class StorageSettingsUiState(
    val storageLocation: StorageLocation
) {
    val storageLocationString: String = storageLocation.toString()
}
