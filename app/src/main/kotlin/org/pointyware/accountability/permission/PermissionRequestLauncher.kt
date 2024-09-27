/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission


/**
 * Starts a process to request the permissions passed in [request] on the system.
 */
interface PermissionRequestLauncher {
    fun request(permissions: List<Permission>)
}
