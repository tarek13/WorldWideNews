plugins {
    id(GradlePlugins.javaLib)
    id(GradlePlugins.kotlinJVM)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {

    implementation(DI.javaxInject)


    //reactive
    implementation(ReactiveRx.rxJava)
    implementation(ReactiveRx.rxAndroid)

    // Test
    testImplementation(AndroidTestLibs.junit)
    testImplementation(AndroidTestLibs.mockitoCore)


}