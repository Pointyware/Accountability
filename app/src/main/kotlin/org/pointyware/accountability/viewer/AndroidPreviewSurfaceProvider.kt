package org.pointyware.accountability.viewer

import android.graphics.ImageFormat
import android.media.ImageReader
import android.view.Surface
import org.pointyware.accountability.settings.ConfigurationRepository
import kotlinx.coroutines.runBlocking
import org.pointyware.accountability.recording.VisualConfig
import javax.inject.Inject

/**
 *
 */
class AndroidPreviewSurfaceProvider @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): PreviewSurfaceProvider {

    private val visualConfig: VisualConfig
        get() {
        val configuration = runBlocking {
            configurationRepository.getRecordingConfiguration()
        }
        return configuration.video ?: throw IllegalStateException("Surface can only be retrieved " +
                "when recording configuration defines a visual configuration")
    }

    override val surface: Surface get() {
        return imageReader.surface // TODO: consider case where configuration may change
    }

    override val imageReader: ImageReader by lazy {
        val videoConfig = visualConfig
        ImageReader.newInstance(videoConfig.width, videoConfig.height, imageFormat, 2)
    }

    companion object {
        const val imageFormat = ImageFormat.YUV_420_888
    }
}
