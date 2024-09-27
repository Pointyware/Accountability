/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.viewer

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.pointyware.accountability.databinding.ContentRecordingBinding

/**
 * This activity can be started at any time. If a recording is taking place, it should show the
 * current live recording. If no recording is taking place, one will be started.
 */
@AndroidEntryPoint
class ViewerActivity : AppCompatActivity() {

    private lateinit var previewCallButton: FloatingActionButton
    private lateinit var policeCallButton: FloatingActionButton
    private lateinit var previewImage: ImageView

    private val viewModel: ViewerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ContentRecordingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        previewCallButton = binding.previewCallButton
        policeCallButton = binding.policeCallButton
        previewImage = binding.recordingPreview

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.configurationState.collect { state ->
                    bindCallButtonToState(previewCallButton, state.friendlyCallState, viewModel::startFriendlyCall)
                    bindCallButtonToState(policeCallButton, state.emergencyCallState, viewModel::startEmergencyCall)
                }
            }
        }

        viewModel.onViewerOpened()
    }

    private fun bindCallButtonToState(
        button: FloatingActionButton,
        state: CallButtonUiState,
        callback: (number:String)->Unit
    ) {
        when (state) {
            is CallButtonUiState.Enabled -> {
                button.visibility = View.VISIBLE
                button.setOnClickListener {
                    callback(state.number)
                }
            }
            is CallButtonUiState.Disabled -> {
                button.visibility = View.GONE
            }
        }
    }
}
