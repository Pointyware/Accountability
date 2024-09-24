package org.pointyware.accountability.settings

import org.pointyware.accountability.storage.StorageLocation
import org.pointyware.clean.Framework
import javax.inject.Inject

/**
 */
@Framework
class LocationAndroidDataSource @Inject constructor(

): LocationDataSource {
    override fun getLocation(): StorageLocation {
        TODO("Not yet implemented")
    }

    override fun setLocation(location: StorageLocation) {
        TODO("Not yet implemented")
    }
}
