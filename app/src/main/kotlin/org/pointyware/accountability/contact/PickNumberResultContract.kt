package org.pointyware.accountability.contact

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import org.pointyware.clean.Framework

/**
 * Very similar to [ActivityResultContracts.PickContact] which uses [ContactsContract.Contacts.CONTENT_TYPE]
 * as its intent type. This contract instead uses [ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE] to
 * prompt the user to select a specific number.
 */
class PickNumberResultContract: ActivityResultContract<Unit, Uri?>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        // ContactsContract.Contacts.CONTENT_TYPE
        return Intent(Intent.ACTION_PICK).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == RESULT_OK) { intent?.data } else { null }
    }
}
