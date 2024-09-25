/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.settings

import org.pointyware.accountability.storage.StorageLocation

/**
 * TODO: describe purpose/intent of LocationDataSource
 */
interface LocationDataSource {
    fun getLocation(): StorageLocation
    fun setLocation(location: StorageLocation)
}
