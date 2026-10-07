plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.hcmute.buoi_06"
    compileSdk = 37

    defaultConfig {
        applicationId = "edu.hcmute.buoi_06"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}
