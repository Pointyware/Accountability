package org.pointyware.accountability.permission

import android.Manifest
import androidx.annotation.StringRes
import org.pointyware.accountability.R

/**
 * Represents a permission in a system that the app may use.
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
            val allValues = values()
            return Array(ordinalArray.size) { ordinal ->
                allValues.getOrNull(ordinal)
            }
        }
    }
}
