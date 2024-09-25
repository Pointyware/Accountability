package org.pointyware.accountability.storage

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference

/**
 * A simple ListPreference that presents the user with available storage locations.
 * // TODO: replace dao usage and bind UI values in fragment from view model
 */
class StoragePreference(context: Context, attrs: AttributeSet): ListPreference(context, attrs) {

    // TODO: assign values in fragment from viewmodel
//    init {
//        val storageDao = StorageDao(context.applicationContext as Application,
//            PreferenceManager.getDefaultSharedPreferences(context))
//
//        entryValues = storageDao.storageNames()
//        entries = entryValues
//
//        summaryProvider = SummaryProvider<StoragePreference> {
//            context.getString(R.string.summary_storage_location).format(storageDao.selectedLocation.name)
//        }
//    }
}
