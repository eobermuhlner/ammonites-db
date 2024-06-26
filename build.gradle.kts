import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("nu.studer.jooq") version "7.0"
    id("org.flywaydb.flyway") version "9.0.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val h2Version = "2.2.224"
val jooqVersion = "3.19.1"
val jdbcUrl = "jdbc:h2:file:~/ammonites-db;DB_CLOSE_DELAY=-1"
val jdbcUser = "sa"
val jdbcPassword = ""

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework:spring-jdbc")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.jooq:jooq:$jooqVersion")
    implementation("com.h2database:h2:$h2Version")
    jooqGenerator("com.h2database:h2:$h2Version")
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    runtimeOnly("org.slf4j:slf4j-simple:1.7.32")
    testImplementation(kotlin("test"))
}

flyway {
    url = jdbcUrl
    user = jdbcUser
    password = jdbcPassword
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

jooq {
    version.set(jooqVersion)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.h2.Driver"
                    url = jdbcUrl
                    user = jdbcUser
                    password = jdbcPassword
                }
                generator.apply {
                    database.apply {
                        name = "org.jooq.meta.h2.H2Database"
                        includes = ".*"
                        inputSchema = "PUBLIC"
                    }
                    target.apply {
                        packageName = "ch.obermuhlner.ammonites.jooq"
                        directory = "src/main/java"
                    }
                    generate.apply {
                        isPojos = true
                    }
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}