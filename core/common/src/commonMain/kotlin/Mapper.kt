/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.common

/**
 *
 */
interface Mapper<I, O> {
    fun map(input: I): O
}
