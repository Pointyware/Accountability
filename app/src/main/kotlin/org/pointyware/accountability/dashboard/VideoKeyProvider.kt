package org.pointyware.accountability.dashboard

import androidx.recyclerview.selection.ItemKeyProvider
import org.pointyware.accountability.recording.Recording
import org.pointyware.clean.Framework

/**
 */
@Framework
class VideoKeyProvider(
    var recordingList: List<Recording> = emptyList()
): ItemKeyProvider<String>(SCOPE_MAPPED) {

    override fun getKey(position: Int): String? {
        /*
         it's possible that video list can change between different calls, always grab a local
         reference and check range
         */
        return recordingList.let { list ->
            if (position in list.indices) {
                list[position].name
            } else {
                null
            }
        }
    }

    override fun getPosition(key: String): Int {
        return recordingList.indexOfFirst { it.name == key }
    }
}
