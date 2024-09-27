package org.pointyware.accountability.recording

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Responsible for retrieving a list of all completed recordings.
 */
class GetRecordingListUseCase @Inject constructor(
    private val recordingRepository: RecordingRepository
) {
    suspend operator fun invoke(): List<Recording> {
        return recordingRepository.getRecordingList()
    }

    fun watch(): StateFlow<List<Recording>> = recordingRepository.recordingListFlow
}
