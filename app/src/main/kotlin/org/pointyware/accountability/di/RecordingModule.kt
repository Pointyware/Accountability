/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.pointyware.accountability.audio.AudioDao
import org.pointyware.accountability.audio.AudioOptionsDataSource
import org.pointyware.accountability.recording.AndroidRecordingRepository
import org.pointyware.accountability.recording.FileRecordingDao
import org.pointyware.accountability.recording.LocalRecordingDao
import org.pointyware.accountability.recording.RecordingManager
import org.pointyware.accountability.recording.RecordingRepository
import org.pointyware.accountability.recording.camera.CameraRecordingManager
import org.pointyware.accountability.settings.AndroidConfigurationRepository
import org.pointyware.accountability.settings.CallingOptionsAndroidDataSource
import org.pointyware.accountability.settings.CallingOptionsDataSource
import org.pointyware.accountability.settings.CameraOptionsDataSource
import org.pointyware.accountability.settings.CameraOptionsManagerDataSource
import org.pointyware.accountability.settings.ConfigurationRepository
import org.pointyware.accountability.settings.LocationAndroidDataSource
import org.pointyware.accountability.settings.LocationDataSource
import org.pointyware.accountability.settings.SharedPreferencesAndroidDataSource
import org.pointyware.accountability.settings.SharedPreferencesDataSource
import org.pointyware.accountability.storage.ConfigurableFileProvider
import org.pointyware.accountability.storage.FileProvider
import org.pointyware.accountability.viewer.AndroidPreviewSurfaceProvider
import org.pointyware.accountability.viewer.PreviewSurfaceProvider

/**
 */
@Module
@InstallIn(SingletonComponent::class)
interface RecordingModule {

    @Binds
    fun getRecordingRepository(androidRecordingRepository: AndroidRecordingRepository): RecordingRepository
    @Binds
    fun getConfigurationRepository(androidConfigurationRepository: AndroidConfigurationRepository): ConfigurationRepository
    @Binds
    fun getCameraOptionsDataSource(cameraOptionsManagerDataSource: CameraOptionsManagerDataSource): CameraOptionsDataSource
    @Binds
    fun getAudioOptionsDataSource(audioDao: AudioDao): AudioOptionsDataSource
    @Binds
    fun getLocationDataSource(locationAndroidDataSource: LocationAndroidDataSource): LocationDataSource
    @Binds
    fun getCallingOptionsDataSource(callingOptionsAndroidDataSource: CallingOptionsAndroidDataSource): CallingOptionsDataSource
    @Binds
    fun getSharedPreferencesDataSource(sharedPreferencesAndroidDataSource: SharedPreferencesAndroidDataSource): SharedPreferencesDataSource
    @Binds
    fun getRecordingManager(cameraRecordingManager: CameraRecordingManager): RecordingManager
//    @Binds
//    fun getNotificationOutput(notificationCoordinator: NotificationCoordinator): NotificationOutput
    @Binds
    fun getLocalRecordingDao(fileRecordingDao: FileRecordingDao): LocalRecordingDao
    @Binds
    fun getFileProvider(configurableFileProvider: ConfigurableFileProvider): FileProvider
//
    @Binds
    fun getPreviewSurfaceProvider(androidPreviewSurfaceProvider: AndroidPreviewSurfaceProvider): PreviewSurfaceProvider
//
//    @Binds
//    fun getPermissionVerifier(androidPermissionVerifier: AndroidPermissionVerifier): PermissionVerifier
//    @Binds
//    fun getPermissionRequestLauncher(androidPermissionRequestLauncher: AndroidPermissionRequestLauncher): PermissionRequestLauncher
}
