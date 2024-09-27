/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.content.edit
import org.pointyware.accountability.R
import org.pointyware.accountability.storage.StorageLocation
import javax.inject.Inject

/**
 */
class LocationAndroidDataSource @Inject constructor(
    resources: Resources,
    private val sharedPreferences: SharedPreferences
): LocationDataSource {

    private val key = resources.getString(R.string.pStorageLocation)

    // https://github.com/Pointyware/Accountability/issues/14
    override fun getLocation(): StorageLocation {
        return sharedPreferences.getString(key, StorageLocation.External.name)?.let {
            StorageLocation.valueOf(it)
        } ?: StorageLocation.External
    }

    override fun setLocation(location: StorageLocation) {
        sharedPreferences.edit { putString(key, location.name) }
    }
}
