/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.recording

import android.Manifest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.StateFlow
import java.io.File

/**
 * Handles coordination with frameworks and drivers to manage a recording according to a given
 * configuration.
 */
interface RecordingManager {
    val state: StateFlow<RecordingState>

    @RequiresPermission(Manifest.permission.CAMERA)
    suspend fun startRecording(destination: File, config: RecordingConfig)
    // fun migrateConfiguration(config: RecordingConfig)
    suspend fun stopRecording()
}
