import com.aktiia.convention.addUiLayerDependencies
import com.aktiia.convention.configureApiKeys
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidFeatureUIConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("aktiia.android.library.compose")
            }
            configureApiKeys()
            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}