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
    resources: Resources, // TODO: consider replacing with String to configure the key used
): AudioOptionsDataSource {

    private val key: String = resources.getString(R.string.pAVAudio)

    override val audioConfig: AudioConfig?
        get() = if (sharedPreferences.getBoolean(key, false)) {
            AudioConfig(0)
        } else {
            null
        }

    override fun setEnabled(enabled: Boolean) {
        sharedPreferences.edit {
            putBoolean(key, enabled)
        }
    }
}
