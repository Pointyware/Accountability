package org.pointyware.accountability.recording

import java.io.File

/**
 * Basic description of a single recording.
 */
data class Recording constructor(
    val file: File,
    val type: RecordingType
) {
    /**
     * File name of recording
     */
    val name: String get() = file.name

    /**
     * The total number of bytes.
     */
    val size: Long get() = file.length()
}
