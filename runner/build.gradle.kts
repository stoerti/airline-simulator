
plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.jib)
}

dependencies {
    implementation(project(":fleet-manager"))
    implementation(project(":flightmanager"))
    implementation(project(":customer-manager"))
    implementation(project(":booking-manager"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)
    implementation(libs.postgresql)
}

description = "runner"

jib {
    to {
        image = "airsim/runner"
    }
}
