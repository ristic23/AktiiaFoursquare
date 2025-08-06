import com.aktiia.convention.configureAndroidCompose
import com.aktiia.convention.configureApiKeys
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("aktiia.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extensions = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extensions)

            configureApiKeys()
        }
    }

}