/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

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
