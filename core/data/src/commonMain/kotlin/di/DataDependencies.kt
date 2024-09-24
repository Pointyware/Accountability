package org.pointyware.placeholder.core.data.di

import org.koin.core.component.KoinComponent

/**
 */
interface DataDependencies {
//    fun provideFundsRepository(): FundsRepository
}

class KoinDataDependencies: DataDependencies, KoinComponent {
//    override fun provideFundsRepository(): FundsRepository = get()
}
