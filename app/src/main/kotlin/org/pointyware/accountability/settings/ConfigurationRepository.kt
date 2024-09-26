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
 * Maintains configuration information for the following feature groups:
 * - Recording configuration
 * - Recording storage location
 * - Calling configuration
 *
 * Setters change single configuration options, while getters return the entire configuration.
 */
interface ConfigurationRepository {

    /**
     * Returns the current recording configuration with all options.
     */
    suspend fun getRecordingConfiguration(): RecordingConfig
    suspend fun setAudioEnabled(enabled: Boolean)
    suspend fun setCameraEnabled(enabled: Boolean)
    suspend fun setSelectedCamera(camera: String)
    suspend fun setCameraResolution(resolution: Size)

    suspend fun getStorageLocation(): StorageLocation
    suspend fun setStorageLocation(location: StorageLocation)

    /**
     * Returns the current calling configuration with all options.
     */
    suspend fun getCallingConfiguration(): CallingConfig
    suspend fun setEmergencyNumberEnabled(enabled: Boolean)
    suspend fun setContactNumberEnabled(enabled: Boolean)
    suspend fun setContactNumber(number: String)

    /**
     * Returns a list of permissions required by the application to support enabled features.
     */
    suspend fun getRequiredPermissions(): List<Permission>
}
