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
    private val callingOptionsDataSource: CallingOptionsDataSource
): ConfigurationRepository {


    override suspend fun getRecordingConfiguration(): RecordingConfig {
        return RecordingConfig(
            audioOptionsDataSource.audioConfig,
            cameraOptionsDataSource.visualConfig
        ).also {
            Timber.i("Configuration: $it")
        }
    }

    override suspend fun setAudioEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setCameraEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setSelectedCamera(camera: String) {
        TODO("Not yet implemented")
    }

    override suspend fun setCameraResolution(resolution: Size) {
        TODO("Not yet implemented")
    }

    override suspend fun getStorageLocation(): StorageLocation {
        return locationDataSource.getLocation()
    }

    override suspend fun setStorageLocation(location: StorageLocation) {
        locationDataSource.setLocation(location)
    }

    override suspend fun getCallingConfiguration(): CallingConfig? {
        return callingOptionsDataSource.callingConfig
    }

    override suspend fun setCallingEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setEmergencyNumberEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setEmergencyNumber(number: String) {
        TODO("Not yet implemented")
    }

    override suspend fun setContactNumberEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setContactNumber(number: String) {
        TODO("Not yet implemented")
    }

    // TODO: write test
    override suspend fun getRequiredPermissions(): List<Permission> {
        TODO("Not yet implemented")
    }
}
