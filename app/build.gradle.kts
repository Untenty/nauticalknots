plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.jetbrains.kotlin.android) apply true
    kotlin("plugin.serialization") version "1.9.22" apply true
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply true
    alias(libs.plugins.compose.compiler) apply true
}

android {
    namespace = "com.untenty.nauticalknots"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.untenty.nauticalknots"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resourceConfigurations += setOf("en", "ru")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }

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
        sourceCompatibility = JavaVersion.VERSION_15
        targetCompatibility = JavaVersion.VERSION_15
    }
    kotlinOptions {
        jvmTarget = "15"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.coil.compose)

    implementation(libs.androidx.navigation.compose)
//    implementation(androidx.appcompat:appcompat:{latest_version})

    //implementation(libs.accompanist.reorderable)
    //implementation(libs.reorderable)
    //implementation("org.burnoutcrew.composereorderable:reorderable:<latest_version>")
//    implementation("org.burnoutcrew.composereorderable:reorderable:<latest_version>")
//
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.runtime)
//    implementation(libs.accompanist.placeholder)

    implementation(libs.gson)

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

}
