package org.pointyware.accountability.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.pointyware.accountability.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * This activity exists only to embed the fragment.
 * The fragment layout could be loaded directly, but we may as well prevent refactoring if we want
 * to use it elsewhere later.
 */
@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commitNow()
        }
    }
}
