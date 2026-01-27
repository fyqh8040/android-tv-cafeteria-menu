pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.application") {
                useVersion("8.0.0")
            }
            if (requested.id.id == "org.jetbrains.kotlin.android") {
                useVersion("1.9.23")
            }
            if (requested.id.id == "org.jetbrains.kotlin.kapt") {
                useVersion("1.9.23")
            }
            if (requested.id.id == "com.google.dagger.hilt.android") {
                useVersion("2.44")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CafeteriaMenuTV"