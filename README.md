# CS712 Android App

This repository contains the Android application developed for **CS712 – Mobile Software Engineering**.
The project is built using **Kotlin**, follows modern Android development practices, and is designed to be extended over the course of the semester.

---

## 📌 Assignment

**Assignment 2 – Create and Run an Android App**

This assignment focuses on:

* Android app creation
* Activity lifecycle management
* Explicit and implicit intents

---

## 📱 App Requirements (Assignment 2)

### Main Activity

* Displays:

    * Full name
    * Student ID
* Includes two buttons:

    * **Start Activity Explicitly**
      Launches the second activity using an explicit intent
    * **Start Activity Implicitly**
      Launches the second activity using an implicit intent

### Second Activity

* Displays a list of **at least five mobile software engineering challenges**
* Includes a button labeled **Main Activity** that returns to the main activity

---

## 🔁 Intent Implementation Notes

* **Explicit Intent:**
  Uses a direct class reference to launch `SecondActivity`.

* **Implicit Intent:**
  Uses a custom intent action resolved via an `intent-filter` declared in `AndroidManifest.xml`.

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

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/winston-spencer/CS712AndroidApp.git
   ```
2. Open **Android Studio**
3. Select **Open**
4. Navigate to the cloned `CS712AndroidApp` directory
5. Allow Gradle to sync completely
6. Select a device/emulator
7. Click **Run ▶️**

---

## 🧪 Tested On

* **Emulator / Device:** Pixel 5 (AVD)
* **Android OS Version:** Android 13 / Android 14
* **ABI:** arm64-v8a

---

## 🧾 Git & Submission Details

* **Branch:** `assignment-2`
* **Commit Message Format:** `assignment-2`
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
