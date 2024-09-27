/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.di

import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.MediaRecorder
import android.os.Handler
import android.os.HandlerThread
import androidx.core.content.getSystemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 */
@Module
@InstallIn(SingletonComponent::class)
class CameraModule {

    @Provides
    fun getCameraManager(@ApplicationContext context: Context): CameraManager {
        return context.getSystemService()!!
    }

    @Provides
    fun getMediaRecorder(): MediaRecorder {
        return MediaRecorder()
    }

    @Provides
    fun getHandler(): Handler = HandlerThread("CameraLooper").let { thread ->
            thread.start()
            Handler(thread.looper)
        }
}
