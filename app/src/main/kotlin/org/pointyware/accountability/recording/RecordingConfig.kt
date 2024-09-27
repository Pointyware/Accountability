/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording

/**
 * Configuration for a recording.
 */
data class RecordingConfig(
    val audio: AudioConfig?,
    val video: VisualConfig?
) {
    val type: RecordingType? = RecordingType.getType(audio != null, video != null)
}
