/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission

import android.content.Context
import android.content.Intent
import org.pointyware.accountability.permission.ui.PermissionActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Starts an activity to request permissions necessary for the current recording configuration.
 *
 * // TODO: write tests
 */
class AndroidPermissionRequestLauncher @Inject constructor(
    @ApplicationContext private val context: Context
): PermissionRequestLauncher {
    override fun request(permissions: List<Permission>) {
        val intent = Intent(context, PermissionActivity::class.java)
        val ordinalArray = IntArray(permissions.size) { index -> permissions[index].ordinal }
        intent.putExtra(PermissionActivity.EXTRA_PERMISSION_IDS, Permission.permissionArray(ordinalArray))
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        context.startActivity(intent)
    }
}
