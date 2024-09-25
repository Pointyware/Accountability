package org.pointyware.accountability.recording.camera

import android.media.MediaRecorder
import android.view.Surface
import org.pointyware.accountability.recording.RecordingConfig
import java.io.File
import javax.inject.Inject

/**
 * Manages a [android.media.MediaRecorder] and its configuration.
 */
class CameraMediaRecorder @Inject constructor(
    private val mediaRecorder: MediaRecorder
) {

    /**
     * Configures the media recorder according to the given configuration with the given output
     * destination and returns the surface
     */
    fun configure(output: File, configuration: RecordingConfig): Surface {
        val recordingType = configuration.type ?: throw IllegalArgumentException("")
        val hasAudio: Boolean = recordingType.hasAudio
        val hasVideo: Boolean = recordingType.hasVideo

        if (hasAudio) {
            // configure audio
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
//            mediaRecorder.setAudioChannels()
//            mediaRecorder.setAudioSamplingRate()
//            mediaRecorder.setAudioEncodingBitRate()
        }

        if (hasVideo) {
            val videoConfig = configuration.video!!
            // configure video
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder.setVideoSize(videoConfig.width, videoConfig.height)
            // TODO: set video options
//            mediaRecorder.setVideoFrameRate()
//            mediaRecorder.setVideoEncodingBitRate()
//            mediaRecorder.setNextOutputFile() // TODO: read for details on implementing long recordings
        }

        // finish media configuration
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        if (hasAudio) {
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        }
        if (hasVideo) {
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
        }

        mediaRecorder.setOutputFile(output.absolutePath)

        // TODO: consider location data
//        mediaRecorder.setLocation()

        mediaRecorder.prepare()
        return mediaRecorder.surface
    }

    fun start() {
        mediaRecorder.start()
    }

    fun stop() {
        mediaRecorder.stop()
    }
}
