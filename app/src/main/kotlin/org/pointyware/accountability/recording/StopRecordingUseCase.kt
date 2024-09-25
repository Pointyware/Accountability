package org.pointyware.accountability.recording

import javax.inject.Inject

/**
 *
 */
class StopRecordingUseCase @Inject constructor(
    private val recordingManager: RecordingManager,
    private val recordingRepository: RecordingRepository
) {

    suspend operator fun invoke() {
        recordingManager.stopRecording()
        recordingRepository.finalizeRecording()
    }
}
