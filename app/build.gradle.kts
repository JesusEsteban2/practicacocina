plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.practicacocina"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.practicacocina"
        minSdk = 27
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

    //Activar viewBinding para la aplicación.
    buildFeatures {
        viewBinding=true
    }
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }


}

dependencies {

    // Añadir implementación de Retrofit.
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // Implementation gson 2.9.0
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // Implementation Picasso 2.8
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation("androidx.activity:activity:1.8.0")

    val sqlite_version = "2.4.0"
    // Dependencias SQLite Java
    //implementation("androidx.sqlite:sqlite:$sqlite_version")
    // Dependencias SQLite Kotlin
    implementation("androidx.sqlite:sqlite-ktx:$sqlite_version")
    // Implementation of the AndroidX SQLite interfaces via the Android framework APIs.
    // implementation("androidx.sqlite:sqlite-framework:$sqlite_version")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}