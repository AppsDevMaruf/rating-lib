pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/AppsDevMaruf/rating-lib")
            credentials {
                username =
                    providers.gradleProperty("gpr.user").orElse(System.getenv("GITHUB_USERNAME")).toString()
                password =
                    providers.gradleProperty("gpr.key").orElse(System.getenv("GITHUB_TOKEN")).toString()
            }
        }
    }
}

rootProject.name = "rating"
include(":rating")