# Getting Started

## Requirements

1. [Android SDK Manager](https://developer.android.com/studio/index.html#downloads)
2. [Android Studio](https://developer.android.com/studio/index.html) or [IntelliJ Ultimate](https://www.jetbrains.com/idea/)

## Setup

1. Download and install Android SDK 25 and Build Tools 25.0.2 with the SDK Manager. Install any other SDK or images to run the emulator as you like. 
2. Rename the file `local.properties_example` to `local.properties` and change to path to the home of your android sdk. ANDROID_HOME might work if set up properly.

## Build & Test

The project uses gradle for dependency management and running scripts. Wrapper are located in the root, use `./gradlew` on unix and `gradle.bat` on Windows
to issue following commands:`

* `./gradlew build` builds the apks to `app/build/output/apk/app-debug.apk` and `app/build/output/apk/app-release-unsigned.apk`.
* `./gradlew test` runs all unit tests
