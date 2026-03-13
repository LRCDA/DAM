# Hello World V2 - Exercise 7

An evolved Android application that demonstrates advanced layout management, multi-orientation UI strategies, and basic form handling.

## Exercise 7: Multi-Orientation UI & Form Validation

This update introduces a distinct user interface for landscape mode, showcasing how to handle different layouts within a single Activity.

### Key Features

- **Adaptive UI Design**: 
    - **Portrait Mode**: Displays a welcoming interface with a "Hello World" message, a cat illustration, and a `CalendarView`.
    - **Landscape Mode**: Transforms into a functional **Sign Up form**, featuring email and password inputs with a custom-styled button and gradient background.
- **Dynamic View Binding**: `MainActivity.kt` safely manages UI components that are present in only one of the orientations using nullable references and safe calls.
- **Form Validation**:
    - Validates that email and password fields are not empty.
    - Provides user feedback via `Toast` notifications ("Sign Up Successfully" or "Please enter email and password").
- **Custom Theming**: 
    - Usage of custom drawables for `EditText` and `Button` backgrounds in the Sign Up form.
    - Implementation of a `gradient_background` for the landscape layout.

## Screenshots

<p align="center">
  <img src="screenshot_portrait.png" width="250" title="Portrait Mode">
  <img src="screenshot_landscape.png" width="450" title="Landscape Mode (Sign Up Form)">
</p>

## Technical Implementation

### Activity Logic (`MainActivity.kt`)
The activity uses `initView()` to attempt to find views. Since the Sign Up form elements only exist in the landscape layout (`layout-land/activity_main.xml`), the code uses safe calls (`?.`) to avoid `NullPointerException` when running in portrait mode.

```kotlin
private fun initView() {
    signUpBtn = findViewById(R.id.button)
    // ...
    signUpBtn?.setOnClickListener {
        // Validation logic
    }
}
```

### Layouts
- `res/layout/activity_main.xml`: Main landing UI (Portrait).
- `res/layout-land/activity_main.xml`: Specialized Sign Up form (Landscape).

## Project Structure

- `MainActivity.kt`: Contains the lifecycle logic and form validation.
- `res/drawable/`: Contains custom backgrounds (`gradient_background.xml`, etc.).
- `res/values/strings.xml`: Centralized strings for messages and labels.

## Getting Started

1. Open in Android Studio.
2. Build and run on an emulator.
3. **Rotate the device** to see the transition between the greeting screen and the Sign Up form.

## Author

- **dam_a51472**
