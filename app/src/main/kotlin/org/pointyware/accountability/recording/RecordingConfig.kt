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
