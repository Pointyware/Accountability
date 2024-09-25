/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

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
