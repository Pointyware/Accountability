package org.pointyware.accountability.recording

//import com.taushsampley.clean.Interactor
import javax.inject.Inject

/**
 *
 */
//@Interactor
class StopRecordingUseCase @Inject constructor(
    private val recordingManager: RecordingManager,
    private val recordingRepository: RecordingRepository
) {

    suspend operator fun invoke() {
        recordingManager.stopRecording()
        recordingRepository.finalizeRecording()
    }
}
