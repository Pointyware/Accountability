/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.pointyware.accountability.R
import org.pointyware.accountability.permission.Permission

/**
 * An activity that handles acquisition of permissions when recording is requested in
 * the background. Requesting permission from the user through the system requires an Activity, so
 * if the system has removed permissions from the app, this activity will be launched to request
 * those permissions, otherwise the recording service can only be shut down.
 */
class PermissionActivity : AppCompatActivity() {

    companion object {
        private const val PACKAGE = "org.pointyware.accountability.permission.ui"

        /**
         * An array of [Permission] ordinal ids.
         */
        const val EXTRA_PERMISSION_IDS = "$PACKAGE.permission_list"
        const val RESULT_EXTRA_GRANTED = "$PACKAGE.granted"
    }

    private val permissionViewModel by viewModels<PermissionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PermissionFragment.newInstance())
                .commitNow()
        }

        val permissions = intent?.getIntArrayExtra(EXTRA_PERMISSION_IDS) ?: IntArray(0)
        if (permissions.isEmpty()) {
            sendPositiveResult()
        } else {
            permissionViewModel.setPermissions(Permission.permissionArray(permissions).filterNotNull())
        }
    }

    private fun sendPositiveResult() {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(RESULT_EXTRA_GRANTED, true)
        )
        finish()
    }
}
