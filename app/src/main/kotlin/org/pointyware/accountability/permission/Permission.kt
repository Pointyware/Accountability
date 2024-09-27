/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission

import android.Manifest
import androidx.annotation.StringRes
import org.pointyware.accountability.R

/**
 * Represents a permission in a system that the app may use.
 *
 */
enum class Permission(
    /**
     * The string that identifies a permission within the system.
     */
    val key: String,
    /**
     * A unique id for a short, human-readable title of the permission.
     */
    @StringRes val title: Int,
    /**
     * A unique id for a longer, human-readable description of the permission.
     */
    @StringRes val use: Int
) {

    Camera(Manifest.permission.CAMERA, R.string.title_permission_camera, R.string.desc_permission_camera),
    Audio(Manifest.permission.RECORD_AUDIO, R.string.title_permission_audio, R.string.desc_permission_audio),
    Contacts(Manifest.permission.READ_CONTACTS, R.string.title_permission_contacts, R.string.desc_permission_contacts),
    Phone(Manifest.permission.CALL_PHONE, R.string.title_permission_phone, R.string.desc_permission_phone);

    companion object {
        /**
         * Utility function to convert an array of permissions into an int array.
         */
        fun indexArray(permissionArray: Array<Permission?>): IntArray {
            return IntArray(permissionArray.size) { index ->
                permissionArray[index]?.ordinal ?: -1
            }
        }

        /**
         * Utility function to convert an int array into an array of permissions.
         */
        fun permissionArray(ordinalArray: IntArray): Array<Permission?> {
            val allValues = entries.toTypedArray()
            return Array(ordinalArray.size) { ordinal ->
                allValues.getOrNull(ordinal)
            }
        }
    }
}

/*

        /*
        TODO: request permissions
        PermissionChecker(this, audioDao, cameraDao, callingDao).ungrantedPermissions.let { permissions ->
            if (permissions.isNotEmpty()) {
                registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { grantMap ->
                    if (grantMap.filterValues { isEnabled -> !isEnabled }.isNotEmpty()) {
                        Timber.e("Some permissions were not granted. Can't start recording.")
                        // TODO: if a permission is denied once (possibly on accident) the system will not ask the user again and just deny the request. This causes the app to close immediately. Present dialog explaining the necessary permission, so it closes only after user acknowledgement
                        finish()
                    } else {
//                        bindServiceAndPreview() TODO: retrieve preview surface and start presenting images from video
}
}.launch(permissions.toTypedArray())
}
}
*/
 */
