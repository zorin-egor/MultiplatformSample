import com.android.build.api.dsl.ApplicationExtension
import com.sample.app.ext.configureDependencyMultiplatform
import com.sample.app.ext.configureKotlinAndroid
import com.sample.app.ext.getPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformApplicationSetup : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(getPlugin("kotlinMultiplatform").pluginId)
                apply(getPlugin("androidApplication").pluginId)
                apply(getPlugin("kotlinSerialization").pluginId)
                apply("com.multiplatform.setup.app.compose")
            }

            extensions.configure<ApplicationExtension>(::configureKotlinAndroid)
            extensions.configure<KotlinMultiplatformExtension>{
                configureDependencyMultiplatform(this)

                jvmToolchain(17)

                sourceSets.apply {
                    jsMain.dependencies {
                        implementation(devNpm("copy-webpack-plugin", "12.0.2"))
                    }
                }
            }
        }
    }
}