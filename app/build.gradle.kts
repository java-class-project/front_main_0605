plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.teamplay_p"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.teamplay_p"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.gsonConverter)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.preference)
    implementation(libs.navigation.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.github.bumptech.glide:glide:4.10.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.10.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.google.android.material:material")
    /*
    implementation("com.android.support:recyclerview-v7:28.0.0")
    implementation("com.github.bumptech.glide:glide:4.10.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.10.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.android.support:recyclerview-v7:28.0.0")
    implementation ("com.android.support:design:28.0.0")
    */


}