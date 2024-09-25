/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Defines base behavior for all view models.
 */
interface BaseViewModel {

    /**
     * The scope for the view model. The default uses the main dispatcher and a supervisor job.
     */
    val viewModelScope: CoroutineScope
        get() {
            return CoroutineScope(Dispatchers.Main + SupervisorJob())
        }
}
