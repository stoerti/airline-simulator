import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    alias(libs.plugins.springBoot) apply false
    alias(libs.plugins.jib) apply false
    alias(libs.plugins.lombok) apply false
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    // is java project
    if (this.projectDir.walkTopDown()
            .filter { it.isDirectory }
            .any { it.path.contains("src" + java.nio.file.FileSystems.getDefault().separator + "main" + java.nio.file.FileSystems.getDefault().separator + "java") }) {

        apply {
            plugin("java")
            plugin("java-library")
            plugin("io.spring.dependency-management")
            plugin("io.freefair.lombok")
        }

        the<DependencyManagementExtension>().apply {
            imports {
                mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            }
        }
    }

    tasks {
/*
        withType<JavaCompile> {
            sourceCompatibility = Versions.java
            targetCompatibility = Versions.java
        }
        withType<GenerateLombokConfig> {
            enabled = false
        }
*/

        withType<Test> {
            useJUnitPlatform() {
                testLogging {
                    events("passed", "skipped", "failed")
                }
            }
        }
    }

}


version = "0.0.0-SNAPSHOT"
