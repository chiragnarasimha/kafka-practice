plugins {
    id("java")
}

group = "kafka.practice"

repositories {
    mavenCentral()
}

dependencies {
// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation("org.apache.kafka:kafka-clients:3.1.0");
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:1.7.36")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:1.7.36")
//    testImplementation(platform("org.junit:junit-bom:5.9.1"));
//    testImplementation("org.junit.jupiter:junit-jupiter");
}

tasks.test {
    useJUnitPlatform()
}