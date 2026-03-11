# Hello World V2

A simple Android application developed as part of a tutorial. This project demonstrates basic Android UI components, layout management, and multi-orientation support.

## Features

- **Responsive Layout**: Uses `ConstraintLayout` to ensure the UI adapts well to different screen sizes.
- **Landscape Support**: Includes a specialized layout for landscape orientation (`layout-land/activity_main.xml`).
- **UI Components**:
    - `TextView` for displaying custom messages.
    - `ImageView` displaying a friendly "smilycat".
    - `CalendarView` for date interaction.
- **Styling**: Custom background gradients and color resources.

## Screenshots

<p align="center">
  <img src="screenshot.png" width="300" title="Main Screen">
</p>

## Project Structure

- `MainActivity.kt`: The main entry point of the application.
- `activity_main.xml`: Portrait layout configuration.
- `layout-land/activity_main.xml`: Landscape layout configuration.
- `strings.xml`: Centralized string resources for easy localization.

## Getting Started

### Prerequisites

- Android Studio Flamingo or later.
- Android SDK 33+.

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## Technologies Used

- **Kotlin**: Primary programming language.
- **Jetpack Libraries**:
    - `AppCompat`: For backward compatibility.
    - `ConstraintLayout`: For building flexible layouts.
    - `Core KTX`: For idiomatic Kotlin usage of Android APIs.
- **Material Components**: For modern UI design elements.

## Author

- **dam_a51472**
