
dependencies {
    api(project(":fleet-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)
}

description = "fleet-manager"
