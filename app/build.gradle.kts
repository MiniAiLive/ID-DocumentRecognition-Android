plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.miniai.idreader"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.miniai.idreader"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

//    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0")
//    implementation 'androidx.navigation:navigation-ui-ktx:2.4.0'
//    implementation 'androidx.viewpager:viewpager:1.0.0'
    implementation("io.github.medyo:android-about-page:2.0.0")

//    implementation(project(":IdSdk"))
    implementation(files("./libs/libminiai-IDSDK.aar"))

    implementation(libs.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
}