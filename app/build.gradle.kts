plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("kotlin-kapt")
    alias(libs.plugins.google.services)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"

}

android {
    namespace = "vcmsa.projects.petcareapp"
    compileSdk = 35
    viewBinding.isEnabled=true
    dataBinding.isEnabled = true

    defaultConfig {
        applicationId = "vcmsa.projects.petcareapp"
        minSdk = 31
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.play.services.auth)
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    //firebase
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.firestore)
    //google + facebook
    implementation(libs.play.services.identity)
    implementation("com.facebook.android:facebook-login:18.1.3")
    //import for retrofit and gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //serilization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}