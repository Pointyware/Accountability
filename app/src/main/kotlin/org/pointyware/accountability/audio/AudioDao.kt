/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.audio

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.content.edit
import org.pointyware.accountability.R
import org.pointyware.accountability.recording.AudioConfig
import javax.inject.Inject

/**
 */
class AudioDao @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    resources: Resources,
): AudioOptionsDataSource {

    private val key: String = resources.getString(R.string.pAVAudio)

    override suspend fun getAudioConfig(): AudioConfig? {
        return if (sharedPreferences.getBoolean(key, false)) {
            AudioConfig()
        } else {
            null
        }
    }

    override suspend fun setEnabled(enabled: Boolean) {
        sharedPreferences.edit {
            putBoolean(key, enabled)
        }
    }
}
