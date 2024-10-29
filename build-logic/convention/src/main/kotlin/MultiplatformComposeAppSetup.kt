import com.sample.app.ext.configureComposeMultiplatform
import com.sample.app.ext.getPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformComposeAppSetup : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(getPlugin("jetbrainsCompose").pluginId)
                apply(getPlugin("composeCompiler").pluginId)
            }

            val composeExtension = extensions.getByType<ComposeExtension>()
            extensions.configure<KotlinMultiplatformExtension>{
                configureComposeMultiplatform(this, composeExtension)
            }
        }
    }
}