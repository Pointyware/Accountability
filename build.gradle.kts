import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.composeMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinJvm).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.hilt).apply(false)

    // apply dokka now
    alias(libs.plugins.dokka)
}

tasks.dokkaHtmlMultiModule {
    moduleName.set("Accountability")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<DokkaTask>().configureEach {

        dokkaSourceSets.configureEach {

            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URL("https://github.com/Pointyware/Accountability/tree/main/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}
