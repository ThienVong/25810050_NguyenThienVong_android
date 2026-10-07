pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplication"
include(":app")
include(":BUOI_4")
include(":BUOI_3")
include(":BUOI_06:app")
include(":BUOI_05:DiceRoller:app")
include(":BUOI_05:HappyBirthday:app")
include(":BUOI_07:basic-android-kotlin-compose-training-cupcake:app")
