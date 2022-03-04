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
include(":booking-api")
include(":booking-manager")
include(":booking-service")
include(":customer-api")
include(":customer-manager")
include(":fleet-api")
include(":fleet-manager")
include(":flight-api")
include(":flightmanager")
include(":flighttracker-service")
include(":runner")
include(":agents")
