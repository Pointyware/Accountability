package org.pointyware.accountability.recording

/**
 */
interface LocalRecordingDao {
    fun add(recording: Recording)
    val recordingList: List<Recording>
    fun remove(recording: Recording)
}
