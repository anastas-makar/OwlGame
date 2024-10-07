import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("maven-publish")
    id("com.google.devtools.ksp")
}

android {
    namespace = "pro.progr.owlgame"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        buildConfigField("String", "API_BASE_URL", "\"${localProperties["API_BASE_URL"]}\"")
        buildConfigField("String", "API_KEY", "\"${localProperties["API_KEY"]}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        version = "0.0.1-alpha"
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
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    implementation("androidx.compose.ui:ui:1.7.2")
    implementation("androidx.compose.ui:ui-tooling:1.7.2")
    implementation("androidx.compose.material:material:1.7.2")
    implementation("androidx.compose.material:material-icons-extended:1.7.2")
    implementation("androidx.compose.foundation:foundation:1.7.2")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.navigation:navigation-compose:2.8.1")

    implementation("androidx.work:work-runtime-ktx:2.9.1")
    implementation("com.google.dagger:dagger:2.48")
    ksp("com.google.dagger:dagger-compiler:2.48")

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifact("$buildDir/outputs/aar/app-release.aar")

            groupId = "pro.progr"
            artifactId = "owlgame"
            version = "0.0.1-alpha"
        }
    }
    repositories {
        maven {
            url = uri("file://${buildDir}/repo")
        }
    }
}