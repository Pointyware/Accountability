package org.pointyware.accountability.storage.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import org.pointyware.accountability.R

/**
 * Display a horizontal "bar" with 3 sections: app-specific use, other used space,
 * remaining free space.
 */
class StorageBar(context: Context, attrs: AttributeSet): View(context, attrs) {

    // TODO: make attributes and load
    private val unusablePaint = Paint().apply { color = ResourcesCompat.getColor(resources, R.color.storage_unusable, null) }
    private val videoPaint = Paint().apply { color = ResourcesCompat.getColor(resources, R.color.storage_videos, null) }
    private val freePaint = Paint().apply { color = ResourcesCompat.getColor(resources, R.color.storage_free, null) }

    /**
     * Total space on some storage media.
     */
    var total: Long = 10
    set(value) {
        this.invalidate()
        field = if (value > 0) value else 1 // do not allow total to be 0
    }

    /**
     * Amount of space reported free by the storage media.
     */
    var free: Long = 0
    set(value) {
        this.invalidate()
        field = value
    }

    /**
     * Amount of space taken up by videos.
     */
    var videos: Long = 0
    set(value) {
        this.invalidate()
        field = value
    }

    // TODO: make previewable in layout editor
    override fun onDraw(canvas: Canvas) {
        val percentUnused = free.toFloat() / total
        val percentVideos = videos.toFloat() / total
        val freeLength = width * percentUnused
        val videoLength = width * percentVideos

        val start = 0f
        val freeStart = width - freeLength
        val videoStart = freeStart - videoLength
        val end = width.toFloat()

        val top = 0f
        val bottom = height.toFloat()

        canvas.drawRect(start, top, videoStart, bottom, unusablePaint)
        canvas.drawRect(videoStart, top, freeStart, bottom, videoPaint)
        canvas.drawRect(freeStart, top, end, bottom, freePaint)
    }
}
