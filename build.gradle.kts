import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("java-library")
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.8.21"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.serialization") version "1.8.21"
}

group = "com.github.buckcri.xclacks"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

// Spring Boot is only used for testing. Building a boot jar fails because of missing boot application, so we need to disable that task:
tasks.bootJar {
	enabled = false
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.21")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.test {
	useJUnitPlatform()
}
