/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.util.Size
import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.permission.Permission
import org.pointyware.accountability.recording.RecordingConfig
import org.pointyware.accountability.storage.StorageLocation

/**
 * Maintains configuration information for the following features:
 * - Recording configuration
 * - Recording storage location
 * - Calling configuration
 */
interface ConfigurationRepository {

    suspend fun getRecordingConfiguration(): RecordingConfig
    suspend fun setAudioEnabled(enabled: Boolean)
    suspend fun setCameraEnabled(enabled: Boolean)
    suspend fun setSelectedCamera(camera: String)
    suspend fun setCameraResolution(resolution: Size)

    suspend fun getStorageLocation(): StorageLocation
    suspend fun setStorageLocation(location: StorageLocation)

    suspend fun getCallingConfiguration(): CallingConfig
    suspend fun setCallingEnabled(enabled: Boolean)
    suspend fun setEmergencyNumberEnabled(enabled: Boolean)
    suspend fun setEmergencyNumber(number: String)
    suspend fun setContactNumberEnabled(enabled: Boolean)
    suspend fun setContactNumber(number: String)

    suspend fun getRequiredPermissions(): List<Permission>
}
