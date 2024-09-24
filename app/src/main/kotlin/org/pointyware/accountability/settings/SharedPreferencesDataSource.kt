package org.pointyware.accountability.settings

import org.pointyware.accountability.recording.RecordingConfig

/**
 * Stores and retrieves settings and configuration information in [android.content.SharedPreferences].
 *
 * __note__ - possibly rename as a Data Access Object
 */
interface SharedPreferencesDataSource {
    /**
     * Retrieves the stored configuration if one is present. If not set, a default is retrieved.
     */
    suspend fun getRecordingConfiguration(): RecordingConfig
}
