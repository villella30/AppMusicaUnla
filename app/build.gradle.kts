plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt") // Aplicas el plugin KAPT
}

android {
    namespace = "com.example.appmusicagrupon"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appmusicagrupon"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // --- DEPENDENCIAS PRINCIPALES ---
    // Se han limpiado las dependencias duplicadas y conflictivas.
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation("com.squareup.picasso:picasso:2.8")

    // Se usan versiones específicas que son compatibles con compileSdk 34
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.8.2")

    // --- DEPENDENCIAS DE ROOM (CORREGIDAS) ---
    val room_version = "2.4.2" // Define la versión para mantener la consistencia
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Para Coroutines y Flow
    kapt("androidx.room:room-compiler:$room_version") // El compilador SÓLO con 'kapt'

    // --- DEPENDENCIAS DE TEST ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
