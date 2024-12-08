plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
