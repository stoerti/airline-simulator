plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("java")
}

dependencies {
    api(project(":flight:flight-api"))
    api(project(":fleet:fleet-api"))
    api(project(":customer:customer-api"))
    api(project(":booking:booking-api"))

    implementation(libs.bundles.spring.boot.starter)
    implementation(libs.bundles.swagger)
    implementation(libs.axon.spring.starter)
    implementation(libs.spring.boot.quartz)

    implementation(libs.postgresql)
}

description = "agents"
