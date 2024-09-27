package org.pointyware.accountability.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.pointyware.accountability.recording.GetRecordingListUseCase
import org.pointyware.accountability.recording.Recording
import org.pointyware.accountability.recording.RemoveRecordingUseCase
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val removeRecordingUseCase: RemoveRecordingUseCase,
    private val getRecordingListUseCase: GetRecordingListUseCase,
//    private val recordingDao: VideoDao,
//    private val storageDao: StorageDao
): ViewModel() {

    val recordingList: StateFlow<List<Recording>>
    get() = getRecordingListUseCase.watch()

    fun deleteRecordings(vararg recordings: Recording) {
        viewModelScope.launch {
            for (recording in recordings) {
                removeRecordingUseCase(recording)
            }
        }
    }

    /**
     * Reports the total space used by recordings in Megabytes.
     */
    val storageUsed = recordingList.map {
        it.sumOf { recording -> recording.size }
    }

    fun storageMax(): Int {
        return 1000 //TODO: replace functionality of storageDao
    }

    // TODO: also need property for free space
}
