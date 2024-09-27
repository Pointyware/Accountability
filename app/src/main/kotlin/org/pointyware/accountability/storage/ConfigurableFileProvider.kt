/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import org.pointyware.accountability.settings.ConfigurationRepository
import java.io.File
import javax.inject.Inject

/**
 *
 *
 */
class ConfigurableFileProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val configurationRepository: ConfigurationRepository
): FileProvider {

    override fun directory(): File? {
        return when (runBlocking { configurationRepository.getStorageLocation() }) {
            StorageLocation.Internal -> context.filesDir
            StorageLocation.External -> context.getExternalFilesDir(null)
        }
    }

    private val disallowedCharacters = "/\\?%*:|\"<>.,;="

    override fun getFile(name: String, ext: String?): File {
        // determine root directory
        val directory = directory()

        fun sanitize(string: String?): String {
            var escapedString = string ?: ""
            if (escapedString.isNotEmpty()) {
                for (char in disallowedCharacters) {
                    escapedString = escapedString.replace(char, '_')
                }
            }
            return escapedString
        }
        // sanitize file name
        val fileName = sanitize(if (name.isEmpty()) { "_" } else { name })

        // sanitize extension
        var tail = sanitize(ext)
        if (tail.isNotEmpty()) {
            tail = ".$tail"
        }

        // find first non-conflict name
        fun getFile(prefix: String, suffix: String): File {
            return File(directory, "$prefix$suffix")
        }
        var tryCount = 0
        var file = getFile(fileName, tail)
        while(file.exists()) {
            tryCount++
            file = getFile("$fileName-$tryCount", tail)
        }
        return file
    }
}
