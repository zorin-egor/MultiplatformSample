import com.android.build.api.dsl.ApplicationExtension
import com.sample.app.ext.configureKotlinAndroid
import com.sample.app.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MultiplatformApplicationSetup : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidApplication").get().get().pluginId)
                apply(libs.findPlugin("kotlinSerialization").get().get().pluginId)
            }
            extensions.configure<ApplicationExtension>(::configureKotlinAndroid)
        }
    }
}