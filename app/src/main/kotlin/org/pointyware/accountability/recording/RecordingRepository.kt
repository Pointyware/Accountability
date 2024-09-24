package org.pointyware.accountability.recording

import kotlinx.coroutines.flow.StateFlow
import java.io.File

/**
 * Maintains record of previous recordings and new/ongoing recordings.
 */
interface RecordingRepository {
    /**
     * Creates a record of a recording and returns the destination [File] where recording data
     * should be stored.
     */
    suspend fun createRecording(config: RecordingConfig): File

    /**
     * Notifies the repository that a recording has completed, the file can be closed, and the new
     * recording can be added to the list of recordings.
     */
    suspend fun finalizeRecording()

    /**
     * Get the list of all recordings, in nor particular order.
     */
    suspend fun getRecordingList(): List<Recording>

    /**
     * A [StateFlow] of the list of all recordings, in no particular order.
     */
    val recordingListFlow: StateFlow<List<Recording>>

    /**
     * Removes a recording, deleting the local file if necessary.
     */
    suspend fun removeRecording(recording: Recording)
}
