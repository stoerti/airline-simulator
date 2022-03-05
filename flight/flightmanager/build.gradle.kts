
dependencies {
    api(project(":flight:flight-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)
}

description = "flightmanager"
