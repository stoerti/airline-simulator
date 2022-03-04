plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("java")
}

dependencies {
    api(project(":flight-api"))
    api(project(":fleet-api"))
    api(project(":customer-api"))
    api(project(":booking-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)
    implementation(libs.spring.boot.quartz)

    implementation(libs.postgresql)
}

description = "agents"
