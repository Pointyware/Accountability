package org.pointyware.accountability.settings

import org.pointyware.accountability.recording.RecordingConfig
import javax.inject.Inject

/**
 */
class SharedPreferencesAndroidDataSource @Inject constructor(): SharedPreferencesDataSource {

    override suspend fun getRecordingConfiguration(): RecordingConfig {
        TODO("Not yet implemented")
    }
}
