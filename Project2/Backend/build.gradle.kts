plugins {
    id("java")
}

group = "io.github.octcarp.sustech.cs307"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // sparkjava(!=apache spark) framework
    implementation("com.sparkjava:spark-core:2.9.4")

    val slf4jVersion = "1.7.25"
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    // json parser
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.alibaba:fastjson:1.2.83")

    // Database driver
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.opengauss:opengauss-jdbc:5.1.0")

    // Junit5
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}