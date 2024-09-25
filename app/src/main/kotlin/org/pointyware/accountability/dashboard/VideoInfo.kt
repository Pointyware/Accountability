package org.pointyware.accountability.dashboard

import androidx.recyclerview.selection.ItemDetailsLookup
import org.pointyware.accountability.recording.Recording
import org.pointyware.clean.Entity

/**
 */
data class VideoDetails(
    private val position: Int,
    private val recording: Recording
): ItemDetailsLookup.ItemDetails<String>() {
    override fun getPosition(): Int = position
    override fun getSelectionKey(): String = recording.name
}
