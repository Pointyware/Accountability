/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.pointyware.accountability.storage.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 *
 */
class AndroidRecordingRepository @Inject constructor(
    private val fileProvider: FileProvider,
    private val localRecordingDao: LocalRecordingDao
): RecordingRepository {

    // TODO: migrate out to be controlled ConfigurableFileProvider
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ROOT)

    private var lastFile: File? = null
    private var lastConfig: RecordingConfig? = null

    override suspend fun createRecording(config: RecordingConfig): File {
        return fileProvider.getFile(dateFormat.format(Date()), ".mp4").also {
            lastFile = it
            lastConfig = config
        } // TODO: possibly maintain some kind of state to prevent calling this multiple times
    }

    override suspend fun finalizeRecording() {
        val newRecording = Recording(
            lastFile ?: throw IllegalStateException("No recording file has been created. " +
                    "See org.pointyware.accountability.recording.AndroidRecordingRepository#createRecording"),
            lastConfig?.type ?: throw IllegalStateException("Unknown configuration."),
        )
        _recordingListFlow.value = _recordingListFlow.value.plus(newRecording)
        localRecordingDao.add(newRecording)
    }

    override suspend fun getRecordingList(): List<Recording> {
        return localRecordingDao.recordingList
    }

    private val _recordingListFlow = MutableStateFlow(localRecordingDao.recordingList)
    override val recordingListFlow: StateFlow<List<Recording>> = _recordingListFlow

    override suspend fun removeRecording(recording: Recording) {
        _recordingListFlow.value = _recordingListFlow.value.filter { it != recording }
        localRecordingDao.remove(recording)
    }
}
