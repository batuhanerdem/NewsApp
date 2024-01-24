plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}


android {
    namespace = "com.example.newsapp"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.example.newsapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        android.buildFeatures.buildConfig = true


        buildConfigField("String", "API_KEY", project.findProperty("MY_KEY") as String)

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    val nav_version = "2.7.0"

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-compiler:2.48.1")

    // Lifecycle
    val lifecycle_version = "2.6.2"

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48.1")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.48.1")

    testImplementation("com.google.dagger:hilt-android-testing:2.48.1")
    kaptTest("com.google.dagger:hilt-compiler:2.48.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //coordinator layout
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    //constraint layout
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")

    implementation("jp.wasabeef:glide-transformations:4.3.0")

    // Sneaker
    val sneaker_version = "2.0.0"
    implementation("com.irozon.sneaker:sneaker:$sneaker_version")

    // Lottie
    val lottie_version = "6.2.0"
    implementation("com.airbnb.android:lottie:$lottie_version")

    // Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //OKhttp profiler
    implementation("com.localebro:okhttpprofiler:1.0.8")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Kotlin + coroutines
    val work_version = "2.9.0"
    implementation("androidx.work:work-runtime-ktx:$work_version")

}

kapt {
    correctErrorTypes = true
}