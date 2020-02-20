plugins {
    kotlin("jvm") version "1.3.61"
    application
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("org.hildan.hashcode:hashcode-utils-kt:0.3.0")
}

application {
    mainClassName = "org.hildan.hashcode2020.MainKt"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    val srcZip = register<Zip>("srcZip") {
        archiveClassifier.set("src")
        archiveBaseName.set("$rootDir/outputs/")
        from(sourceSets.main.get().allSource)
    }

    val generateOutputs = register<JavaExec>("generateOutputs") {
        args("inputs/num1.in")
        description = "Hashcode output generation"
        classpath = sourceSets["main"].runtimeClasspath
        main = application.mainClassName
    }

    register("submit") {
        dependsOn.add(srcZip.get())
        dependsOn.add(generateOutputs.get())
    }
}
