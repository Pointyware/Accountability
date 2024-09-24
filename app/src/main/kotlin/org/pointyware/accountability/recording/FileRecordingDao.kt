package org.pointyware.accountability.recording

import org.pointyware.accountability.storage.FileProvider
import javax.inject.Inject

/**
 * Accesses local file system to maintain recording files.
 */
class FileRecordingDao @Inject constructor(
    private val fileSelector: FileProvider
): LocalRecordingDao {


    override fun add(recording: Recording) {
        loadedRecordings.add(recording)
    }

    // lazy-load recordings
    private val loadedRecordings = mutableListOf<Recording>()
    override val recordingList: List<Recording> by lazy {
        loadedRecordings.also { recordings ->
            recordings.addAll(
                fileSelector.directory()?.listFiles()?.map { file ->
                    Recording(
                        file,
                        // TODO: get media information from each file to determine file type
                        RecordingType.Video
                    )
                } ?: throw IllegalStateException("Save location not accessible.")
            )
        }
    }

    override fun remove(recording: Recording) {
        recording.file.delete()
        loadedRecordings.remove(recording)
    }
}
