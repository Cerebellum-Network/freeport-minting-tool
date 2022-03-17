plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.allopen") version "1.6.10"
    id("io.quarkus") version "2.7.5.Final"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Quarkus
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:2.7.5.Final"))

    // Web
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-rest-client-reactive-jackson")

    // CSV
    implementation("io.quarkiverse.univocityparsers:quarkus-univocity-parsers:0.0.1")

    // Crypto
    implementation("com.codahale:xsalsa20poly1305:0.11.0")
    implementation("com.github.komputing.khex:core:1.1.2")
    implementation("com.github.komputing.khex:extensions:1.1.2")
    implementation("com.github.komputing.kethereum:model:0.85.3")
    implementation("com.github.komputing.kethereum:crypto:0.85.3")
    implementation("com.github.komputing.kethereum:crypto_api:0.85.3")
    implementation("com.github.komputing.kethereum:crypto_impl_bouncycastle:0.85.3")
    implementation("com.github.komputing.kethereum:extensions_kotlin:0.85.3")

    // Web3j
    implementation("org.web3j:core:5.0.0") {
        isForce = true
    }
    implementation("com.squareup.okhttp3:okhttp:4.9.3") {
        isForce = true
    }
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") {
        isForce = true
    }
    implementation("com.squareup.okio:okio:3.0.0") {
        isForce = true
    }
    implementation("io.reactivex.rxjava2:rxjava")

    // Kotlin
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Build
    implementation("io.quarkus:quarkus-arc")
}

group = "nft.freeport"
version = "0.1.0"

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
