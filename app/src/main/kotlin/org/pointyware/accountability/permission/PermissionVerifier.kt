/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission

/**
 * Checks an environment to see if some permission is granted.
 */
interface PermissionVerifier {
    /**
     * Checks if the user has granted the permission indicated by this string.
     */
    suspend fun hasPermission(permission: Permission): Boolean
}
