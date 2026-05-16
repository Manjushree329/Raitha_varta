# Raitha-Varta

Android App Development using GenAI - Agriculture Advisory Flash Cards

Raitha-Varta is a farmer-friendly Android application that converts long crop advisory documents into short, actionable daily tips. The app is designed for farmers who need clear guidance in the field: what to do, when to do it, and what precautions to follow.

The idea is simple: make scientific agricultural advice feel as easy to consume as a one-minute social media card, while keeping the content useful, localized, and available in Kannada and English.

## Problem Statement

Agricultural experts such as Krishi Vigyana Kendra and agromet advisory teams release weekly crop advisories, but these are often long PDFs or text-heavy bulletins. Many farmers do not have the time or interest to read lengthy documents while working in the field.

Farmers need:

- Short, actionable tips instead of long reports.
- District and crop-specific guidance.
- Kannada-first content for local usability.
- High-contrast screens that are readable outdoors.
- Offline-friendly access to important advice.

## Vision

Raitha-Varta acts as a Flash-Card Advisor for farmers. Each card gives one clear action, such as:

- Spray guidance for a pest or disease.
- Best time to irrigate, harvest, or weed.
- Weather-linked precautions.
- Crop-specific advisory highlights.

It is like "Instagram for Farming", but focused on scientifically backed agricultural guidance.

## Key Features

- Daily Tip: A highlighted flash-card style advisory with crop image, short instruction, and expert source.
- Crop Categories: Filter advisories by crops and themes such as Paddy, Areca Nut, Coconut, Tomato, Irrigation, Fertilizer, Pest Control, Moringa, and Maize.
- Kannada and English Support: Users can choose a preferred language and switch language inside the app.
- Success Stories: Local advisory highlight cards showing practical farming outcomes and examples.
- Expert Ask: Simulated expert flow where a farmer can capture or upload a leaf/crop image and receive source-backed guidance.
- Saved Tips: Farmers can bookmark important advisories for later use.
- Text-to-Speech: Listen button reads advisory content aloud for accessibility.
- Farmer Profile: Stores farmer name, district, taluk, phone number, and selected crops.
- Simulated OTP Login: Student-project-friendly authentication flow.
- Field-Friendly UI: Large cards, strong colors, simple navigation, and bottom tab access.

## App Flow

1. Splash screen introduces Raitha-Varta.
2. User selects language: Kannada or English.
3. User enters mobile number and verifies simulated OTP.
4. User completes farmer profile with district, taluk, and crop preferences.
5. Home screen opens instantly with the daily advisory card.
6. User can browse crop categories, save tips, listen to tips, share tips, and ask an expert.

## Screens

- Language Selection
- Phone Number Login
- OTP Verification
- Farmer Profile Setup
- Home Dashboard
- Daily Tip Flash Card
- All Tips with Search and Filters
- Expert Ask
- Saved Tips
- Profile and Preferences
- Notifications
- Tip Detail View

## Tech Stack

| Layer | Technology |
| --- | --- |
| Platform | Android |
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | Single-activity Compose app |
| Local State | SharedPreferences for profile, language, and saved tips |
| Media | Local drawable/image resources |
| Accessibility | Android TextToSpeech |
| Image Input | Camera, gallery picker, FileProvider |
| Build System | Gradle Kotlin DSL |
| Minimum SDK | 24 |
| Target SDK | 35 |

## Project Structure

```text
RaithaVarta/
|-- app/
|   |-- src/main/
|   |   |-- java/com/raithavarta/app/
|   |   |   |-- MainActivity.kt
|   |   |   |-- data/
|   |   |   |   |-- RaithaModels.kt
|   |   |   |-- ui/
|   |   |   |   |-- RaithaVartaApp.kt
|   |   |   |   |-- Theme.kt
|   |   |-- res/
|   |   |   |-- drawable/
|   |   |   |-- drawable-nodpi/
|   |   |   |-- values/
|   |   |   |-- xml/
|-- build.gradle.kts
|-- settings.gradle.kts
|-- gradle.properties
|-- README.md
```

## Current Implementation Notes

- Advisory data is stored in Kotlin data models for fast startup and offline-style access.
- Farmer preferences and saved tips are persisted locally using SharedPreferences.
- Expert Ask is simulated for a student prototype and returns crop-based advisory guidance.
- The app currently uses Jetpack Compose components for the flash-card experience.
- Future production versions can add Room Database for structured offline storage, Glide/Coil for remote image loading, and a real GenAI backend for advisory summarization.

## GenAI Role in the Project

The GenAI concept behind Raitha-Varta is to transform complex advisory material into short, farmer-readable cards:

- Input: Crop advisory PDFs, agromet bulletins, and expert notes.
- Processing: Summarize long advisory content into one action per card.
- Output: Bilingual short tips with crop, weather, dosage, and precaution fields.

In this prototype, the generated advisory content is represented as structured sample data inside the app.

## Impact Goals

- Precision Farming: Bring expert knowledge to small and medium farms.
- Yield Improvement: Reduce crop losses caused by pests, disease, weather stress, and incorrect fertilizer use.
- Digital Inclusion: Make scientific data simple, visual, and accessible in Kannada.
- Faster Decision-Making: Help farmers act quickly through one-minute advisory cards.

## Success Criteria

- The app opens quickly and shows the Daily Tip on startup.
- Flash-card content is available in Kannada and English.
- Crop filters help farmers find relevant advisories.
- UI remains readable and high-contrast for outdoor field use.
- Expert Ask demonstrates how image-based crop help can work.
- Saved tips and profile preferences remain available across app launches.

## Getting Started

### Prerequisites

- Android Studio
- JDK 17
- Android SDK with API 35
- Gradle wrapper included in the project

### Run the App

1. Clone the repository:

```bash
git clone https://github.com/Manjushree329/Raitha_varta.git
```

2. Open the project in Android Studio.

3. Let Gradle sync the project.

4. Run the app on an emulator or Android device.

### Build from Terminal

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

On macOS/Linux:

```bash
./gradlew assembleDebug
```

The debug APK will be generated under:

```text
app/build/outputs/apk/debug/
```

## Permissions Used

- Internet: Reserved for future online advisory or backend features.
- Camera: Used by Expert Ask to capture crop or leaf images.
- Read Images/Storage: Used to pick crop images from the device.
- Notifications: Reserved for advisory reminder notifications.

## Future Enhancements

- Real GenAI summarization pipeline for advisory PDFs.
- Room Database for robust offline advisory storage.
- Weather API integration for district-level recommendations.
- Push notifications for daily crop tips.
- Real expert dashboard for KVK/agriculture officer review.
- Voice-first Kannada assistant for low-literacy users.
- Remote image loading with Glide or Coil.
- ViewPager-style card swiping for a richer flash-card experience.

## Project Title

Android App Development using GenAI - Raitha-Varta (Agriculture)

## Author

K Manjushree

## Repository

[Raitha-Varta on GitHub](https://github.com/Manjushree329/Raitha_varta)
