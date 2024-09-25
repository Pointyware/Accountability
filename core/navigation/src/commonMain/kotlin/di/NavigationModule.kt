/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.navigation.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.pointyware.accountability.core.navigation.StackNavigationController
import org.pointyware.accountability.core.navigation.StackNavigationControllerImpl

/**
 *
 */
fun coreNavigationModule() = module {
    single<NavigationDependencies> { KoinNavigationDependencies() }

    single<StackNavigationController<Any, Any>> {
        StackNavigationControllerImpl(get<Any>(qualifier = named("home")))
    }
}
