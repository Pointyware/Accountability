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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
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

    // region Activity/Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ContentRecordingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        previewCallButton = binding.previewCallButton
        policeCallButton = binding.policeCallButton
        previewImage = binding.recordingPreview

        configureCallButtons()

        /* if we should call on start - onStart would trigger a call
        every time without storing state somehow. ew.
         */
        if (viewModel.callOnStart) {
            startFriendlyCall()
        }

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

    override fun onStart() {
//        Timber.v("onStart()")
        super.onStart()
    }

    override fun onResume() {
//        Timber.v("onResume()")
        super.onResume()
    }

    override fun onPause() {
//        Timber.v("onPause:")
        super.onPause()
    }

    override fun onStop() {
//        Timber.v("onStop:")
        super.onStop()
    }

    override fun onDestroy() {
//        Timber.v("onDestroy: ")
        super.onDestroy()
    }

    // endregion

    private fun configureCallButtons() {
        // setup preview surface
        if (viewModel.callingEnabled) {
            Timber.v("Calling Enabled")

            previewCallButton.visibility = if (viewModel.friendEnabled) {
                Timber.v("Friend Enabled")

                previewCallButton.setOnClickListener {
                    startFriendlyCall()
                }
                View.VISIBLE
            } else { View.GONE }

            policeCallButton.visibility = if (viewModel.policeEnabled) {
                Timber.v("Emergency Enabled")

                policeCallButton.setOnClickListener {
                    startEmergencyCall()
                }
                View.VISIBLE
            } else { View.GONE }

        } else {
            Timber.v("Calling Disabled")

            previewCallButton.visibility = View.GONE
            policeCallButton.visibility = View.GONE
        }
    }

    private fun startFriendlyCall() {
        Timber.v("Calling Friend")

        viewModel.contactNumber?.also {

            startCall(true, it)

        } ?: run {

            // if no number saved, open dialer
            startCall(false)
        }
    }

    private fun startEmergencyCall() {
        Timber.v("Dialing Emergency Services")

        startCall(false, viewModel.policeNumber)
    }

    private fun startCall(callAfterDial: Boolean, number: String = "") {
        val action = if (callAfterDial) { Intent.ACTION_CALL } else { Intent.ACTION_DIAL }
        val intent = Intent(action).apply {
            data = Uri.parse("tel:$number")
        }
        try {
            startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(baseContext, "Activity Could Not Be Found to Handle Calling", Toast.LENGTH_LONG).show()
        }
    }
}
