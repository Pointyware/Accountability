/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording

import android.annotation.SuppressLint
import timber.log.Timber
import javax.inject.Inject

/**
 */
class StartRecordingUseCase @Inject constructor(
//    private val configurationRepository: ConfigurationRepository,
//    private val permissionVerifier: PermissionVerifier,
//    private val permissionRequestLauncher: PermissionRequestLauncher,
//    private val recordingRepository: RecordingRepository,
//    private val notificationOutput: NotificationOutput,
    private val recordingManager: RecordingManager
) {

    @SuppressLint("MissingPermission")
    suspend operator fun invoke() {
        Timber.i("Starting recording")
//        when (recordingManager.state.value) {
//            RecordingState.Stopped -> {
//                val permissions = configurationRepository.getRequiredPermissions()
//                if (permissions.all { permissionVerifier.hasPermission(it) }) {
//                    val config = configurationRepository.getRecordingConfiguration()
//                    val recordingFile = recordingRepository.createRecording(config)
//                    recordingManager.startRecording(recordingFile, config)
//                    notificationOutput.showRecordingNotification()
//                } else {
//                    permissionRequestLauncher.request(permissions)
//                }
//            }
//            RecordingState.Recording -> {
//                Timber.i("Already recording")
//            }
//            RecordingState.Error -> {
//                Timber.w("Can not start recording while in error state")
//            }
//        }
    }
}
