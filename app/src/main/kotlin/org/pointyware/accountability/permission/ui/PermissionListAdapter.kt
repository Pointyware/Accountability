/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.pointyware.accountability.databinding.ItemPermissionBinding
import javax.inject.Inject

/**
 */
class PermissionListAdapter @Inject constructor(
    private val permissionRequestChecker: PermissionRequestChecker
): RecyclerView.Adapter<PermissionViewHolder>() {

    var permissionArray: Array<PermissionItem> = emptyArray()
    set(value) {
        notifyItemRangeRemoved(0, field.size)

        field = value

        notifyItemRangeInserted(0, value.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
        return PermissionViewHolder(
            ItemPermissionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        // permission array could potentially change while views are being bound
        permissionArray.getOrNull(position)?.also {
            val showDeniedDetail = !it.isGranted && permissionRequestChecker.requestBlocked(it.permission)
            holder.bind(it, showDeniedDetail)
        }
    }

    override fun getItemCount(): Int {
        return permissionArray.size
    }
}
