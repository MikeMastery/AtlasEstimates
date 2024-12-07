plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.atlasestimates"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.atlasestimates"
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation(libs.room.common)
    implementation(libs.glide)
    implementation("androidx.room:room-runtime:2.5.2") // Asegúrate de usar la última versión disponible
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation("com.itextpdf:itext7-core:7.1.15")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")
    implementation(libs.firebase.crashlytics.buildtools)
    annotationProcessor(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.core:core:1.6.0")
}
