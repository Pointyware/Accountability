/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "org.pointyware.accountability"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "org.pointyware.accountability.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    viewBinding.enable = true
    buildFeatures {
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes.add("META-INF/LICENSE*.md")
        }
    }
    testOptions {
        unitTests {
//            includeAndroidResources = true
        }
    }

//    useLibrary 'android.test.runner'
//
//    useLibrary 'android.test.base'
//    useLibrary 'android.test.mock'
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.feature.about)
    implementation(projects.feature.calling)
    implementation(projects.feature.recording)

    implementation(libs.timber)

    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.activityKtx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.preference)

    // still need to include view-based material system
    implementation(libs.google.material)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)


    debugImplementation(libs.androidx.compose.manifest)

    testImplementation(libs.junit4)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutinesTest)
//    implementation fileTree(dir: "libs", include: ["*.jar"])
//
//    def fragment_version = '1.4.1'
//    def testing_version = '1.4.0'
//    def lifecycle_version = '2.4.1'
//
//    implementation "com.google.dagger:hilt-android:$hilt_version"
//    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1'
//    kapt "com.google.dagger:hilt-compiler:$hilt_version"
//
//    // Hilt testing dependency
//    androidTestImplementation "com.google.dagger:hilt-android-testing:$hilt_version"
//    // Make Hilt generate code in the androidTest folder
//    kaptAndroidTest "com.google.dagger:hilt-android-compiler:$hilt_version"
//
//
//    // Kotlin support and extensions
//    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
//    implementation 'androidx.core:core-ktx:1.7.0'
//    // - Lifecycle
//    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
//    implementation "androidx.lifecycle:lifecycle-common:$lifecycle_version"
//    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
//    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
//    implementation "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"
//
//    // - Fragment
//    implementation "androidx.fragment:fragment-ktx:$fragment_version"
//
//    // - Nav
//    implementation 'androidx.navigation:navigation-ui-ktx:2.4.2'
//    implementation 'androidx.navigation:navigation-fragment-ktx:2.4.2'
//    // - Prefs
//    implementation 'androidx.preference:preference-ktx:1.2.0'
//
//    // UI/Layout
//    implementation 'androidx.appcompat:appcompat:1.4.1'
//    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
//    // - Themes
//    implementation 'com.google.android.material:material:1.6.0'
//    // - Layout
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
//    // - Recycler
//    implementation 'androidx.recyclerview:recyclerview:1.2.1'
//    implementation 'androidx.recyclerview:recyclerview-selection:1.1.0'
//
//    // Google Services
//    implementation 'com.google.android.gms:play-services-location:19.0.1'
//
//    // Logging
//    implementation 'com.jakewharton.timber:timber:5.0.1'
//
//    debugImplementation "androidx.fragment:fragment-testing:$fragment_version"
//    // region Unit Tests
//
//    testImplementation 'junit:junit:4.13.2'
//    testImplementation 'org.hamcrest:hamcrest:2.2'
//    testImplementation 'com.google.truth:truth:1.1.3'
//    testImplementation "androidx.test:core:$testing_version" // AndroidX
//    testImplementation "androidx.test:runner:$testing_version"
//    testImplementation "androidx.test:rules:$testing_version"
//    testImplementation 'androidx.test.ext:junit:1.1.3'
//    testImplementation 'org.mockito:mockito-core:4.5.1'
//    testImplementation 'org.robolectric:robolectric:4.8.1'
//
//    // endregion
//
//    // region Instrumented Tests
//
//    androidTestImplementation 'junit:junit:4.13.2'
//    androidTestImplementation 'com.google.truth:truth:1.1.3'
//    androidTestImplementation "androidx.test:core:$testing_version" // AndroidX
//    androidTestImplementation "androidx.test:runner:$testing_version"
//    androidTestImplementation "androidx.test:rules:$testing_version"
//    androidTestImplementation "androidx.test:core-ktx:$testing_version"
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.ext:junit-ktx:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
//    androidTestImplementation 'androidx.test.espresso:espresso-intents:3.4.0'
//    androidTestImplementation 'androidx.arch.core:core-testing:2.1.0'
//    androidTestImplementation 'org.mockito:mockito-core:4.5.1'
//    androidTestImplementation 'org.mockito:mockito-android:4.5.1'
//
//    // endregion
}
