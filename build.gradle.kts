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
	signing
}

group = "io.github.buckcri.xclacks"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

// Spring Boot is only used for testing. Building a boot jar fails because of missing boot application, so we need to disable that task:
tasks.bootJar {
	enabled = false
}

tasks.named<Jar>("jar") {
	// Unset 'plain' classifier from Spring Boot
	archiveClassifier.set("")
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

java {
	withSourcesJar()
	withJavadocJar()
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			from(components["java"])
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
					connection.set("scm:git:git://github.com/buckcri/xclacks.git")
					developerConnection.set("scm:git:ssh://github.com/buckcri/xclacks.git")
					url.set("https://github.com/buckcri/xclacks/tree/master")
				}
				repositories {
					maven {
						val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
						val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

						val  ossrhUsername: String by project
						val  ossrhPassword: String by project

						name = "OSSRH"
						url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
						credentials {
							username = ossrhUsername
							password = ossrhPassword
						}
					}
				}
			}
		}
	}
}

signing {
	sign(publishing.publications["maven"])
}

tasks.javadoc {
	if (JavaVersion.current().isJava9Compatible) {
		(options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
	}
}