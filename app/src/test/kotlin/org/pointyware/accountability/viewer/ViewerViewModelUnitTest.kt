/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.viewer

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.pointyware.accountability.calling.CallingConfig
import org.pointyware.accountability.calling.StartCallUseCase
import org.pointyware.accountability.recording.StartRecordingUseCase
import org.pointyware.accountability.recording.StopRecordingUseCase
import org.pointyware.accountability.settings.ConfigurationRepository

/**
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ViewerViewModelUnitTest {

    private lateinit var configurationRepository: ConfigurationRepository
    private lateinit var startRecordingUseCase: StartRecordingUseCase
    private lateinit var stopRecordingUseCase: StopRecordingUseCase
    private lateinit var startCallUseCase: StartCallUseCase
    private lateinit var viewModel: ViewerViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        configurationRepository = mockk(relaxed = true)
        startRecordingUseCase = mockk(relaxed = true)
        stopRecordingUseCase = mockk(relaxed = true)
        startCallUseCase = mockk(relaxed = true)
        viewModel = ViewerViewModel(
            configurationRepository = configurationRepository,
            startRecordingUseCase = startRecordingUseCase,
            stopRecordingUseCase = stopRecordingUseCase,
            startCallUseCase = startCallUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getConfigurationState() = runTest {
        /*
        Given:
        - A configuration repository with a calling configuration
         */
        coEvery { configurationRepository.getCallingConfiguration() } returns CallingConfig(
            emergencyNumber = null,
            contactNumber = "123-456-7890",
            callOnStart = false
        )

        /*
        When:
        - The configuration state is requested
         */
        val state = viewModel.configurationState.take(2).toList()

        /*
        Then:
        - The state list contains two states
         */
        assertEquals(ViewerUiState(), state[0])
        assertEquals(
            ViewerUiState(
                emergencyCallState = CallButtonUiState.Disabled,
                friendlyCallState = CallButtonUiState.Enabled("123-456-7890")
            ),
            state[1]
        )
    }

    @Test
    fun startRecording() = runTest {
        viewModel.startRecording()

        advanceUntilIdle()

        coVerify { startRecordingUseCase.invoke() }
    }

    @Test
    fun stopRecording() = runTest {
        viewModel.stopRecording()
        advanceUntilIdle()

        coVerify { stopRecordingUseCase.invoke() }
    }

    @Test
    fun onViewerOpened_call_on_open_disabled() = runTest {
        /*
        Given:
        - record on open is disabled
         */
        coEvery { configurationRepository.getCallingConfiguration() } returns CallingConfig(
            emergencyNumber = "911",
            contactNumber = "123-456-7890",
            callOnStart = false
        )

        /*
        When:
        - The viewer opened event is triggered
         */
        viewModel.onViewerOpened()
        advanceUntilIdle()

        /*
        Then:
        - No call is started
         */
        coVerify(inverse = true) { startCallUseCase.invoke(true, "123-456-789") }
    }

    @Test
    fun onViewerOpened_call_on_open_enabled() = runTest {
        /*
        Given:
        - record on open is disabled
         */
        coEvery { configurationRepository.getCallingConfiguration() } returns CallingConfig(
            emergencyNumber = "911",
            contactNumber = "123-456-7890",
            callOnStart = true
        )

        /*
        When:
        - The viewer opened event is triggered
         */
        viewModel.onViewerOpened()
        advanceUntilIdle()

        /*
        Then:
        - No call is started
         */
        coVerify { startCallUseCase.invoke(true, "123-456-7890") }
    }

    @Test
    fun startFriendlyCall() = runTest {
        /*
        Given:
        - A friend's number is saved in the config
         */
        coEvery { configurationRepository.getCallingConfiguration() } returns CallingConfig(
            emergencyNumber = "911",
            contactNumber = "123-456-7890",
            callOnStart = false
        )

        /*
        When:
        - A friendly call is triggered
         */
        viewModel.startFriendlyCall("123-456-7890")
        advanceUntilIdle()

        /*
        Then:
        - The call is started
         */
        coVerify { startCallUseCase.invoke(true, "123-456-7890") }
    }

    @Test
    fun startEmergencyCall() = runTest {
        /*
        Given:
        - An emergency number is saved in the config
         */
        coEvery { configurationRepository.getCallingConfiguration() } returns CallingConfig(
            emergencyNumber = "911",
            contactNumber = "123-456-7890",
            callOnStart = false
        )

        /*
        When:
        - An emergency call is triggered
         */
        viewModel.startEmergencyCall("911")
        advanceUntilIdle()

        /*
        Then:
        - The call is started
         */
        coVerify { startCallUseCase.invoke(false, "911") }
    }
}
