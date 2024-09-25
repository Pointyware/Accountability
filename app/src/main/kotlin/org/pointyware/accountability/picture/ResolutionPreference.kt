package org.pointyware.accountability.picture

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference
import androidx.preference.Preference
import timber.log.Timber

/**
 */
class ResolutionPreference(
    context: Context, attrs: AttributeSet
): ListPreference(context, attrs) {

    init {
//        summaryProvider = SummaryProvider<ResolutionPreference> {
            // TODO: get resolution from repository or set summaryProvider in fragment
//            context.getString(R.string.summary_template_resolution, size.toString())
//        }

        initEntries()
    }

    private fun initEntries() {
        // TODO: assign entries in fragment from viewmodel
//        entries = resolutionDao.resolutionStringsForCamera(cameraDao.selectedCamera)
//        entryValues = entries
//        resolutionDao.preferredResolution?.let { size ->
//            val sizeString = size.toString()
//            value = if (entries.contains(sizeString)) {
//                size.toString()
//            } else {
//                null
//            }
//        }
    }

    override fun onAttached() {
        super.onAttached()

        sharedPreferences?.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == dependency) {
                initEntries()
            }
        }
    }

    override fun onDependencyChanged(dependency: Preference, disableDependent: Boolean) {
        super.onDependencyChanged(dependency, disableDependent)
        Timber.v("dependency changed")
        // TODO: move to more suitable location
        initEntries()
    }
}
