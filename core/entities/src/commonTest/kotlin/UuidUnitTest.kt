/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.entities

import kotlin.experimental.and
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 */
class UuidUnitTest {

    @Test
    fun `version 4 - random uuid`() {
        val uuid = Uuid.v4()

        assertEquals(0x40.toByte(), uuid[6] and 0xF0.toByte())
    }
}
