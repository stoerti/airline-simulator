plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.jib)
}

description = "booking-service"

dependencies {
    implementation(project(":flight:flight-api"))
    implementation(project(":fleet:fleet-api"))
    implementation(project(":booking:booking-api"))
    implementation(project(":customer:customer-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)

    implementation(libs.postgresql)
}

jib {
    to {
        image = "airsim/booking-service"
    }
}
