package org.pointyware.accountability.settings

import org.pointyware.accountability.storage.StorageLocation

/**
 * TODO: describe purpose/intent of LocationDataSource
 */
interface LocationDataSource {
    fun getLocation(): StorageLocation
    fun setLocation(location: StorageLocation)
}
