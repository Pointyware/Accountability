package org.pointyware.accountability.recording

import javax.inject.Inject

/**
 * Responsible for removing completed recordings.
 */
class RemoveRecordingUseCase @Inject constructor(
    private val recordingRepository: RecordingRepository
) {
    suspend operator fun invoke(recording: Recording) {
        recordingRepository.removeRecording(recording)
    }

    suspend fun removeList(recordingList: List<Recording>) {
        recordingList.forEach { recordingRepository.removeRecording(it) }
    }
}
