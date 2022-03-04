plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.jib)
}

dependencies {
    implementation(project(":flight-api"))
    implementation(project(":fleet-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)

    implementation(libs.postgresql)
}

description = "flighttracker-service"

jib {
    to {
        image = "airsim/flighttracker-service"
    }
}
