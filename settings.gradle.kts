/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
//    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "Accountability"

include(":app")

include("core:common")
include("core:entities")
include("core:data")
include("core:interactors")
include("core:view-models")
include("core:local")
include("core:remote")
include("core:navigation")
include("core:ui")

include("feature:recording")
include("feature:calling")
include("feature:about")
