# SystemInfo

SystemInfo is a simple Android application that retrieves and displays various technical details about the device it's running on.

## Features

- Displays key hardware and software information:
  - Manufacturer
  - Model
  - Brand
  - Build Type
  - User
  - Base Version Code
  - Incremental Version
  - SDK Version (API Level)
  - Display ID

## Screenshots

*(Add screenshots here to show the app in action)*

## How it works

The app uses the `android.os.Build` class to access system properties. This information is then formatted into a string and displayed in a multi-line `EditText` on the main screen.

```kotlin
val systemInfo = """
    Manufacturer: ${Build.MANUFACTURER}
    Model: ${Build.MODEL}
    Brand: ${Build.BRAND}
    // ... and more
""".trimIndent()

systemInfoWidget.setText(systemInfo)
```

## Getting Started

### Prerequisites

- Android Studio Jellyfish | 2023.3.1 or newer
- Android SDK 34 (Target)
- Minimum SDK 24

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/SystemInfo.git
   ```
2. Open the project in **Android Studio**.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Technologies Used

- **Kotlin**: Primary programming language.
- **Jetpack Libraries**: `AppCompat`, `ConstraintLayout`, `Activity`.
- **Material Design**: For UI components.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
