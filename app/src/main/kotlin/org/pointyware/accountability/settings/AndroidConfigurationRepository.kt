/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.util.Size
import org.pointyware.accountability.audio.AudioOptionsDataSource
import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.permission.Permission
import org.pointyware.accountability.recording.RecordingConfig
import org.pointyware.accountability.storage.StorageLocation
import timber.log.Timber
import javax.inject.Inject

/**
 * Maintains configuration information in the context of the Android system.
 *
 */
class AndroidConfigurationRepository @Inject constructor(
    private val cameraOptionsDataSource: CameraOptionsDataSource,
    private val audioOptionsDataSource: AudioOptionsDataSource,
    private val locationDataSource: LocationDataSource,
    private val callingOptionsDataSource: CallingOptionsDataSource,
): ConfigurationRepository {

    override suspend fun getRecordingConfiguration(): RecordingConfig {
        return RecordingConfig(
            audioOptionsDataSource.getAudioConfig(),
            cameraOptionsDataSource.getVisualConfig()
        ).also {
            Timber.i("Configuration: $it")
        }
    }

    override suspend fun setAudioEnabled(enabled: Boolean) {
        audioOptionsDataSource.setEnabled(enabled)
    }

    override suspend fun setCameraEnabled(enabled: Boolean) {
        cameraOptionsDataSource.setEnabled(enabled)
    }

    override suspend fun setSelectedCamera(camera: String) {
        cameraOptionsDataSource.setSelectedCamera(camera)
    }

    override suspend fun setCameraResolution(resolution: Size) {
        cameraOptionsDataSource.setResolution(resolution)
    }

    override suspend fun getStorageLocation(): StorageLocation {
        return locationDataSource.getLocation()
    }

    override suspend fun setStorageLocation(location: StorageLocation) {
        locationDataSource.setLocation(location)
    }

    override suspend fun getCallingConfiguration(): CallingConfig {
        return callingOptionsDataSource.callingConfig
    }

    override suspend fun setEmergencyNumberEnabled(enabled: Boolean) {
        callingOptionsDataSource.setEmergencyEnabled(enabled)
    }

    override suspend fun setContactNumberEnabled(enabled: Boolean) {
        callingOptionsDataSource.setContactEnabled(enabled)
    }

    override suspend fun setContactNumber(number: String) {
        callingOptionsDataSource.setContact(number)
    }

    override suspend fun getRequiredPermissions(): List<Permission> {
        val callingConfig = getCallingConfiguration()
        val recordingConfig = getRecordingConfiguration()

        return listOf(
            Permission.Phone to (callingConfig.contactNumber != null || callingConfig.emergencyNumber != null),
            Permission.Camera to (recordingConfig.video != null),
            Permission.Audio to (recordingConfig.audio != null),
        ).filter { it.second }.map { it.first }
    }
}
