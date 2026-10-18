plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.mycashier"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mycashier"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")

    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation ("androidx.recyclerview:recyclerview:1.4.0")

    implementation("com.google.android.gms:play-services-auth:21.2.0")

    implementation("com.google.mlkit:text-recognition:16.0.0")

    //room
    // Room (runtime + optional ktx if you want coroutines/KTX helpers)
    implementation ("androidx.room:room-runtime:2.8.4")
    implementation ("androidx.room:room-ktx:2.8.4")   // optional, safe to add

    // Annotation processor
    annotationProcessor ("androidx.room:room-compiler:2.8.4")




    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation ("net.objecthunter:exp4j:0.4.8")

}