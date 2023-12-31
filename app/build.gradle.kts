plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")


}


android {
    namespace = "edu.ktu.lab1_rajesh"
    compileSdk = 34
    buildFeatures{
        viewBinding = true
        dataBinding=true

    }

    defaultConfig {
        applicationId = "edu.ktu.lab1_rajesh"
        minSdk = 24
        targetSdk = 33
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("androidx.compose.ui:ui-graphics-android:1.5.4")
    implementation("com.google.code.gson:gson:2.8.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("mysql:mysql-connector-java:8.0.27")

    //java mysql connector

    implementation("mysql:mysql-connector-java:8.0.27")
    implementation ("org.jetbrains.exposed:exposed-core:0.35.1")
    implementation ("org.jetbrains.exposed:exposed-dao:0.35.1")
    implementation ("org.jetbrains.exposed:exposed-jdbc:0.35.1")

}