
dependencies {
    api(project(":customer-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)
}

description = "customer-manager"
