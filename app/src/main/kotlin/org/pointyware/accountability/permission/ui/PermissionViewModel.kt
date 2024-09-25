/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.pointyware.accountability.permission.Permission
import org.pointyware.accountability.permission.PermissionVerifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val permissionVerifier: PermissionVerifier,
): ViewModel() {

    private val _permissionList = MutableStateFlow<Array<PermissionItem>>(emptyArray())
    val permissionArray: StateFlow<Array<PermissionItem>> = _permissionList

    fun setPermissions(permissions: List<Permission>) {
        viewModelScope.launch {
            _permissionList.value = Array(permissions.size) { index ->
                val permission = permissions[index]

                // requires all strings to have at least one character
                PermissionItem(
                    permission,
                    permissionVerifier.hasPermission(permission)
                )
            }
        }
    }
}
