package org.pointyware.accountability.settings

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.pointyware.accountability.R
import org.pointyware.accountability.contact.ContactPreference
import org.pointyware.accountability.contact.PickNumberResultCallback
import org.pointyware.accountability.contact.PickNumberResultContract
import org.pointyware.accountability.permission.PermissionPreference
import org.pointyware.accountability.picture.CameraPreference
import org.pointyware.accountability.picture.ResolutionPreference
import org.pointyware.accountability.storage.StorageLocation
import org.pointyware.accountability.viewer.CallButtonUiState

/**
 * Presents recording, calling, and storage preferences to the user to control their experience.
 */
@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val settingsViewModel by activityViewModels<SettingsViewModel>()

    private lateinit var isAudioEnabledPreference: PermissionPreference
    private lateinit var isCameraEnabledPreference: PermissionPreference
    private lateinit var selectedCameraPreference: CameraPreference
    private lateinit var selectedResolutionPreference: ResolutionPreference

    private lateinit var contactPreference: ContactPreference
    private lateinit var callOnStartPreference: SwitchPreference
    private lateinit var enableEmergencyNumber: SwitchPreference

    private lateinit var storagePreference: ListPreference

    private lateinit var pickContactLauncher: ActivityResultLauncher<Unit>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            ::handlePermissionResults)

        isAudioEnabledPreference = findPreference(resources.getString(R.string.pAVAudio))!!
        isCameraEnabledPreference = findPreference(resources.getString(R.string.pAVVideo))!!
        selectedCameraPreference = findPreference(resources.getString(R.string.pAVCamera))!!
        selectedResolutionPreference = findPreference(resources.getString(R.string.pAVCameraResolution))!!

        contactPreference = findPreference(resources.getString(R.string.pCallingContact))!!
        callOnStartPreference = findPreference(resources.getString(R.string.pCallingOnStart))!!
        enableEmergencyNumber = findPreference(resources.getString(R.string.pCallingEmergency))!!

        storagePreference = findPreference(resources.getString(R.string.pStorageLocation))!!

        arrayOf(isCameraEnabledPreference, isAudioEnabledPreference, contactPreference).forEach {
            it.launcher = requestPermissionLauncher
        }

        pickContactLauncher = registerForActivityResult(PickNumberResultContract(), PickNumberResultCallback(contactPreference))
        contactPreference.contactLauncher = pickContactLauncher
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactPreference.contactLauncher = pickContactLauncher
        contactPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            pickContactLauncher.launch(Unit)
            true
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                settingsViewModel.state.collect { state ->
                    state?.let {
                        bindVideoPreferences(it.audioVideoSettings)
                        bindCallingPreferences(it.callingSettings)
                        bindLocationPreferences(it.storageSettings)
                    }
                }
            }
        }
    }

    private fun handlePermissionResults(permissionResults: Map<String, Boolean>) {
        permissionResults.forEach { (permission, result) ->
            when (permission) {
                Manifest.permission.RECORD_AUDIO -> {
                    if (!result) {
                        isAudioEnabledPreference.isChecked = false
                    }
                }
                Manifest.permission.CAMERA -> {
                    if (!result) {
                        isCameraEnabledPreference.isChecked = false
                    }
                }
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                    if (!result) {
                        storagePreference.value = StorageLocation.Internal.name
                    }
                }
                Manifest.permission.CALL_PHONE -> {
                    if (!result) {
                        contactPreference.isChecked = false
                    }
                }
            }
        }
    }

    private fun bindVideoPreferences(audioVideoSettingsUiState: AudioVideoSettingsUiState) {
        isAudioEnabledPreference.isChecked = audioVideoSettingsUiState.audioEnabled
        isCameraEnabledPreference.isChecked = audioVideoSettingsUiState.videoEnabled

        selectedCameraPreference.setVideoConfig(audioVideoSettingsUiState.recordingConfig.video)
        selectedResolutionPreference.setVideoConfig(audioVideoSettingsUiState.recordingConfig.video)
    }

    private fun bindCallingPreferences(callingSettingsUiState: CallingSettingsUiState) {
        contactPreference.isChecked = callingSettingsUiState.contactNumber is CallButtonUiState.Enabled
        callOnStartPreference.isChecked = callingSettingsUiState.callOnStart

    }

    private fun bindLocationPreferences(storageSettingsUiState: StorageSettingsUiState) {
        storagePreference.value = storageSettingsUiState.storageLocationString
    }
}
