import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidFlavorsConventionalPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
            }
            extensions.configure<ApplicationExtension> {
                val appDimension = "app"
                flavorDimensions.add(appDimension)

                productFlavors {
                    create("appA") {
                        dimension = appDimension
                        applicationId = "com.app.a"
                    }
                    create("appB") {
                        dimension = appDimension
                        applicationId = "com.app.b"
                    }
                }
            }
        }
    }
}