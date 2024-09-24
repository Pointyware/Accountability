package org.pointyware.accountability.recording;

/**
 * Coarse description of a recording's media.
 */
enum class RecordingType {

    /**
     * Audio only
     */
    Audio,

    /**
     * Video only (no audio)
     */
    Video,

    /**
     * Audio and Video
     */
    AudioVideo;

    val hasAudio: Boolean
    get() = this != Video

    val hasVideo: Boolean
    get() = this != Audio

    companion object {
        /**
         * Return the type that indicates the given audio/video options.
         */
        fun getType(hasAudio: Boolean, hasVideo: Boolean): RecordingType? {
            return if (hasAudio) {
                if (hasVideo) {
                    AudioVideo
                } else {
                    Audio
                }
            } else {
                if (hasVideo) {
                    Video
                } else {
                    null
                }
            }
        }
    }
}
