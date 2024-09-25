package org.pointyware.accountability.dashboard

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.selection.SelectionTracker
import org.pointyware.accountability.R
import timber.log.Timber

/**
 */
class VideoSelectionObserver(
    private val selectionTracker: SelectionTracker<String>,
    private val fragment: MainFragment,
    private val actionModeCallback: Callback
): SelectionTracker.SelectionObserver<String>() {

    private val actionMode: ActionMode? = null

    override fun onSelectionChanged() {
        Timber.v("Tracker has selection? ${selectionTracker.hasSelection()}")
        if (actionMode == null && selectionTracker.hasSelection()) {
            // if no action mode started and we have a selection
            fragment.requireActivity().startActionMode(actionModeCallback)
        } else if (!selectionTracker.hasSelection()) {
            actionMode?.finish()
        }
    }

    class Callback() : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//            actionMode = mode TODO
//            menuInflater.inflate(R.menu.video_action, menu)
            menu?.getItem(0)?.setOnMenuItemClickListener {
                Timber.v("Clicked delete button")
                false
            }
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//         do nothing
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menu_item_delete -> {
                    Timber.v("Delete Selected Videos")

//                    val selectedVideos = mutableListOf<Video>() FIXME
//                    viewModel.videoList.value?.forEach {
//                        if (tracker.selection.contains(it.name)) {
//                            selectedVideos.add(it)
//                        }
//                    }
//                    viewModel.deleteVideos(selectedVideos.toTypedArray())
                    true
                }
                else -> {
                    Timber.v("Other action item clicked")
                    false
                }
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
//            actionMode = null FIXME
//            tracker.clearSelection()
        }
    }
}
