# CS712 Android App

This repository contains the Android application developed for **CS712 – Mobile Software Engineering**.
The project is built using **Kotlin**, follows modern Android development practices, and is designed to be extended over the course of the semester.

---

## 🛠️ Development Environment

### IDE

* **Android Studio Otter 3 Feature Drop (2025.2.3)**

### Language & Build

* **Language:** Kotlin
* **Gradle DSL:** Kotlin DSL
* **Build Tool:** Gradle 9.3.1
* **Kotlin Version:** 2.2.10
* **Java Runtime:** OpenJDK 21 (Zulu / JetBrains)

### Android Configuration

* **Minimum SDK:** API 24 (Android 7.0 – Nougat)
* **Target SDK:** Default (as configured by Android Studio)
* **Architecture:** ARM64 (Apple Silicon compatible)

### Operating System

* **macOS:** 15.7.3 (Apple Silicon)

---

## ▶️ Building & Running the App

### Prerequisites

* Android Studio installed
* Android SDK configured via Android Studio
* At least one of the following:
  * Android Emulator (AVD)
  * Physical Android device with USB debugging enabled

### Using Android Studio (GUI)

1. Clone the repository:
   ```bash
   git clone https://github.com/winston-spencer/CS712AndroidApp.git
   ```
2. Open **Android Studio**
3. Select **Open**
4. Navigate to the cloned `CS712AndroidApp` directory
5. Allow Gradle to sync completely
6. Select a device/emulator
7. Click **Run ▶️** or **Debug ⚙️**

### Using Command Line

#### **Clean the Build**
Remove all build artifacts and intermediate files:

```bash
./gradlew clean
```

#### **Build the Project**
Build the debug APK:

```bash
./gradlew build
```

Build a release APK (unsigned):

```bash
./gradlew assembleRelease
```

Build debug APK only (faster):

```bash
./gradlew assembleDebug
```

#### **Run the Application**

**Prerequisites:** Ensure an Android emulator is running or a physical device is connected via USB with USB debugging enabled.

**Install and run on connected device/emulator:**

```bash
./gradlew installDebug
```

Then launch the app:

```bash
adb shell am start -n edu.ndsu.csci/.MainActivity
```

**Or, build, install, and run in one command:**

```bash
./gradlew installDebug && adb shell am start -n edu.ndsu.csci/.MainActivity
```

#### **Debug the Application**

**Build debug APK with debugging symbols:**

```bash
./gradlew assembleDebug
```

**Install debug APK:**

```bash
./gradlew installDebug
```

**Start debugger in Android Studio:**
- In Android Studio, go to **Run > Debug 'app'** or press `Shift + F9`
- Or use command line:

```bash
./gradlew installDebug
adb shell am start -D -n edu.ndsu.csci/.MainActivity
```

Then attach debugger in Android Studio (**Run > Attach Debugger to Android Process**).

#### **Deploy to Device/Emulator**

**Quick Deploy (Build, Install, and Run):**

```bash
./gradlew installDebug && adb shell am start -n edu.ndsu.csci/.MainActivity
```

**Deploy Release Build (unsigned):**

```bash
./gradlew assembleRelease
adb install -r app/build/outputs/apk/release/app-release-unsigned.apk
adb shell am start -n edu.ndsu.csci/.MainActivity
```

**Check Device/Emulator Connection:**

```bash
adb devices
```

**View Device Logs:**

```bash
adb logcat
```

**View Logs for App Only:**

```bash
adb logcat | grep edu.ndsu.csci
```

### Complete Workflow Examples

**Full Development Workflow:**

```bash
# Navigate to project directory
cd /Users/winstonspencer/git/CS712AndroidApp

# Clean previous builds
./gradlew clean

# Build the project
./gradlew build

# Install on device/emulator
./gradlew installDebug

# Run the application
adb shell am start -n edu.ndsu.csci/.MainActivity

# View logs (optional)
adb logcat | grep edu.ndsu.csci
```

**Quick Rebuild and Run:**

```bash
./gradlew clean assembleDebug && ./gradlew installDebug && adb shell am start -n edu.ndsu.csci/.MainActivity
```

**Testing Build:**

```bash
# Run unit tests
./gradlew test

# Run instrumented tests on device/emulator
./gradlew connectedAndroidTest
```

---

## 🤖 UI Automator Tests

### Overview

`LaunchAndNavigateTest` is a UI Automator instrumented test that verifies end-to-end navigation:

1. Presses the Home button to start from a clean state
2. Launches the app via an explicit Intent
3. Clicks the **"Start Activity Explicitly"** button on `MainActivity`
4. Verifies that **"Device Fragmentation"** is visible on `SecondActivity`

### Prerequisites

* A connected Android device **or** a running emulator (AVD)
* USB debugging enabled on the device (if physical)
* Verify the device is detected:

```bash
adb devices
```

### Build the Test APK (no device required)

```bash
./gradlew :app:assembleAndroidTest
```

### Run the UI Automator Test

```bash
./gradlew connectedAndroidTest
```

To run only `LaunchAndNavigateTest`:

```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=edu.ndsu.csci.LaunchAndNavigateTest
```

### View Test Results

After the run, HTML results are at:

```
app/build/reports/androidTests/connected/index.html
```

### Gradle Tasks Reference

| Task | Purpose |
|------|---------|
| `./gradlew clean` | Clean all build outputs |
| `./gradlew build` | Build debug and release APKs |
| `./gradlew assembleDebug` | Build debug APK only |
| `./gradlew assembleRelease` | Build release APK (unsigned) |
| `./gradlew assembleAndroidTest` | Build instrumented test APK only |
| `./gradlew installDebug` | Install debug APK on device |
| `./gradlew installRelease` | Install release APK on device |
| `./gradlew test` | Run unit tests |
| `./gradlew connectedAndroidTest` | Run all instrumented tests on device/emulator |
| `./gradlew lint` | Run lint analysis |

### ADB (Android Debug Bridge) Commands Reference

| Command | Purpose |
|---------|---------|
| `adb devices` | List connected devices/emulators |
| `adb install [APK]` | Install APK on device |
| `adb install -r [APK]` | Reinstall APK (replace existing) |
| `adb shell am start -n [PACKAGE]/[ACTIVITY]` | Launch activity |
| `adb shell am start -D -n [PACKAGE]/[ACTIVITY]` | Launch activity with debugger wait |
| `adb logcat` | View system logs |
| `adb push [LOCAL] [DEVICE]` | Push file to device |
| `adb pull [DEVICE] [LOCAL]` | Pull file from device |
| `adb shell pm uninstall [PACKAGE]` | Uninstall app from device |

---

## 🧪 Tested On

* **Emulator / Device:** Pixel 5 (AVD)
* **Android OS Version:** Android 13 / Android 14
* **ABI:** arm64-v8a

---

## 🧾 Git & Submission Details

* **Branch:** `assignment-6`
* **Commit Message Format:** `assignment-6`
* Repository includes:

  * Full source code
  * This README
  * Gradle build files

---

## ✅ Status

* App successfully created and executed: **Yes**

---

## 📎 Notes

This project will be expanded throughout the semester.
Future assignments may introduce additional activities, UI components, services, or architectural patterns.

---
