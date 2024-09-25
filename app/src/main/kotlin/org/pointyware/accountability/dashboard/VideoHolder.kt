package org.pointyware.accountability.dashboard

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.pointyware.accountability.R
import org.pointyware.accountability.recording.Recording
import timber.log.Timber
import java.io.File
import java.io.IOException

/**
 */
class VideoHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.videoLabel)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private var position: Int? = null
    var recording: Recording? = null

    fun getItemDetails(): VideoDetails? {
        val position = position
        val recording = recording
        return if (position != null && recording != null) {
            VideoDetails(position, recording)
        } else {
            null
        }
    }

    fun bindVideo(recording: Recording, position: Int) {

        val size = Size(imageView.maxWidth, imageView.maxHeight)
        textView.text = recording.name
        // TODO: handle loading on another thread
        imageView.setImageBitmap(loadThumbnail(recording.file, size))
        this.position = position
        this.recording = recording
    }

    /**
     * Loads a Uri as a bitmap of the requested size.
     * @param size Size of the returned bitmap
     * @return A sized bitmap of an image resource.
     */
    private fun loadThumbnail(file: File, size: Size): Bitmap? {
        return if (Build.VERSION.SDK_INT >= 29) {
            try {
                ThumbnailUtils.createVideoThumbnail(file, size, null)
            } catch (io: IOException) {
                Timber.e(io)
                null
            } catch (ia: IllegalArgumentException) {
                Timber.e(ia)
                null
            }
        } else {
            @Suppress("deprecation")
            return ThumbnailUtils.createVideoThumbnail(
                file.absolutePath,
                MediaStore.Images.Thumbnails.MINI_KIND)
        }
    }
}
