/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.storage

import java.io.File

/**
 * A provider of both files and the directory in which they will be placed.
 */
interface FileProvider {

    /**
     * Returns the parent directory of files returned from [getFile].
     */
    fun directory(): File?

    /**
     * Attempts to create a file with the given filename and extension.
     * Certain characters may cause problems when moving files to other systems, so many are
     * disallowed.
     * @see ConfigurableFileProvider.disallowedCharacters
     * @param ext extension characters excluding leading dot.
     */
    fun getFile(name: String, ext: String? = null): File
}
