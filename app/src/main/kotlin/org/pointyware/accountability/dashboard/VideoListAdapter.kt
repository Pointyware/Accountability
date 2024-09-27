package org.pointyware.accountability.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import org.pointyware.accountability.R
import org.pointyware.accountability.recording.Recording

/**
 */
class VideoListAdapter(
    var recordingList: List<Recording>
) : RecyclerView.Adapter<VideoHolder>() {

    lateinit var selectionTracker: SelectionTracker<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideoHolder(layoutInflater.inflate(R.layout.item_video, parent, false))
    }

    override fun getItemCount(): Int {
        return recordingList.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        recordingList[position].also {
            holder.bindVideo(it, position)
        }
        holder.itemView.isActivated = selectionTracker.isSelected(holder.recording?.name)
    }
}
