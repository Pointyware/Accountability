package org.pointyware.accountability.picture

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import org.pointyware.accountability.recording.VisualConfig

/**
 * So far, this class is just a wrapper around ListPreference.
 * In the future, it should preview which camera the user selects in a dialog.
 */
class CameraPreference(
    context: Context,
    attrs: AttributeSet
): ListPreference(context, attrs) {

    init {
        // TODO: assign values in fragment from viewmodel
//        val entryPoint = EntryPoints.get(context.applicationContext, CameraEntryPoint::class.java)
//        cameraDao = entryPoint.getCameraDao()
//
//        entries = cameraDao.cameraList
//        entryValues = entries
//        setDefaultValue(cameraDao.selectedCamera)
    }

    fun setVideoConfig(config: VisualConfig?) {
        setDefaultValue(config?.cameraId)
    }
}
