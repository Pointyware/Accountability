package org.pointyware.accountability.dashboard

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import org.pointyware.clean.Framework

/**
 */
@Framework
class VideoDetailsLookup(
    private val videoRecycler: RecyclerView
): ItemDetailsLookup<String>() {

    override fun getItemDetails(e: MotionEvent): VideoDetails? {
        return videoRecycler.findChildViewUnder(e.x, e.y)?.let {
            val holder = (videoRecycler.getChildViewHolder(it) as? VideoHolder)
            return@let holder?.getItemDetails()
        }
    }
}
