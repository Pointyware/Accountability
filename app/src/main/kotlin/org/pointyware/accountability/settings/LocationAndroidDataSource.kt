package org.pointyware.accountability.settings

import org.pointyware.accountability.storage.StorageLocation
import javax.inject.Inject

/**
 */
class LocationAndroidDataSource @Inject constructor(

): LocationDataSource {
    override fun getLocation(): StorageLocation {
        TODO("Not yet implemented")
    }

    override fun setLocation(location: StorageLocation) {
        TODO("Not yet implemented")
    }
}
