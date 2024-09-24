package org.pointyware.accountability.settings

import android.util.Size
import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.permission.Permission
import org.pointyware.accountability.recording.RecordingConfig
import org.pointyware.accountability.storage.StorageLocation

/**
 * I'm now thinking this should really be broken up.
 *
 * Maintains configuration information for the following features:
 * * Recording configuration
 * * Recording storage location
 * * Calling configuration
 * * Backup configuration *
 * * Location configuration *
 *
 * &#42; - Planned
 *
 * __note__ - some might see this as a SRP violation, but each configuration is handled by its
 * own implementation. The *responsibility* of this interface is to provide all configuration
 * information.
 */
interface ConfigurationRepository {

    suspend fun getRecordingConfiguration(): RecordingConfig
    suspend fun setAudioEnabled(enabled: Boolean)
    suspend fun setCameraEnabled(enabled: Boolean)
    suspend fun setSelectedCamera(camera: String)
    suspend fun setCameraResolution(resolution: Size)

    suspend fun getStorageLocation(): StorageLocation
    suspend fun setStorageLocation(location: StorageLocation)

    suspend fun getCallingConfiguration(): CallingConfig?
    suspend fun setCallingEnabled(enabled: Boolean)
    suspend fun setEmergencyNumberEnabled(enabled: Boolean)
    suspend fun setEmergencyNumber(number: String)
    suspend fun setContactNumberEnabled(enabled: Boolean)
    suspend fun setContactNumber(number: String)

    suspend fun getRequiredPermissions(): List<Permission>
}
