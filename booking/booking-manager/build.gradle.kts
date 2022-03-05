
dependencies {
    api(project(":booking:booking-api"))
    api(project(":customer:customer-api"))
    api(project(":flight:flight-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)

    implementation(libs.postgresql)
}

description = "booking-manager"
