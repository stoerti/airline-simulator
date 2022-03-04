
dependencies {
    api(project(":booking-api"))
    api(project(":customer-api"))
    api(project(":flight-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)

    implementation(libs.postgresql)
}

description = "booking-manager"
