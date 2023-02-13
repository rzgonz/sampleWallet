
android {

    compileSdkVersion AppConfig.CompileSdkVersion

    defaultConfig {
        minSdkVersion AppConfig.MinSdkVersion
        targetSdkVersion AppConfig.TargetSdkVersion

        testInstrumentationRunner AppConfig.TestInstrumentationRunner
        consumerProguardFiles AppConfig.ConsumerProguardFiles
    }


    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }


}


dependencies {
    implementation Libraries.fragment
    implementation Libraries.coreKtx

    //koin
    implementation Libraries.koin

    //network
    implementation Libraries.retrofit
    implementation Libraries.retrofitGson
    implementation Libraries.gson
    implementation Libraries.okHttp3
    implementation Libraries.okHttp3Log
    testImplementation TestLibraries.junit
    testImplementation TestLibraries.kotlinCoroutinesTest
    testImplementation TestLibraries.mockito
    testImplementation TestLibraries.mockitoKotlin
    testImplementation TestLibraries.mockk
}