/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.data.di

import org.koin.core.component.KoinComponent

/**
 */
interface DataDependencies {
//    fun provideFundsRepository(): FundsRepository
}

class KoinDataDependencies: DataDependencies, KoinComponent {
//    override fun provideFundsRepository(): FundsRepository = get()
}
