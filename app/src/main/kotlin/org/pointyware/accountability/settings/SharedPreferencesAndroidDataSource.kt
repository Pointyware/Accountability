package org.pointyware.accountability.settings

import org.pointyware.accountability.recording.RecordingConfig
import javax.inject.Inject

/**
 */
class SharedPreferencesAndroidDataSource @Inject constructor(): SharedPreferencesDataSource {

    override suspend fun getRecordingConfiguration(): RecordingConfig {
        return RecordingConfig( // https://github.com/Pointyware/Accountability/issues/15
            null,
            null
        )
    }
}
