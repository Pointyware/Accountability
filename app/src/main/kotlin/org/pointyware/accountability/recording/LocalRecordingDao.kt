/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording

/**
 */
interface LocalRecordingDao {
    fun add(recording: Recording)
    val recordingList: List<Recording>
    fun remove(recording: Recording)
}
