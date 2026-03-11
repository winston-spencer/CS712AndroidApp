# Assignment 4 — AndroidManifest.xml Component Analysis

## Part 1

### Declared App Components

#### Activities — 13 total (2 exported)

| Activity | Exported |
|---|---|
| `TransferEntriesActivity` | No |
| `AboutActivity` | No |
| `ImportEntriesActivity` | No |
| `MainActivity` | **Yes** |
| `ScannerActivity` | No |
| `EditEntryActivity` | No |
| `IntroActivity` | No |
| `AuthActivity` | No |
| `PreferencesActivity` | No |
| `GroupManagerActivity` | No |
| `AssignIconsActivity` | No |
| `LicensesActivity` | No |
| `PanicResponderActivity` | **Yes** |
| `ExitActivity` | No |

> Note: `NotificationService` is declared in a comment and excluded from the count.

---

#### Services — 2 total (2 exported)

| Service | Exported |
|---|---|
| `LaunchAppTileService` | **Yes** |
| `LaunchScannerTileService` | **Yes** |

---

#### Broadcast Receivers — 2 total (0 exported)

| Receiver | Exported |
|---|---|
| `VaultLockReceiver` | No (`android:exported="false"`) |
| `QsTileRefreshReceiver` | No (`android:exported="false"`) |

---

#### Content Providers — 1 total (0 exported)

| Provider | Exported |
|---|---|
| `androidx.core.content.FileProvider` | No (`android:exported="false"`) |

---

#### Summary

| Type | Total | Exported |
|---|---|---|
| Activities | 13 | 2 |
| Services | 2 | 2 |
| Broadcast Receivers | 2 | 0 |
| Content Providers | 1 | 0 |
| **Total** | **18** | **4** |

---

### Permissions

| Permission | Purpose |
|---|---|
| `CAMERA` | Used to scan QR codes when adding new OTP entries. |
| `USE_BIOMETRIC` | Enables biometric authentication (fingerprint/face) to unlock the vault. |
| `VIBRATE` | Provides haptic feedback during interactions. |
| `RECEIVE_BOOT_COMPLETED` | Allows the app to receive a broadcast on device boot to refresh Quick Settings tiles. |
| `POST_NOTIFICATIONS` *(disabled)* | Would allow the app to post notifications; currently commented out pending issue #1047. |

---

### Manifest Elements and Attributes

#### XML Elements

| Element | Purpose |
|---|---|
| `<manifest>` | Root element of the file; defines the package and XML namespaces. |
| `<uses-permission>` | Declares a permission the app requires from the system or the user. |
| `<uses-feature>` | Declares a hardware or software feature the app depends on. |
| `<application>` | Container element for all app components and global app settings. |
| `<activity>` | Declares an Activity component (a single screen in the UI). |
| `<service>` | Declares a Service component (a background operation). |
| `<receiver>` | Declares a Broadcast Receiver component (listens for system/app broadcasts). |
| `<provider>` | Declares a Content Provider component (manages shared app data). |
| `<intent-filter>` | Specifies the types of intents a component can respond to. |
| `<action>` | Defines the action part of an intent filter (e.g., MAIN, VIEW, SEND). |
| `<category>` | Defines the category part of an intent filter (e.g., LAUNCHER, DEFAULT). |
| `<data>` | Specifies the data URI scheme or MIME type an intent filter matches. |
| `<meta-data>` | Supplies additional key-value metadata to a component or the application. |
| `<queries>` | Declares which external packages the app intends to interact with. |
| `<package>` | Specifies a particular package name within a `<queries>` block. |

---

#### XML Attributes

| Attribute | Purpose |
|---|---|
| `xmlns:android` | Declares the Android XML namespace, enabling use of `android:` attributes. |
| `xmlns:tools` | Declares the tools XML namespace, enabling build/lint-time directives. |
| `android:required` | On `<uses-feature>`: indicates whether the feature is mandatory (`true`) or optional (`false`). |
| `android:allowBackup` | Allows the app's data to be included in system backups. |
| `android:fullBackupOnly` | Restricts backup to the full-data backup system (ignores key-value backup). |
| `android:fullBackupContent` | Points to an XML file defining rules for full-data backups (pre-Android 12). |
| `android:dataExtractionRules` | Points to an XML file defining backup/restore rules for Android 12 and above. |
| `android:backupAgent` | Specifies the class that handles key-value backup operations. |
| `android:enableOnBackInvokedCallback` | Enables the predictive back gesture API for the app. |
| `android:icon` | Sets the app's launcher icon. |
| `android:supportsRtl` | Declares that the app supports right-to-left layouts. |
| `android:largeHeap` | Requests a larger Dalvik heap allocation for the app process. |
| `android:theme` | Sets the default visual theme for the app or a specific activity. |
| `tools:targetApi` | Lint directive indicating the minimum API level for which an element applies; suppresses lint warnings. |
| `android:exported` | Controls whether the component can be launched by external apps (`true`) or only by the same app (`false`). |
| `android:configChanges` | Lists configuration changes the activity handles itself, preventing recreation. |
| `android:screenOrientation` | Locks the activity to a specific screen orientation (e.g., `portrait`). |
| `android:launchMode` | Defines how the activity is instantiated (e.g., `singleInstance` creates only one instance). |
| `android:noHistory` | Removes the activity from the back stack as soon as the user leaves it. |
| `android:permission` | Requires a caller to hold the specified permission to interact with the component. |
| `android:authorities` | Unique identifier(s) for a Content Provider, used to resolve content URIs. |
| `android:grantUriPermissions` | Allows temporary access to specific URIs from a provider to other apps. |
| `android:value` | Supplies a simple scalar value to a `<meta-data>` entry. |
| `android:resource` | Points to a resource reference as the value of a `<meta-data>` entry. |
| `android:scheme` | Defines the URI scheme an intent filter's `<data>` element matches (e.g., `otpauth`). |
| `android:mimeType` | Defines the MIME type an intent filter's `<data>` element matches (e.g., `image/*`). |

---

## Part 2

### Selected Attribute: `android:largeHeap="true"`

**Source:** Used in Aegis on the `<application>` element to request a larger Dalvik/ART heap allocation for the app process.

### Where and How It Was Applied

Added to the `<application>` element in `app/src/main/AndroidManifest.xml`:

```xml
<application
    ...
    android:supportsRtl="true"
    android:largeHeap="true"
    android:theme="@style/Theme.CS712AndroidApp">
```

### Observable Effect

`android:largeHeap="true"` does not produce a visible UI change. Its effect is runtime: the Android system grants the app a larger heap size when requested via `Runtime.getRuntime().maxMemory()`. This reduces the likelihood of `OutOfMemoryError` crashes when the app handles memory-intensive operations such as loading large images, processing notification payloads, or managing foreground service data. The CS712AndroidApp already uses a foreground service (`MyForegroundService`), making this a practical safeguard against memory pressure.

---

## Submission Links

- **Assignment 1 app AndroidManifest.xml:** https://raw.githubusercontent.com/winston-spencer/Aegis/refs/heads/master/app/src/main/AndroidManifest.xml
- **GitHub repo:** https://github.com/winston-spencer/CS712AndroidApp.git
- **CS712AndroidApp commit link:** https://github.com/winston-spencer/CS712AndroidApp/commit/b0be83a0006cb9589e6176b04bd7e438ea5616f0
- **Commit ID:** `b0be83a0006cb9589e6176b04bd7e438ea5616f0`
