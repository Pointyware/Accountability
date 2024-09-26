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
import org.pointyware.accountability.viewer.CallButtonUiState

/**
 * Implemented/Tested Preferences:
 *
 * Planned Preferences:
 * * Enable Video
 *     * Device
 *     * Resolution
 * * Enable Audio
 * * Enable Calling
 *     * Enable Friend
 *         * Select Contact
 *     * Enable Emergency Services
 *         * Select Emergency Number
 */
@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val settingsViewModel by activityViewModels<SettingsViewModel>()

    private lateinit var audioPreference: PermissionPreference
    private lateinit var videoPreference: PermissionPreference
    private lateinit var cameraPreference: CameraPreference
    private lateinit var resolutionPreference: ResolutionPreference

//    private val backupEnabledPreference: SwitchPreference? = null
//    private lateinit var gDrivePreference: GDrivePreference

    private lateinit var contactPreference: ContactPreference
    private lateinit var callOnStartPreference: SwitchPreference
    private lateinit var enableEmergencyNumber: SwitchPreference

//    private val shareLocationPreference: SwitchPreference? = null
//    private val distanceTimePreference: ListPreference? = null

//    private val disguisePreference: SwitchPreference? = null
//    private val titlePreference: EditTextPreference? = null
//    private val messagePreference: EditTextPreference? = null
//    private val iconPreference: ListPreference? = null
//    private val controlPreference: SwitchPreference? = null

    private lateinit var storagePreference: ListPreference

    private lateinit var pickContactLauncher: ActivityResultLauncher<Unit>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionResults ->
            permissionResults.forEach { (permission, result) ->
                when (permission) {
                    Manifest.permission.RECORD_AUDIO -> {}
                    Manifest.permission.CAMERA -> {}
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {}
                    Manifest.permission.CALL_PHONE -> {}
                    Manifest.permission.READ_CONTACTS -> {}
                }
            }
        }
    }

    private fun bindVideoPreferences(audioVideoSettingsUiState: AudioVideoSettingsUiState) {
        audioPreference.isEnabled = audioVideoSettingsUiState.audioEnabled
        videoPreference.isEnabled = audioVideoSettingsUiState.videoEnabled

        cameraPreference.setVideoConfig(audioVideoSettingsUiState.recordingConfig.video)
        resolutionPreference.setVideoConfig(audioVideoSettingsUiState.recordingConfig.video)
    }

    private fun bindCallingPreferences(callingSettingsUiState: CallingSettingsUiState) {
        contactPreference.isChecked = callingSettingsUiState.contactNumber is CallButtonUiState.Enabled
        callOnStartPreference.isChecked = callingSettingsUiState.callOnStart

    }

    private fun bindLocationPreferences(storageSettingsUiState: StorageSettingsUiState) {
        storagePreference.value = storageSettingsUiState.storageLocationString
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        /*
        // TODO: replace with gDriveResultContract

            if (resultCode == Activity.RESULT_OK) {
    //                gDrivePreference.setDriveConnected(true)
            } else {
    //                val key = resources.settingsKey(R.string.pBackupGDrive)
    //                findPreference<SwitchPreference>(key)?.isChecked = false
            }
         */

        audioPreference = findPreference(resources.getString(R.string.pAVAudio))!!
        videoPreference = findPreference(resources.getString(R.string.pAVVideo))!!
        cameraPreference = findPreference(resources.getString(R.string.pAVCamera))!!
        resolutionPreference = findPreference(resources.getString(R.string.pAVCameraResolution))!!

        contactPreference = findPreference(resources.getString(R.string.pCallingContact))!!
        callOnStartPreference = findPreference(resources.getString(R.string.pCallingOnStart))!!
        enableEmergencyNumber = findPreference(resources.getString(R.string.pCallingEmergency))!!

        storagePreference = findPreference(resources.getString(R.string.pStorageLocation))!!

        arrayOf(videoPreference, audioPreference, contactPreference).forEach {
            it.launcher = requestPermissionLauncher
        }

        pickContactLauncher = registerForActivityResult(PickNumberResultContract(), PickNumberResultCallback(contactPreference))
        contactPreference.contactLauncher = pickContactLauncher
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        TODO: Clean up SettingsFragment/ViewModel
          1. Define SettingsUiState
          2. Expose in SettingsViewModel
          3. Bind to Preferences
         */

        videoPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener {

            true
        }

        contactPreference.contactLauncher = pickContactLauncher
        contactPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            pickContactLauncher.launch(Unit)
            true
        }
    }
}
