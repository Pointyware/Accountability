package org.pointyware.accountability.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.pointyware.accountability.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * This activity is launched by the main "Accountability" app launcher.
 *
 * The layout contains a toolbar for navigation, video list, and a button to connect your google drive.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar_main))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .commit()
    }
}
