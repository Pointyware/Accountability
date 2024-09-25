/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import org.pointyware.accountability.storage.StorageLocation
import javax.inject.Inject

/**
 */
class LocationAndroidDataSource @Inject constructor(

): LocationDataSource {

    // https://github.com/Pointyware/Accountability/issues/14
    private var location: StorageLocation = StorageLocation.External
    override fun getLocation(): StorageLocation {
        return location
    }

    override fun setLocation(location: StorageLocation) {
        this.location = location
    }
}
