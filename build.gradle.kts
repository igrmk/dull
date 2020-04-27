plugins {
    signing
    `maven-publish`
    kotlin("jvm") version "1.3.71"
}

group = "com.github.igrmk"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    val ossrhUsername: String by project
    val ossrhPassword: String by project

    publications {
        create<MavenPublication>("default") {
            pom {
                name.set("dull")
                description.set("Dull & undeveloped logging library")
                url.set("http://github.com/igrmk/dull")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("igrmk")
                        name.set("igrmk")
                        email.set("igrmk@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/igrmk/dull")
                    connection.set("scm:git:https://github.com/igrmk/dull.git")
                    developerConnection.set("scm:git:https://github.com/igrmk.git")
                }
            }
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

signing {
    sign(publishing.publications["default"])
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
