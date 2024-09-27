/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.entities

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.random.Random

private const val BYTE_COUNT = 16
private const val VERSION_INDEX = 6
private const val VERSION_MASK_INVERSE = 0x0F.toByte()
private const val VERSION_VALUE_4 = 0x40.toByte()

data class Uuid(
    val bytes: ByteArray
) {

    operator fun get(index: Int): Byte = bytes[index]

    companion object {
        fun v4(): Uuid {
            val bytes = Random.Default.nextBytes(BYTE_COUNT)
            bytes[VERSION_INDEX] = (bytes[VERSION_INDEX] and VERSION_MASK_INVERSE) or VERSION_VALUE_4
            return Uuid(bytes)
        }
    }
}
