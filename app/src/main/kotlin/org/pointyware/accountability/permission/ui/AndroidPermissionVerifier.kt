/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import org.pointyware.accountability.permission.Permission
import org.pointyware.accountability.permission.PermissionVerifier
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Checks if the given permission is granted by the Android system.
 *
 * TODO: write tests
 */
class AndroidPermissionVerifier @Inject constructor(
    @ApplicationContext private val context: Context
): PermissionVerifier {

    override suspend fun hasPermission(permission: Permission): Boolean {
        return ContextCompat.checkSelfPermission(context, permission.key) ==
                PackageManager.PERMISSION_GRANTED
    }
}
