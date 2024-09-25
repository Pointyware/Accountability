/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import org.pointyware.accountability.permission.Permission

/**
 * UI data class that presents the current status of a given permission.
 */
data class PermissionItem(
    /**
     * The permission whose status this item represents.
     */
    val permission: Permission,
    /**
     * True if the system or user has previously granted permission. False otherwise.
     */
    val isGranted: Boolean
)
