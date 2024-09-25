package org.pointyware.accountability.about

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.pointyware.accountability.R

/**
 * Small Activity displaying the logo, version number, and buttons to:
 * 1. submit feedback via email
 * 2. view change log
 * 3. share the app
 * 4. rate the app
 */
@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {

//    @Inject
//    lateinit var feedbackSender: FeedbackSender
//
//    @Inject
//    lateinit var changeLog: ChangeLogFragment
//
//    @Inject
//    lateinit var sharer: Sharer
//
//    @Inject
//    lateinit var rating: Reviewer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        findViewById<Button>(R.id.about_feedback).setOnClickListener {
//            feedbackSender.openFeedback()
        }

        findViewById<Button>(R.id.about_changes).setOnClickListener {
//            changeLog.openChangeLog()
        }

        findViewById<Button>(R.id.about_sharing).setOnClickListener {
//            sharer.openSharing()
        }

        findViewById<Button>(R.id.about_rating).setOnClickListener {
//            rating.openRating()
        }
    }
}
