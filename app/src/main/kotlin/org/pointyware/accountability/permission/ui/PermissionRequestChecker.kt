/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import android.app.Activity
import androidx.core.app.ActivityCompat
import org.pointyware.accountability.permission.Permission
import javax.inject.Inject

/**
 * Takes an [Activity] as a constructor arg which is retained as a field, which could cause a
 * memory leak, but this should only be used in the `RecyclerView` adapter which is destroyed
 * when the UI is destroyed, which will alleviate the dependency cycle.
 */
class PermissionRequestChecker @Inject constructor(
    private val activity: Activity
) {
    /**
     * Checks if the user has previously denied this permission and further permission requests
     * will be circumvented by the system.
     * @return True if requesting a permission will be blocked by the system; False if the
     * system will present the request to the user. Undefined if a permission doesn't need request.
     */
    fun requestBlocked(permission: Permission): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.key)
    }
}
