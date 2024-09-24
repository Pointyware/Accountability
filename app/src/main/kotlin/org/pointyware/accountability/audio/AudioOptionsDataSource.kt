/*
 * Copyright (c) 2022 Taush Sampley
 * All rights reserved.
 *
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
