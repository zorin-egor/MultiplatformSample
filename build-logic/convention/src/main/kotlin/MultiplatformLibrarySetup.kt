import com.android.build.api.dsl.LibraryExtension
import com.sample.app.ext.configureKotlinAndroid
import com.sample.app.ext.configureKotlinMultiplatform
import com.sample.app.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformLibrarySetup : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidLibrary").get().get().pluginId)
                apply(libs.findPlugin("kotlinSerialization").get().get().pluginId)
            }
            extensions.configure<LibraryExtension>(::configureKotlinAndroid)
            extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
        }
    }
}