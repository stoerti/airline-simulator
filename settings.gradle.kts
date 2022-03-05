enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

rootProject.name = "airline-simulator"

include(":ui")
include(":booking")
include(":booking:booking-api")
include(":booking:booking-manager")
include(":booking:booking-service")
include(":customer-api")
include(":customer")
include(":customer:customer-api")
include(":customer:customer-manager")
include(":fleet")
include(":fleet:fleet-api")
include(":fleet:fleet-manager")
include(":flight")
include(":flight:flight-api")
include(":flight:flightmanager")
include(":flight:flighttracker-service")
include(":runner")
include(":agents")
