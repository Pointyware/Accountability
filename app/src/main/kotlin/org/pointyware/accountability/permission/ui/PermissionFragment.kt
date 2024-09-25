/*
 * Copyright (c) 2022-2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.permission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.pointyware.accountability.R
import org.pointyware.accountability.databinding.FragmentPermissionBinding
import javax.inject.Inject

@AndroidEntryPoint
class PermissionFragment : Fragment() {

    companion object {
        fun newInstance() = PermissionFragment()
    }

    private val viewModel by activityViewModels<PermissionViewModel>()
    @Inject
    lateinit var permissionAdapter: PermissionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPermissionBinding.bind(view)
        val recycler: RecyclerView = binding.recyclerViewPermissions
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = permissionAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.permissionArray.collect { permissionArray ->
                        permissionAdapter.permissionArray = permissionArray
                    }
                }
            }
        }
    }
}
