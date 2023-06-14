import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("java-library")
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.8.22"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.serialization") version "1.8.22"
	`maven-publish`
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
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.22")
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

publishing {
	publications {
		create<MavenPublication>("maven") {
			pom {
				name.set("XClacks")
				description.set("Servlet 3.0 filter adding http header 'X-Clacks-Overhead'")
				url.set("https://github.com/buckcri/xclacks")
				licenses {
					license {
						name.set("The MIT License (MIT)")
						url.set("https://opensource.org/license/mit/")
					}
				}
				developers {
					developer {
						id.set("buckcri")
						name.set("Christian Buck")
						email.set("buckcri@protonmail.com")
					}
				}
				scm {
					connection.set("https://github.com/buckcri/xclacks.git")
					developerConnection.set("https://github.com/buckcri/xclacks.git")
					url.set("https://github.com/buckcri/xclacks")
				}
				repositories {
					maven {
						name = "OSSRH"
						url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
						credentials {
							username = System.getenv("MAVEN_USERNAME")
							password = System.getenv("MAVEN_PASSWORD")
						}
					}
				}
			}
		}
	}
}
