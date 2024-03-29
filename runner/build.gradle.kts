
plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.jib)
}

dependencies {
    implementation(project(":fleet:fleet-manager"))
    implementation(project(":flight:flightmanager"))
    implementation(project(":customer:customer-manager"))
    implementation(project(":booking:booking-manager"))

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
