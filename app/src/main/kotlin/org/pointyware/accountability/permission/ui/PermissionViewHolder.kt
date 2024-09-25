/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.pointyware.accountability.R
import org.pointyware.accountability.databinding.ItemPermissionBinding

/**
 *
 */
class PermissionViewHolder(
    private val binding: ItemPermissionBinding
): RecyclerView.ViewHolder(binding.root) {

    /**
     * @param item The object representing the permission state
     */
    fun bind(item: PermissionItem, requestsBlocked: Boolean) {
        binding.textPermissionName.setText(item.permission.title)
        binding.textPermissionUse.setText(item.permission.use)
        binding.textRequestDenied.isVisible = requestsBlocked
        binding.imageViewPermissionStatus.setImageResource(
            if (item.isGranted) R.drawable.ic_baseline_check_24 else R.drawable.ic_baseline_close_24
        )
    }
}
