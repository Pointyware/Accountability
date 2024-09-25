package org.pointyware.accountability.viewer

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.pointyware.accountability.databinding.ContentRecordingBinding
import timber.log.Timber

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

        // TODO: check for permissions
//        PermissionChecker(this, audioDao, cameraDao, callingDao).ungrantedPermissions.let { permissions ->
//            if (permissions.isNotEmpty()) {
//                registerForActivityResult(
//                    ActivityResultContracts.RequestMultiplePermissions()
//                ) { grantMap ->
//                    if (grantMap.filterValues { isEnabled -> !isEnabled }.isNotEmpty()) {
//                        Timber.e("Some permissions were not granted. Can't start recording.")
//                        // TODO: if a permission is denied once (possibly on accident) the system will not ask the user again and just deny the request. This causes the app to close immediately. Present dialog explaining the necessary permission, so it closes only after user acknowledgement
//                        finish()
//                    } else {
////                        bindServiceAndPreview() TODO: retrieve preview surface and start presenting images from video
//                    }
//                }.launch(permissions.toTypedArray())
//            }
//        }
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
