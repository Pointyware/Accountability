/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.audio

import org.pointyware.accountability.recording.AudioConfig

/**
 *
 */
interface AudioOptionsDataSource {

    suspend fun getAudioConfig(): AudioConfig?

    suspend fun setEnabled(enabled: Boolean)
}
