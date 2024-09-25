/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.audio

import org.pointyware.accountability.recording.AudioConfig

/**
 *
 */
interface AudioOptionsDataSource {

    val audioConfig: AudioConfig?

    fun setEnabled(enabled: Boolean)
    // TODO: provide information about available bitrates and other audio config info
}
