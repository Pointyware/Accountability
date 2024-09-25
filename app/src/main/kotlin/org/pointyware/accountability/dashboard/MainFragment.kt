package org.pointyware.accountability.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.OnItemActivatedListener
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.pointyware.accountability.R
import org.pointyware.accountability.about.AboutActivity
import org.pointyware.accountability.databinding.ContentMainBinding
import org.pointyware.accountability.recording.Recording
import org.pointyware.accountability.settings.SettingsActivity
import timber.log.Timber

/**
 */
@AndroidEntryPoint
class MainFragment: Fragment(R.layout.content_main) {

    private lateinit var binding: ContentMainBinding

    private val historyViewModel: HistoryViewModel by viewModels()

    private lateinit var itemActivatedListener: ItemActivationListener
    private lateinit var keyProvider: VideoKeyProvider
    private lateinit var tracker: SelectionTracker<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        val listAdapter = VideoListAdapter(emptyList())

        binding.videoRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }

        val trackerId = "videoListTracker"
        val recycler = binding.videoRecycler
        keyProvider = VideoKeyProvider()
        val detailsLookup = VideoDetailsLookup(recycler)
        itemActivatedListener = ItemActivationListener(requireContext())

        tracker = SelectionTracker.Builder(
            trackerId, recycler, keyProvider, detailsLookup,
            StorageStrategy.createStringStorage()
        ).withOnItemActivatedListener(itemActivatedListener).build()

        listAdapter.selectionTracker = tracker
        tracker.addObserver(VideoSelectionObserver(tracker, this, VideoSelectionObserver.Callback()))

        tracker.onRestoreInstanceState(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            historyViewModel.storageUsed.collect {
                binding.storageBar.videos = it
            }

            historyViewModel.recordingList.collect {
                // TODO: keep reference to each type supporting selection and update here
                itemActivatedListener.recordingList = it
                keyProvider.recordingList = it
                listAdapter.recordingList = it
                listAdapter.notifyDataSetChanged() // TODO: use diff-util
            }
        }
        updateStorageBar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_settings -> {
                val intent = Intent(requireActivity(), SettingsActivity::class.java)
                // add parameters?
                startActivity(intent)
                true
            }
            R.id.menu_item_about -> {
                val intent = Intent(requireActivity(), AboutActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateStorageBar() {
        // progress bar should show total video usage out of sum of video usage and free space
//        val freeSpace = viewModel.storageMax() - viewModel.storageUsed()
//        val videoSpace = viewModel.videoSum.value ?: 0
//        storageSpaceBar.max = freeSpace + videoSpace
//        storageSpaceBar.progress = videoSpace
        binding.storageBar.apply {
            total = historyViewModel.storageMax().toLong()
        }
    }

    private class ItemActivationListener(
        val context: Context,
        var recordingList: List<Recording> = emptyList()
    ): OnItemActivatedListener<String> {

        override fun onItemActivated(
            item: ItemDetailsLookup.ItemDetails<String>,
            e: MotionEvent
        ): Boolean {

            Timber.v("Activated item at position ${item.position} with key ${item.selectionKey}")
//            if (!tracker.hasSelection()) {
//                // TODO: originally prevented user from opening videos while video selection is in progress.
//            }
            recordingList.let { videos ->
                val videoFile = videos[item.position].file
                Timber.i("Attempting to view video ($videoFile)")

                context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = FileProvider.getUriForFile(
                        context,
                        "org.pointyware.accountability.fileprovider", videoFile
                    )
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                })
            }
            return true
        }
    }
}
