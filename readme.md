# RAITHA-VARTA — COMPLETE STANDARD OPERATING PROCEDURE (SOP)

## Android Application Development Using Generative AI

---

# DOCUMENT CONTROL

| Field | Details |
|---|---|
| Document Title | Raitha-Varta — Full SOP & Development Blueprint |
| Version | 1.0 |
| Prepared By | K Manjushree (1GD22CS018) |
| Institution | Gopalan College of Engineering and Management |
| Platform | Android (Kotlin + Jetpack Compose) |
| Backend | Firebase + Node.js |
| AI Layer | Google Gemini API / Simulated AI |
| Date | 2025 |

---

# TABLE OF CONTENTS

1. Project Overview
2. System Architecture
3. Technology Stack
4. Database Schema
5. Screen-by-Screen UI Prompts
6. Screen Navigation & Connection Map
7. Backend & API Workflow
8. AI Integration Details
9. Security & Authentication Flow
10. Offline Functionality
11. Testing Plan
12. Deployment Plan
13. Appendix

---

---

# SECTION 1 — PROJECT OVERVIEW

## 1.1 Application Name
**Raitha-Varta** (ರೈತ-ವಾರ್ತಾ)
*Translation: Farmer's News / Farmer's Information*

## 1.2 Purpose
Raitha-Varta is an AI-powered Android application designed to deliver daily, actionable farming tips to rural farmers in Karnataka. The application converts complex agricultural advisory PDFs and expert knowledge into simple, swipeable flash cards. It supports Kannada and English, uses phone-based OTP authentication, and stores all farmer data persistently in a cloud and local database.

## 1.3 Target Users
- Small and medium-scale farmers in Karnataka
- Farmers growing Paddy, Areca Nut, Coconut, Tomato, and other crops
- Farmers with basic smartphone literacy
- Farmers in areas with intermittent internet connectivity

## 1.4 Core Problems Solved

| Problem | Solution |
|---|---|
| Complex agricultural PDFs | Swipeable flash card tips |
| Language barrier | Kannada + English support |
| No expert access | Expert Ask feature with AI |
| Forgetting saved advice | Persistent cloud + local storage |
| No proof of effectiveness | Success Stories section |
| No audio support for low literacy | Text-to-Speech audio button on each card |

## 1.5 Application Goals
- Deliver one actionable tip per card in under 30 seconds of reading time
- Support crop categories: Paddy, Areca Nut, Coconut, Tomato, Fertilizer, Irrigation, Pest Control
- Authenticate farmers securely using mobile OTP
- Store all data per farmer profile
- Work partially offline using Room Database

---

---

# SECTION 2 — SYSTEM ARCHITECTURE

## 2.1 High-Level Architecture Diagram (Text Representation)

```
┌─────────────────────────────────────────────────────────────────┐
│                        FARMER'S ANDROID DEVICE                  │
│                                                                  │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐   │
│  │  UI Layer    │    │  ViewModel   │    │  Room Database   │   │
│  │  (Compose/  │◄──►│  (State +    │◄──►│  (Offline Local  │   │
│  │   XML)      │    │   Logic)     │    │   Storage)       │   │
│  └──────────────┘    └──────┬───────┘    └──────────────────┘   │
│                             │                                    │
└─────────────────────────────┼────────────────────────────────────┘
                              │ Retrofit / Firebase SDK
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        BACKEND LAYER                            │
│                                                                  │
│  ┌──────────────────┐    ┌───────────────────────────────────┐  │
│  │  Firebase Auth   │    │  Firebase Firestore               │  │
│  │  (OTP / Phone)   │    │  (Farmer Profile, Tips, Stories)  │  │
│  └──────────────────┘    └───────────────────────────────────┘  │
│                                                                  │
│  ┌──────────────────┐    ┌───────────────────────────────────┐  │
│  │  Firebase Storage│    │  Node.js / Cloud Functions        │  │
│  │  (Leaf Images,   │    │  (AI Processing, Tip Generation)  │  │
│  │   Crop Photos)   │    └───────────────────────────────────┘  │
│  └──────────────────┘                                           │
└──────────────────────────────┬──────────────────────────────────┘
                               │ HTTPS / REST
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                     AI & EXTERNAL SERVICES                      │
│                                                                  │
│  ┌──────────────────────┐    ┌──────────────────────────────┐   │
│  │  Google Gemini API   │    │  OpenWeatherMap API           │   │
│  │  (Tip Generation +   │    │  (Weather-Based Advisory)     │   │
│  │   Disease Detection) │    └──────────────────────────────┘   │
│  └──────────────────────┘                                       │
│                                                                  │
│  ┌──────────────────────┐                                       │
│  │  Android TTS Engine  │                                       │
│  │  (Audio Playback)    │                                       │
│  └──────────────────────┘                                       │
└─────────────────────────────────────────────────────────────────┘
```

## 2.2 Data Flow Summary

```
FARMER OPENS APP
      │
      ▼
Language Selection (Stored in SharedPreferences)
      │
      ▼
Phone Number Entry → Firebase OTP → Verified
      │
      ▼
Profile Check (Does farmer exist in Firestore?)
    ├── YES → Load Dashboard with saved data
    └── NO  → Profile Setup Screen → Save to Firestore
              │
              ▼
         Dashboard (Pull tips from Firestore → Cache in Room)
              │
         ┌────┴────┐
         ▼         ▼
    Browse Tips  Expert Ask
    Save/Share   Upload Leaf Image
    View Detail  → Gemini AI → Response
    Filter Crops
    Success Stories
```

---

---

# SECTION 3 — TECHNOLOGY STACK

## 3.1 Frontend (Android)

| Component | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose + XML Layouts |
| Navigation | Jetpack Navigation Component |
| Card Swiping | ViewPager2 + RecyclerView |
| Image Loading | Glide Library |
| Local Storage | Room Database |
| State Management | ViewModel + LiveData / StateFlow |
| Dependency Injection | Hilt (Dagger) |
| Audio Playback | Android TextToSpeech (TTS) Engine |
| Camera | CameraX Library |
| Language Preference | SharedPreferences |
| Network Calls | Retrofit2 + OkHttp |

## 3.2 Backend & Cloud

| Component | Technology |
|---|---|
| Authentication | Firebase Authentication (Phone/OTP) |
| Primary Database | Firebase Firestore (NoSQL) |
| File Storage | Firebase Cloud Storage |
| Serverless Functions | Firebase Cloud Functions (Node.js) |
| Push Notifications | Firebase Cloud Messaging (FCM) |

## 3.3 AI & External APIs

| Service | Purpose |
|---|---|
| Google Gemini API | Tip generation, disease detection response |
| OpenWeatherMap API | Weather-based tip recommendations |
| Android TTS | Audio reading of tip content |

## 3.4 Development Tools

| Tool | Purpose |
|---|---|
| Android Studio (Latest) | Primary IDE |
| Figma | UI/UX Design Mockups |
| Postman | API Testing |
| Firebase Console | Database & Auth Management |
| Git + GitHub | Version Control |

---

---

# SECTION 4 — DATABASE SCHEMA

## 4.1 Firebase Firestore Collections

### Collection: `farmers`
```
farmers/
  └── {phoneNumber}/
        ├── uid: String
        ├── phoneNumber: String
        ├── name: String
        ├── district: String
        ├── taluk: String
        ├── cropInterests: Array[String]
        ├── preferredLanguage: String ("kn" or "en")
        ├── profileCreatedAt: Timestamp
        └── lastLoginAt: Timestamp
```

### Collection: `tips`
```
tips/
  └── {tipId}/
        ├── tipId: String
        ├── title_en: String
        ├── title_kn: String
        ├── shortAdvice_en: String
        ├── shortAdvice_kn: String
        ├── detailedInfo_en: String
        ├── detailedInfo_kn: String
        ├── cropCategory: String
        ├── subCategory: String ("pest", "fertilizer", "irrigation", "general")
        ├── cropImageUrl: String
        ├── pestImageUrl: String (nullable)
        ├── pesticideRecommendation: String
        ├── fertilizerDetails: String
        ├── dosage: String
        ├── precautions: String
        ├── expertName: String
        ├── weatherCondition: String ("rain", "sunny", "cloudy", "any")
        ├── isDaily: Boolean
        ├── dailyDate: String (YYYY-MM-DD)
        └── createdAt: Timestamp
```

### Collection: `savedTips`
```
savedTips/
  └── {phoneNumber}/
        └── tips/
              └── {tipId}/
                    ├── tipId: String
                    ├── savedAt: Timestamp
                    └── tipSnapshot: Map (full tip data)
```

### Collection: `expertQueries`
```
expertQueries/
  └── {queryId}/
        ├── queryId: String
        ├── farmerPhone: String
        ├── imageUrl: String
        ├── aiResponse: String
        ├── diseaseDetected: String
        ├── treatmentRecommendation: String
        ├── submittedAt: Timestamp
        └── status: String ("processing", "completed")
```

### Collection: `successStories`
```
successStories/
  └── {storyId}/
        ├── storyId: String
        ├── farmerName: String
        ├── district: String
        ├── cropType: String
        ├── beforeYield: String
        ├── afterYield: String
        ├── tipFollowed: String
        ├── story_en: String
        ├── story_kn: String
        ├── farmerImageUrl: String
        ├── cropImageUrl: String
        └── publishedAt: Timestamp
```

## 4.2 Room Database (Local / Offline)

### Table: `cached_tips`
```sql
CREATE TABLE cached_tips (
    tip_id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    short_advice TEXT,
    detailed_info TEXT,
    crop_category TEXT,
    crop_image_url TEXT,
    pest_image_url TEXT,
    is_saved INTEGER DEFAULT 0,
    daily_date TEXT,
    cached_at TEXT
);
```

### Table: `farmer_profile`
```sql
CREATE TABLE farmer_profile (
    phone_number TEXT PRIMARY KEY,
    name TEXT,
    district TEXT,
    taluk TEXT,
    crop_interests TEXT,
    preferred_language TEXT,
    last_sync TEXT
);
```

### Table: `saved_tips_local`
```sql
CREATE TABLE saved_tips_local (
    tip_id TEXT PRIMARY KEY,
    full_tip_json TEXT,
    saved_at TEXT
);
```

---

---

# SECTION 5 — SCREEN-BY-SCREEN UI PROMPTS

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 1 — SPLASH SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design an Android splash screen for an app called "Raitha-Varta" (ರೈತ-ವಾರ್ತಾ).

LAYOUT:
- Full screen with a rich green gradient background 
  (top: #2E7D32, bottom: #1B5E20)
- Center: App logo — a circular emblem containing 
  a golden wheat/paddy plant icon
- Below logo: App name in large bold text
  Line 1: "Raitha-Varta" in English (white, 32sp)
  Line 2: "ರೈತ-ವಾರ್ತಾ" in Kannada (white, 28sp)
- Below name: Tagline "ರೈತರ ದೈನಂದಿನ ಮಾರ್ಗದರ್ಶಿ" 
  (Farmer's Daily Guide) in light green (#A5D6A7), 18sp
- Bottom: A thin golden horizontal divider line
- Below divider: College name and student name 
  in small white text (12sp)
- A circular progress indicator in gold color 
  at the very bottom

BEHAVIOR:
- Display for 2.5 seconds
- Check SharedPreferences for language preference
- Check Firebase Auth for existing login session
- Navigate accordingly after delay

COLORS:
- Background: Green gradient #2E7D32 → #1B5E20
- Logo border: Gold #FFD700
- Text: White #FFFFFF
- Tagline: Light green #A5D6A7
- Progress: Gold #FFD700

FONT: Poppins Bold for title, Poppins Regular for tagline
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 2 — LANGUAGE SELECTION SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design an Android language selection screen for Raitha-Varta.

LAYOUT:
- Background: Soft cream/off-white #FAFAFA with 
  a subtle green wave pattern at top and bottom
- Top section (25% height):
  * Green header bar (#2E7D32)
  * App logo centered, small size (60dp)
  * Below logo: "Choose Your Language" in white (20sp)
  * Below: "ನಿಮ್ಮ ಭಾಷೆ ಆಯ್ಕೆ ಮಾಡಿ" in white (18sp)

- Center section (50% height):
  * Two large selection cards, stacked vertically 
    with 20dp gap between them
  
  CARD 1 — KANNADA:
  * Size: Full width minus 32dp padding, height 140dp
  * Background: White with 3dp green left border
  * Shadow elevation: 6dp
  * Rounded corners: 16dp
  * Left: Kannada script icon or Karnataka flag emoji
  * Center-left: 
    - "ಕನ್ನಡ" in bold, 28sp, green #2E7D32
    - "Kannada" in regular, 16sp, grey #757575
  * Right: Radio button (unchecked by default)
  * On tap: Card gets green border #2E7D32 (3dp), 
    radio button fills green, light green background #E8F5E9
  
  CARD 2 — ENGLISH:
  * Same card style as Kannada card
  * Left: English "A" icon or British/India flag
  * Center-left:
    - "English" in bold, 28sp, green #2E7D32
    - "ಇಂಗ್ಲಿಷ್" in regular, 16sp, grey #757575
  * Right: Radio button

- Bottom section (25% height):
  * "Continue / ಮುಂದುವರೆಯಿರಿ" button
  * Full width minus 32dp padding
  * Height: 56dp
  * Background: Green #2E7D32 (active) / Grey (inactive)
  * Text: White, bold, 18sp
  * Rounded corners: 28dp (pill shape)
  * Only becomes active when a language is selected
  * Arrow icon → on right side of button

BEHAVIOR:
- User must select one language card before Continue 
  button becomes active
- Selection is stored in SharedPreferences as 
  "preferred_language" = "kn" or "en"
- On Continue: Navigate to Phone Number Entry Screen
- This screen appears ONLY on first launch or 
  if no language preference is found

COLORS:
- Background: #FAFAFA
- Header: #2E7D32
- Card background: #FFFFFF
- Selected card background: #E8F5E9
- Selected border: #2E7D32
- Button active: #2E7D32
- Button inactive: #BDBDBD
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 3 — PHONE NUMBER ENTRY SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design an Android phone number authentication screen 
for Raitha-Varta.

LAYOUT:
- Background: White #FFFFFF

- TOP SECTION (35% height):
  * Curved green header (#2E7D32) with wave bottom edge
  * Centered: Farmer icon illustration 
    (simple line art of farmer with hat, white color)
  * Below illustration: "Welcome to Raitha-Varta" (white, 22sp, bold)
  * Sub-text: "ರೈತರ ಡಿಜಿಟಲ್ ಸಹಾಯಕ" (white, 16sp)

- MIDDLE SECTION (45% height):
  * White card with 16dp elevation, rounded top corners 24dp
  * Overlaps the green header slightly (negative margin)
  
  Inside card:
  * Heading: "Enter Mobile Number" (English, 20sp, bold, dark #212121)
  * Sub-heading: "ನಿಮ್ಮ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ ನಮೂದಿಸಿ" (Kannada, 15sp, #616161)
  * 24dp spacing
  
  * Phone Number Input Field:
    - Row layout: [Country Code Box] [Phone Number Field]
    - Country Code Box: 
      * Fixed width 70dp, height 56dp
      * Border: 1dp solid #BDBDBD, rounded 12dp
      * Content: Indian flag emoji + "+91"
      * Background: #F5F5F5
    - Phone Number Field:
      * Fills remaining width, height 56dp
      * Hint text: "9XXXXXXXXX" in grey
      * Input type: number, max 10 digits
      * Border: 1dp solid #BDBDBD (unfocused)
              2dp solid #2E7D32 (focused)
      * Rounded corners: 12dp
      * Font size: 18sp
  
  * 8dp spacing
  * Info text: "ⓘ OTP will be sent to this number" 
    (12sp, #757575)
  * 24dp spacing
  
  * SEND OTP BUTTON:
    - Full width, height 56dp
    - Background: Green gradient #43A047 → #2E7D32
    - Text: "Send OTP / OTP ಕಳುಹಿಸಿ" (white, bold, 18sp)
    - Rounded: 28dp
    - Loading spinner visible on click (replaces text)
  
  * 16dp spacing
  * Terms text: "By continuing, you agree to our Terms of Service"
    (center, 12sp, grey, with clickable link styling)

- BOTTOM SECTION (20% height):
  * Illustration: Small icons of crops (paddy, coconut, tomato)
    arranged horizontally in a decorative strip
  * Background: Light green #F1F8E9

BEHAVIOR:
- Validate 10-digit Indian mobile number before 
  enabling button
- On Send OTP: Call Firebase Auth verifyPhoneNumber()
- Show loading state on button
- On success: Navigate to OTP Verification Screen
- On failure: Show SnackBar with error message
- Show error state on input field if invalid number

ERROR STATES:
- Invalid number: Red border on input, 
  error text "Please enter valid 10-digit number"
- Network error: SnackBar "Check internet connection"
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 4 — OTP VERIFICATION SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design an Android OTP verification screen for Raitha-Varta.

LAYOUT:
- Background: White #FFFFFF

- TOP SECTION:
  * Same curved green header as Phone screen
  * Icon: Shield with checkmark (lock/security icon, white)
  * Title: "Verify OTP" (white, 22sp, bold)
  * Sub: "OTP ಪರಿಶೀಲನೆ" (white, 16sp)

- MIDDLE SECTION (white card):
  * Heading: "Enter 6-Digit OTP" (dark, 20sp, bold)
  * Sub: "ಕಳುಹಿಸಿದ OTP ನಮೂದಿಸಿ" (grey, 15sp)
  
  * Phone display: 
    "OTP sent to +91 XXXXXXXX89" (grey, 14sp, centered)
  
  * 32dp spacing
  
  * OTP INPUT BOXES:
    - 6 individual square input boxes in a horizontal row
    - Each box: 48dp × 56dp
    - Border: 2dp solid #BDBDBD (empty)
            2dp solid #2E7D32 (filled)
            2dp solid #F44336 (error)
    - Rounded corners: 8dp
    - Font: Bold, 24sp, centered
    - Auto-focus: Moves to next box after digit entered
    - Auto-backspace: Goes to previous box on delete
    - Spacing between boxes: 8dp
  
  * 24dp spacing
  
  * Timer: "Resend OTP in 00:30" (centered, 14sp, green)
    After 30s: "Resend OTP" clickable link (green, underlined)
  
  * 24dp spacing
  
  * VERIFY BUTTON:
    - Full width, height 56dp
    - Background: Green gradient
    - Text: "Verify & Continue / ಪರಿಶೀಲಿಸಿ" (white, bold, 18sp)
    - Rounded: 28dp
    - Loading spinner visible during verification

  * CHANGE NUMBER link below button (small, grey, underlined)

BEHAVIOR:
- Auto-submit when 6 digits are entered
- Call Firebase Auth signInWithCredential(verificationCode)
- If NEW user: Navigate to Profile Setup Screen
- If EXISTING user: Navigate to Dashboard 
  (with all previously saved data loaded)
- Wrong OTP: Show error state on all boxes (red border)
  + error message "Incorrect OTP. Please try again."
- Resend timer countdown from 30 seconds
- After 30s, "Resend" link becomes active, 
  triggers new OTP request

IMPORTANT: After successful verification, check 
Firestore for existing farmer profile using phone number.
If exists → load profile → go to Dashboard
If not exists → go to Profile Setup
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 5 — FARMER PROFILE SETUP SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design an Android farmer profile setup screen 
for Raitha-Varta. This screen appears only for new users.

LAYOUT:
- Background: Light green #F1F8E9

- TOP BAR:
  * Green (#2E7D32) app bar, height 56dp
  * Left: App logo (small, 32dp)
  * Center: "Complete Your Profile" / "ನಿಮ್ಮ ಪ್ರೊಫೈಲ್ ಪೂರ್ಣಗೊಳಿಸಿ"
  * Progress indicator: Step 1 of 1 (or step dots)

- PROGRESS SECTION:
  * Horizontal step progress bar below top bar
  * 3 steps: Personal Info → Location → Crop Interests
  * Active step: Filled green circle with number
  * Completed: Green check circle
  * Upcoming: Grey empty circle
  * Lines connecting circles: green (completed) / grey (upcoming)

STEP 1 — PERSONAL INFORMATION:
White card with 12dp elevation:
  * Section title: "Personal Details" (green, bold, 16sp)
  * Divider line (green, light)
  
  * NAME FIELD:
    - Label: "Full Name / ಪೂರ್ಣ ಹೆಸರು" (14sp, dark)
    - Input: Full width text field
    - Hint: "Enter your name"
    - Prefix icon: Person icon
    - Rounded: 12dp border
  
  * 16dp spacing
  
  * PHONE FIELD (Pre-filled, non-editable):
    - Label: "Mobile Number" (14sp, dark)
    - Input: Pre-filled with verified phone number
    - Background: Grey #F5F5F5 (disabled state)
    - Prefix icon: Phone icon
    - Lock icon on right side
    - Text: "Verified ✓" in green below field (12sp)

STEP 2 — LOCATION:
White card:
  * Section title: "Farm Location / ಜಮೀನಿನ ಸ್ಥಳ" (green, bold)
  
  * DISTRICT DROPDOWN:
    - Label: "District / ಜಿಲ್ಲೆ" 
    - Exposed dropdown menu
    - Options: All Karnataka districts 
      (Bangalore Rural, Tumkur, Hassan, Shimoga, 
       Dakshina Kannada, Mandya, Mysore, Chikkamagaluru,
       Kodagu, Belgaum, Bidar, Bellary, etc.)
    - Prefix icon: Location pin icon
    - Dropdown arrow on right
  
  * 16dp spacing
  
  * TALUK DROPDOWN:
    - Label: "Taluk / ತಾಲ್ಲೂಕು"
    - Dynamically populated based on selected district
    - Exposed dropdown menu
    - Disabled until District is selected
    - When disabled: Grey background, grey text
    - When enabled: White background, normal styling

STEP 3 — CROP INTERESTS:
White card:
  * Section title: "Select Your Crops / ನಿಮ್ಮ ಬೆಳೆಗಳನ್ನು ಆಯ್ಕೆ ಮಾಡಿ"
  * Sub-text: "Select all crops you grow 
    (ನೀವು ಬೆಳೆಯುವ ಎಲ್ಲಾ ಬೆಳೆಗಳನ್ನು ಆಯ್ಕೆ ಮಾಡಿ)" (grey, 13sp)
  
  * CROP CHIP GRID:
    Grid of crop selection chips (3 per row):
    
    Each chip:
    - Height: 48dp, auto-width with 8dp horizontal padding
    - Unselected: White background, green border, green text
    - Selected: Green background #2E7D32, white text, 
                white tick icon on left
    - Rounded: 24dp (pill)
    - Font: 14sp
    
    Crops available:
    🌾 Paddy (ಭತ್ತ)
    🌴 Areca Nut (ಅಡಿಕೆ)
    🥥 Coconut (ತೆಂಗಿನಕಾಯಿ)
    🍅 Tomato (ಟೊಮ್ಯಾಟೊ)
    🧅 Onion (ಈರುಳ್ಳಿ)
    🌽 Maize (ಮೆಕ್ಕೆಜೋಳ)
    🫚 Sugarcane (ಕಬ್ಬು)
    🥬 Ragi (ರಾಗಿ)
    🌶️ Chilli (ಮೆಣಸಿನಕಾಯಿ)
    🫘 Groundnut (ಕಡಲೆಕಾಯಿ)
    🍌 Banana (ಬಾಳೆ)
    🥭 Mango (ಮಾವು)
  
  * "Select at least 1 crop" validation text (grey, 12sp)

BOTTOM SECTION:
  * Sticky bottom bar (white, elevation 8dp):
    - SAVE & CONTINUE button:
      Full width, 56dp height
      Green gradient background
      Text: "Save & Continue / ಉಳಿಸಿ ಮತ್ತು ಮುಂದುವರೆಯಿರಿ"
      White, bold, 18sp
      Rounded: 28dp

BEHAVIOR:
- All fields are mandatory (Name, District, Taluk, 
  at least 1 crop)
- On Save: 
  * Validate all fields
  * Save to Firebase Firestore under farmers/{phoneNumber}
  * Save to Room Database (farmer_profile table)
  * Navigate to Dashboard
- Show loading overlay during save operation
- On error: Show specific field-level error messages

VALIDATIONS:
- Name: Not empty, min 2 characters
- District: Must be selected
- Taluk: Must be selected
- Crops: At least 1 must be selected
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 6 — MAIN DASHBOARD / HOME SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the main dashboard home screen for Raitha-Varta.
This is the most important screen of the app.

OVERALL STRUCTURE:
- Bottom Navigation Bar (persistent, 5 tabs)
- Scrollable content above bottom nav

═══════════════════════════════════════
TOP APP BAR (Green #2E7D32):
═══════════════════════════════════════
- Left: Hamburger menu icon (white) OR Profile avatar
- Center-Left: 
  * "Good Morning, [Farmer Name]!" (white, 18sp, bold)
  * "ಇಂದಿನ ಸಲಹೆ ನೋಡಿ" (white, 14sp, regular)
- Right side: 
  * Weather widget mini (cloud/sun icon + "28°C Mysore")
    White background chip, 12sp, green text
  * Notification bell icon (white) with badge count

═══════════════════════════════════════
WEATHER BANNER (Below app bar):
═══════════════════════════════════════
- Full width card, height 80dp
- Background: Blue-green gradient (#00BCD4 → #0097A7)
- Left: Large weather icon (sun/cloud/rain based on API)
- Center:
  * Location: "Mysore, Karnataka" (white, 14sp)
  * "28°C | Humid 65% | Rain likely in evening" (white, 13sp)
- Right: "Weather Advisory >" link (white, 12sp, underlined)
- Rounded bottom corners: 16dp

═══════════════════════════════════════
CROP CATEGORY FILTER BAR:
═══════════════════════════════════════
- Horizontal scrollable row of filter chips
- Position: Below weather banner, sticky on scroll
- Background: White with bottom shadow

Filter chips:
  * All (ಎಲ್ಲಾ)
  * Paddy (ಭತ್ತ) 🌾
  * Coconut (ತೆಂಗು) 🥥
  * Areca Nut (ಅಡಿಕೆ) 🌴
  * Tomato (ಟೊಮ್ಯಾಟೊ) 🍅
  * Fertilizer (ಗೊಬ್ಬರ) 🧪
  * Irrigation (ನೀರಾವರಿ) 💧
  * Pest Control (ಕೀಟ ನಿಯಂತ್ರಣ) 🐛

Each chip style:
  * Unselected: White pill, #2E7D32 border, green text, 14sp
  * Selected: #2E7D32 filled, white text, white emoji
  * Rounded: 20dp, height: 36dp, horizontal padding: 12dp

═══════════════════════════════════════
TODAY'S DAILY TIP SECTION:
═══════════════════════════════════════
Section header:
  * "ಇಂದಿನ ಸಲಹೆ — Today's Tip" (20sp, bold, dark #212121)
  * Right: "See All →" link (14sp, green)
  * Left: Gold star icon ⭐ before title

SWIPEABLE FLASH CARD DECK (Main Feature):
ViewPager2 implementation — Only the ACTIVE card 
is fully visible, adjacent cards peek from sides.

CARD DESIGN (Height: 420dp, Width: ~85% of screen):
┌─────────────────────────────────────┐
│  [CROP IMAGE - Full width, 200dp]   │
│  High quality image (via Glide)     │
│  Gradient overlay at bottom of img  │
│  Category chip on image: "🌾 Paddy" │
│  (white chip, top-left of image)    │
├─────────────────────────────────────┤
│  CONTENT AREA (white, padding 16dp) │
│                                     │
│  Title: "Brown Plant Hopper         │
│  Management in Paddy"               │
│  (18sp, bold, dark #212121)         │
│  In Kannada if language = "kn"      │
│                                     │
│  Short Advice:                      │
│  "Spray Imidacloprid 17.8 SL @      │
│  0.3ml/L water. Best time: early    │
│  morning or evening."               │
│  (14sp, #424242, 3 lines max)       │
│                                     │
│  ─────────────────────────────      │
│  Weather Tag: 🌧️ "Recommended       │
│  before rain" (13sp, blue chip)     │
│  ─────────────────────────────      │
│                                     │
│  BOTTOM ACTION ROW:                 │
│  [🔖 Save] [📤 Share] [🔊 Listen]  │
│  Each: Icon + text label            │
│  Color: Green for active,           │
│  Grey for inactive (Save toggles)   │
└─────────────────────────────────────┘

Card swipe indicator:
  * Dot indicators below card deck
  * Active dot: Green filled circle 8dp
  * Inactive: Grey outline circle 6dp

CARD ELEVATION: 12dp shadow
CARD CORNERS: 20dp rounded
CARD BACKGROUND: White

ON CARD TAP → Navigate to Tip Detail Screen

═══════════════════════════════════════
MORE TIPS SECTION (Below card deck):
═══════════════════════════════════════
Section header: "More Tips / ಹೆಚ್ಚಿನ ಸಲಹೆಗಳು"
Right: "See All →" (green link)

Horizontal scrollable list of smaller tip cards:
Each mini card:
  * Width: 180dp, Height: 200dp
  * Top: Crop image 100dp height
  * Below: Category chip (colored)
  * Title: 2 lines, 13sp, bold
  * Save icon (bookmark, bottom right)
  * Rounded: 12dp, elevation: 4dp

═══════════════════════════════════════
SUCCESS STORIES PREVIEW:
═══════════════════════════════════════
Section header: "ರೈತರ ಯಶಸ್ಸಿನ ಕಥೆಗಳು | Success Stories"
Right: "View All →" (green)

Horizontal scrollable story cards:
Each card (width: 260dp, height: 160dp):
  * Left half: Farmer photo (circular, 64dp) 
               + Location text (district, 12sp, grey)
  * Right half: Story preview 3 lines (13sp)
  * Bottom: Yield improvement chip 
    "Yield +40% 📈" (green chip, 12sp)
  * Rounded: 16dp, elevation: 6dp, white background

═══════════════════════════════════════
BOTTOM NAVIGATION BAR:
═══════════════════════════════════════
5 tabs, white background, green active color:
  1. 🏠 Home (ಮನೆ) — Currently active
  2. 📋 Tips (ಸಲಹೆ) — All tips with filter
  3. 🤖 Expert Ask (ತಜ್ಞ) — AI disease detection
  4. ⭐ Saved (ಉಳಿಸಿದ) — Saved tips collection
  5. 👤 Profile (ಪ್ರೊಫೈಲ್) — Farmer profile

Active tab:
  * Icon: Green filled version
  * Label: Green #2E7D32, 12sp bold
  * Background indicator: Light green pill #E8F5E9

Inactive tab:
  * Icon: Grey #9E9E9E outlined
  * Label: Grey #9E9E9E, 12sp

BEHAVIORS:
- Filter chips filter the flash card deck in real-time
- Pull-to-refresh loads new tips from Firestore
- Tips load from Room DB cache if offline
- Weather data fetched from OpenWeatherMap API
- Greeting changes based on time of day
  (Morning/Afternoon/Evening in Kannada and English)
- Notification badge shows count of new tips
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 7 — TIP DETAIL SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the Tip Detail full screen for Raitha-Varta.
This screen opens when farmer taps on any flash card.

LAYOUT: Scrollable single screen (no tabs)

TOP IMAGE SECTION (Height: 280dp, fixed):
  * Full-width hero image (crop/pest image via Glide)
  * Image can be pinch-zoomed
  * Gradient overlay from transparent → black (bottom 40%)
  * Overlaid text on image (bottom-left):
    - Category chip: "🌾 Paddy | Pest Control" 
      (white chip, green background, 13sp)
    - Tip title in white bold text (20sp, 2 lines max)
  
  TOP BAR (transparent, overlays image):
  * Left: Back arrow icon (white, in circular 
    semi-transparent background)
  * Right: 
    - Save/Bookmark icon (white outline → gold when saved)
    - Share icon (white)
  
  WEATHER TAG (floating chip on image, top-right):
  * "🌧️ Pre-monsoon Advisory" 
    (blue-white chip, 12sp, rounded 16dp)

CONTENT SCROLL AREA (white background):
Card-like sections with 16dp elevation, 
stacked vertically with 12dp spacing:

────────────────────────────────────
CARD 1: QUICK SUMMARY
────────────────────────────────────
  * Header: "📋 Quick Summary / ಸಾರಾಂಶ" 
    (green, bold, 16sp)
  * Green left border on card (4dp)
  * Content: Short advice text (16sp, #424242, 
    line height 24sp)
  * "Read in Kannada / ಕನ್ನಡದಲ್ಲಿ ಓದಿ" 
    toggle button (if current language differs)
  
  * AUDIO BUTTON (full width, outlined style):
    - Icon: 🔊 Speaker icon
    - Text: "Listen in Audio / ಆಡಿಯೋದಲ್ಲಿ ಕೇಳಿ"
    - Green outline border, green text
    - When playing: Waveform animation + "Pause" option
    - Uses Android TTS Engine

────────────────────────────────────
CARD 2: PEST / DISEASE IMAGE (if applicable)
────────────────────────────────────
  * Header: "🐛 Pest / Disease Reference"
  * Horizontal scrollable pest images (if multiple)
  * Each image: 120dp × 120dp, rounded 8dp
  * Below each: Image caption (12sp, grey)
  * Note: "Images are for reference only" (italic, 11sp)

────────────────────────────────────
CARD 3: PESTICIDE RECOMMENDATION
────────────────────────────────────
  * Header: "🧪 Pesticide Recommendation / 
    ಕೀಟನಾಶಕ ಶಿಫಾರಸು" (green, bold, 16sp)
  * Orange left border (warning color)
  
  Table-style rows (alternating white/light grey):
  Row 1: "Pesticide Name" | "Imidacloprid 17.8 SL"
  Row 2: "Dosage" | "0.3 ml per litre of water"
  Row 3: "Spray Timing" | "Early morning or evening"
  Row 4: "Frequency" | "Once every 15 days"
  Row 5: "Available at" | "KVK / GKMS Stores"
  
  Each row: 
  * Left label: Grey #757575, 13sp, bold
  * Right value: Dark #212121, 14sp
  * 12dp vertical padding per row
  * 1dp grey divider between rows

────────────────────────────────────
CARD 4: FERTILIZER DETAILS
────────────────────────────────────
  * Header: "🌱 Fertilizer Details / ಗೊಬ್ಬರ ವಿವರ"
    (green, bold, 16sp)
  * Green left border
  
  Same table-style:
  Row 1: "Fertilizer Type" | "Urea + DAP mix"
  Row 2: "Quantity per Acre" | "50kg Urea + 25kg DAP"
  Row 3: "Application Method" | "Broadcast before rain"
  Row 4: "Stage" | "Tillering stage (20-25 days)"

────────────────────────────────────
CARD 5: PRECAUTIONS
────────────────────────────────────
  * Header: "⚠️ Precautions / ಮುನ್ನೆಚ್ಚರಿಕೆಗಳು"
    (orange-red #E65100, bold, 16sp)
  * Red-orange left border
  * Orange light background #FFF3E0
  
  Bullet point list:
  * Each point: Warning icon ⚠️ + text (14sp, dark)
  * 8dp between bullets
  Example bullets:
  - "Wear gloves and mask while spraying"
  - "Do not spray during strong wind"
  - "Keep children away from treated area"
  - "Store pesticides in locked, dry place"

────────────────────────────────────
CARD 6: EXPERT RECOMMENDATION
────────────────────────────────────
  * Header: "👨‍🔬 Expert Recommendation" (green, bold)
  * Blue left border (#1565C0)
  
  Expert profile mini-card:
  * Circular avatar (expert photo or default icon, 56dp)
  * Name: "Dr. Ramesh Kumar" (bold, 15sp)
  * Designation: "Agricultural Officer, Mysore KVK" (grey, 13sp)
  * Star rating: ⭐⭐⭐⭐⭐ (4.8)
  
  Quote block (light blue background, italic):
  "Based on field trials, this method showed 
  35% reduction in pest damage within 2 weeks..."
  
  * "Contact Expert" button (outlined, blue) - optional

────────────────────────────────────
RELATED TIPS SECTION:
────────────────────────────────────
  * Header: "Related Tips / ಸಂಬಂಧಿತ ಸಲಹೆಗಳು"
  * Horizontal scroll list (same mini-card style as dashboard)
  * 3-5 related tips based on same crop category

BOTTOM STICKY ACTION BAR:
  * White bar, elevation 12dp
  * Three equal buttons:
    [🔖 Save]  [📤 Share]  [🔊 Listen]
  * Each: Icon + label, green color
  * Save toggles bookmark state with animation

BEHAVIOR:
- Screen receives tipId from Dashboard intent
- Fetches full tip data from Firestore (or Room cache)
- Audio (TTS): Reads aloud the short advice + precautions
- Save: Stores in savedTips Firestore collection 
  AND saved_tips_local Room table
- Share: Opens Android Share Intent with 
  "Check this farming tip from Raitha-Varta: [title] [link]"
- Back: Returns to Dashboard maintaining scroll position
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 8 — ALL TIPS SCREEN (TIPS TAB)
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the All Tips browsing screen for Raitha-Varta.
This is accessed via the "Tips" bottom navigation tab.

LAYOUT:

TOP APP BAR:
  * Green background #2E7D32
  * Title: "ಎಲ್ಲಾ ಸಲಹೆಗಳು | All Tips" (white, 20sp, bold)
  * Right: Search icon (white magnifying glass)

SEARCH BAR (Expandable):
  * When search icon tapped: Full-width search bar appears
  * White background, grey hint "Search tips... / ಹುಡುಕಿ"
  * Magnifying glass icon on left, clear X on right
  * Real-time search filters the tip list

FILTER SECTION:
  * Two rows of filters:
  
  ROW 1 - Crop Category:
  Horizontal scrollable chips (same as dashboard):
  All | Paddy | Coconut | Areca Nut | Tomato | 
  Onion | Maize | Ragi | Chilli
  
  ROW 2 - Tip Type:
  Horizontal scrollable chips:
  All | Pest Control | Fertilizer | Irrigation | 
  Weather | Harvesting | Soil Management

TIPS LIST (Main Content):
  * Vertical RecyclerView
  * Pull-to-refresh enabled
  * Loading skeleton while fetching

Each tip list item (card):
┌─────────────────────────────────────────┐
│ [Crop Image]  │  Category chip (colored) │
│  120dp×120dp  │  "🌾 Paddy | Pest"       │
│  Left side    │                          │
│  Rounded 12dp │  Title (16sp, bold,      │
│               │   dark, 2 lines)         │
│               │                          │
│               │  Short advice preview    │
│               │  (13sp, grey, 2 lines)   │
│               │                          │
│               │  Date: "Jun 15, 2025"    │
│               │  (11sp, grey)            │
│               │                          │
│               │  [🔊 Audio] [🔖 Save]    │
│               │  (icon buttons, 11sp)    │
└─────────────────────────────────────────┘
* Card height: ~130dp
* Elevation: 4dp
* Margin: 8dp horizontal, 4dp vertical
* Rounded corners: 12dp
* White background

EMPTY STATE:
  * If no tips match filter: 
  * Centered illustration of empty clipboard
  * "No tips found for this category"
  * "ಈ ವರ್ಗದಲ್ಲಿ ಯಾವುದೇ ಸಲಹೆ ಇಲ್ಲ"
  * Green button: "Clear Filters"

LOADING STATE:
  * Skeleton placeholder cards (shimmer animation)
  * 5 skeleton cards visible

SORT OPTION:
  * Sort FAB (Floating Action Button) bottom-right
  * Options: Newest First | Oldest First | By Crop | 
    By Popularity

BEHAVIORS:
- Filters are combinable (Paddy + Pest Control = 
  paddy pest tips only)
- Search works across title and content
- Tips are paginated (20 per page, load more on scroll)
- Tap on card → Tip Detail Screen
- Save icon toggles directly in list (no need to open tip)
- Offline: Shows cached tips with "Offline mode" banner
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 9 — EXPERT ASK SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the Expert Ask AI screen for Raitha-Varta.
This is the AI-powered disease detection feature.

ACCESS: Via bottom nav "Expert Ask" tab (robot icon)

LAYOUT:

TOP APP BAR:
  * Green #2E7D32
  * Title: "Expert Ask / ತಜ್ಞರನ್ನು ಕೇಳಿ" (white, 20sp, bold)
  * Right: History icon (clock, white) 
    → opens past query history

MAIN CONTENT (Scrollable):

────────────────────────────────────
HERO SECTION:
────────────────────────────────────
  * Illustrated banner: 
    AI robot icon + magnifying glass over a leaf
    Light green background #E8F5E9
    "AI-Powered Crop Disease Detection" (18sp, bold, dark)
    "ಬೆಳೆ ರೋಗ ಪತ್ತೆ ತಂತ್ರಜ್ಞಾನ" (15sp, green)
    Height: 160dp, rounded 16dp

────────────────────────────────────
INSTRUCTIONS CARD:
────────────────────────────────────
  * White card, elevation 4dp, rounded 12dp
  * Header: "How to Use / ಹೇಗೆ ಬಳಸುವುದು" 
    (green, bold, 16sp)
  * 3 instruction steps in vertical list:
    Step 1: "📸 Take a clear photo of affected 
              leaf or plant"
    Step 2: "☁️ Upload the image"
    Step 3: "🤖 Get instant AI analysis 
              and treatment plan"
  Each step: Number circle (green) + text (14sp, dark)

────────────────────────────────────
IMAGE UPLOAD SECTION:
────────────────────────────────────
  * White card, elevation 6dp, rounded 16dp
  * Header: "Upload Diseased Leaf / 
    ರೋಗಪೀಡಿತ ಎಲೆ ಅಪ್ಲೋಡ್ ಮಾಡಿ" (green, bold, 16sp)
  
  IMAGE PREVIEW BOX:
  * Size: Full width, height 220dp
  * Default state (no image selected):
    - Dashed border (#2E7D32, 2dp, dash pattern)
    - Center: Camera icon (64dp, grey)
    - Text: "Tap to upload image"
    - Sub: "ಚಿತ್ರ ಅಪ್ಲೋಡ್ ಮಾಡಲು ಟ್ಯಾಪ್ ಮಾಡಿ" (grey)
    - Background: Light green #F1F8E9
  
  * After image selected:
    - Actual image displayed (full box, object-fit: cover)
    - Top-right: X button to remove image (white, 
      circular red background)
    - Bottom overlay: "Tap to change image" (white, 13sp)
  
  TWO UPLOAD BUTTONS (side by side):
  [📷 Use Camera]    [🖼️ Choose from Gallery]
  Each: Half width, height 48dp, rounded 24dp
  * Camera: Green filled button, white text
  * Gallery: Green outlined button, green text
  * Icons on left of text

  CROP TYPE DROPDOWN (below upload buttons):
  * "Select Crop Type / ಬೆಳೆ ಪ್ರಕಾರ ಆಯ್ಕೆ ಮಾಡಿ" 
    (required for better AI accuracy)
  * Same dropdown style as profile setup

────────────────────────────────────
DESCRIPTION FIELD (Optional):
────────────────────────────────────
  * Label: "Describe the problem (Optional)"
    "ಸಮಸ್ಯೆ ವಿವರಿಸಿ (ಐಚ್ಛಿಕ)"
  * Multi-line text input, 4 lines tall
  * Hint: "e.g., Yellow spots on leaves 
    since last week after rain..."
  * Character counter: 0/500
  * Rounded: 12dp border

────────────────────────────────────
ANALYZE BUTTON:
────────────────────────────────────
  * Full width, height 56dp
  * Background: Green gradient #43A047 → #1B5E20
  * Text: "🔍 Analyze Now / ವಿಶ್ಲೇಷಿಸಿ" 
    (white, bold, 18sp)
  * Rounded: 28dp
  * Active only when image is uploaded

────────────────────────────────────
LOADING STATE (After Analyze clicked):
────────────────────────────────────
  * Button shows loading animation
  * Below button: 
    Loading card appears:
    * Animated green circular progress
    * "AI is analyzing your crop..." (14sp, green)
    * "ನಿಮ್ಮ ಬೆಳೆಯನ್ನು ವಿಶ್ಲೇಷಿಸಲಾಗುತ್ತಿದೆ..." (14sp)
    * Animated dots

────────────────────────────────────
AI RESPONSE CARD (After analysis):
────────────────────────────────────
Slides up from bottom with animation.

Green header bar: "🤖 AI Analysis Result"

Section 1 — DISEASE DETECTED:
  * Large text: Disease name (20sp, bold, red #B71C1C)
  * Kannada name below (16sp, grey)
  * Confidence chip: "85% Confidence" (orange chip)
  * Disease description: 3-4 lines (14sp, dark)

Section 2 — SEVERITY INDICATOR:
  * Label: "Severity Level"
  * Color-coded bar: 
    Low (green) | Moderate (orange) | High (red)
  * Current level indicated with marker
  * Example: "Moderate - Early intervention recommended"

Section 3 — TREATMENT PLAN:
Header: "💊 Treatment Recommendation"
Numbered list of treatment steps:
  1. "Remove and destroy infected leaves immediately"
  2. "Apply Copper Oxychloride 50WP @ 3g/L water"
  3. "Spray in evening for best results"
  4. "Repeat after 10 days if symptoms persist"
Each step: Number circle + text (14sp)

Section 4 — PREVENTIVE MEASURES:
Header: "🛡️ Prevention"
Bullet list of prevention tips

Section 5 — BUY MEDICINE:
  * "Where to Get Treatment"
  * List of nearby agricultural stores 
    (based on farmer's district from profile)

DISCLAIMER (bottom of response):
  * Yellow info card:
  "⚠️ This is an AI-generated advisory. 
   Please consult your local Agricultural Officer 
   for confirmation before treatment."
  * Small text (12sp, dark yellow background)

SHARE RESPONSE BUTTON:
  * "Share Analysis / ವಿಶ್ಲೇಷಣೆ ಹಂಚಿಕೊಳ್ಳಿ"
  * Outlined green button, full width

SAVE RESPONSE BUTTON:
  * "Save to My Queries / ಉಳಿಸಿ"
  * Green filled button, full width

────────────────────────────────────
PAST QUERIES (Query History):
────────────────────────────────────
Section header: "My Previous Queries / 
ನನ್ನ ಹಿಂದಿನ ಪ್ರಶ್ನೆಗಳು"
"View All →" link

Horizontal scroll list of past query cards:
Each card (160dp × 180dp):
  * Thumbnail of uploaded image (top, 100dp)
  * Disease name detected (bold, 13sp)
  * Date (grey, 11sp)
  * Status chip: "Analyzed ✓" (green) or "Pending"

BEHAVIOR:
- Camera: Uses CameraX with front/back camera toggle
- Gallery: FileProvider intent to pick image
- Compress image before upload (max 800x800px, 500KB)
- Upload to Firebase Storage: 
  queries/{phoneNumber}/{timestamp}.jpg
- Call Cloud Function / Gemini API with image + crop type
- Display response in animated slide-up card
- Save query + response to Firestore expertQueries collection
- Offline: Show message "Internet required for AI analysis"
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 10 — SAVED TIPS SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the Saved Tips collection screen for Raitha-Varta.
Accessed via "Saved" bottom navigation tab.

LAYOUT:

TOP APP BAR:
  * Green #2E7D32
  * Left: Back arrow (if opened from notification) 
    or just title
  * Title: "ಉಳಿಸಿದ ಸಲಹೆಗಳು | Saved Tips" (white, 20sp, bold)
  * Right: Sort icon (white, 3-line sort icon)

STATS HEADER BAR (below app bar):
  * Light green background #E8F5E9
  * Row: "📚 24 Tips Saved | 🌾 5 Crops | 💧 3 Categories"
  * Each stat: Icon + number + label (13sp, dark green)

FILTER ROW:
  * Same chip-based horizontal filter as other screens
  * Filter saved tips by crop category

SAVED TIPS LIST:

TWO VIEW OPTIONS (toggle buttons top-right):
  * Grid view (2 columns) 
  * List view (1 column, larger cards)

GRID VIEW CARDS (Each card, half-screen width):
┌──────────────────┐
│  [Crop Image]    │
│   Full width     │
│   140dp height   │
│   Gradient over  │
│                  │
│ 🌾 Paddy (chip)  │
│                  │
│ Tip title        │
│ (13sp, bold,     │
│  2 lines)        │
│                  │
│ [Saved: Jun 15]  │
│ (11sp, grey)     │
│                  │
│ [🗑️ Remove]      │
│ (red icon, 11sp) │
└──────────────────┘
* Rounded: 12dp, elevation: 4dp, margin: 4dp

LIST VIEW CARDS (Full width):
Same as All Tips list but with "Remove bookmark" option.

EMPTY STATE:
  * Centered: Large bookmark outline icon (green, 80dp)
  * "No Saved Tips Yet"
  * "ಇನ್ನೂ ಯಾವುದೇ ಸಲಹೆ ಉಳಿಸಿಲ್ಲ"
  * "Save tips from the dashboard by tapping 🔖"
  * Green button: "Browse Tips →"

SWIPE TO DELETE:
  * Each list item can be swiped left to reveal 
    red "Remove" button
  * Swipe left → Red background reveals → 
    "Remove" label with trash icon
  * On tap "Remove" → Show confirmation dialog:
    "Remove from saved tips?"
    [Cancel] [Remove] buttons

BEHAVIORS:
- Load from Room Database saved_tips_local table 
  (works offline)
- Sync with Firestore savedTips on network available
- Tap card → Navigate to Tip Detail Screen
- Long-press card → Multi-select mode
  * Checkbox appears on each card
  * Bottom bar: "Delete Selected" button
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 11 — SUCCESS STORIES SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the Success Stories browsing screen 
for Raitha-Varta.

ACCESS: From Dashboard "View All" link OR 
         dedicated section in app.

LAYOUT:

TOP APP BAR:
  * Green #2E7D32
  * Back arrow (white, left)
  * Title: "ಯಶಸ್ಸಿನ ಕಥೆಗಳು | Success Stories" 
    (white, 20sp, bold)
  * Right: Filter icon (white, funnel icon)

HERO BANNER:
  * Full-width illustrated banner
  * Background: Golden-green gradient #F57F17 → #33691E
  * Icon: Trophy + farmer illustration
  * Text: "Real Farmers. Real Results." (white, 20sp, bold)
  * Sub: "ನೈಜ ರೈತರು. ನೈಜ ಫಲಿತಾಂಶಗಳು." (white, 15sp)
  * Height: 140dp, rounded bottom corners 20dp

FILTER CHIPS:
  Horizontal scroll: All | Paddy | Coconut | Areca Nut | 
  Tomato | Irrigation | Pest Control

STORIES LIST (Vertical RecyclerView):

Each Story Card (Full width card, elevation 6dp):
┌─────────────────────────────────────────────────┐
│  TOP ROW:                                        │
│  [Farmer Photo] [Farmer Name    ] [District  ]  │
│  Circular 64dp  Bold 16sp         Grey 13sp      │
│  with green     "Ravi Shankar"    "Hassan Dist." │
│  border         ⭐⭐⭐⭐⭐          Paddy Farmer  │
├─────────────────────────────────────────────────┤
│  CROP USED: "🌾 Paddy — Brown Plant Hopper Tip" │
│  (green chip, 12sp)                             │
├─────────────────────────────────────────────────┤
│  STORY TEXT (Kannada / English based on pref.): │
│  "After following the Raitha-Varta tip on       │
│  early morning spraying of Imidacloprid,        │
│  my paddy yield increased from 45 bags to       │
│  62 bags per acre. I saved ₹8,000 on crop       │
│  loss this season..."                           │
│  (14sp, dark, line height 22sp)                 │
│  "Read More →" link (green, 13sp)               │
├─────────────────────────────────────────────────┤
│  RESULTS SECTION (3 mini-stat boxes in a row):  │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐        │
│  │ Before   │ │ After    │ │ Savings  │        │
│  │ 45 bags  │ │ 62 bags  │ │ ₹8,000   │        │
│  │ /acre    │ │ /acre    │ │ saved    │        │
│  │ (grey)   │ │ (green,  │ │ (gold)   │        │
│  │          │ │  bold)   │ │          │        │
│  └──────────┘ └──────────┘ └──────────┘        │
├─────────────────────────────────────────────────┤
│  CROP IMAGE (Horizontal scroll if multiple):    │
│  Before image + After image side by side        │
│  Each: 140dp × 100dp, rounded 8dp              │
│  Labels: "Before" "After" (white text overlay)  │
├─────────────────────────────────────────────────┤
│  BOTTOM ROW:                                    │
│  "📅 June 2025" (grey, 12sp, left)              │
│  [📤 Share Story] (outlined green, right, 13sp) │
└─────────────────────────────────────────────────┘

Card margins: 16dp horizontal, 8dp vertical
Card corners: 16dp rounded
Card background: White

EMPTY STATE:
  * Trophy icon (grey, 80dp)
  * "No stories yet for this category"

STORY DETAIL (On "Read More" tap):
Opens a bottom sheet OR full screen:
  * Full story text
  * All images
  * Full stats
  * Video testimony (if available, YouTube embed)
  * Share button
  * "Try this tip" button (navigates to relevant tip)

BEHAVIORS:
- Fetch from Firestore successStories collection
- Filter by crop category
- Share: Shares story text + image via Android Share Intent
- "Try this tip" button links to the specific tip 
  mentioned in the story
- Stories paginated (10 per load)
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 12 — FARMER PROFILE SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the Farmer Profile screen for Raitha-Varta.
Accessed via "Profile" bottom navigation tab.

LAYOUT:

TOP SECTION (Green header, 200dp height):
  * Background: Green gradient #2E7D32 → #1B5E20
  * Center: 
    - Circular avatar (100dp diameter)
      Default: Farmer icon with green background
      Has camera overlay button (bottom-right, 28dp circle, 
      white background, camera icon, to change photo)
    - Name: "Ravi Shankar" (white, 22sp, bold)
    - Phone: "+91 99XXXXXX89" (white, 15sp, with ✓ verified)
    - Location chip: "📍 Hassan, Tumkur" 
      (white semi-transparent chip, 13sp)

PROFILE STATS ROW (White card, elevation 4dp, 
overlapping green header):
  * 3 stat columns separated by vertical dividers:
    Col 1: "28" (24sp, bold, green) / "Tips Saved" (12sp, grey)
    Col 2: "5" (24sp, bold, green) / "Crops" (12sp, grey)
    Col 3: "12" (24sp, bold, green) / "Queries" (12sp, grey)

CONTENT SECTIONS (White scrollable):

────────────────────────────────────
MY INFORMATION CARD:
────────────────────────────────────
  * Header row: "My Information" (bold, 16sp, dark) 
                + "Edit ✏️" (green link, right)
  
  Info rows (each 52dp height, with divider):
  Row: [Icon] [Label]        [Value]
  👤  Full Name              Ravi Shankar
  📱  Mobile                 +91 99XXXXXX89
  📍  District               Hassan
  🏘️  Taluk                 Alur
  🗣️  Language              ಕನ್ನಡ (Kannada)
  
  Icon: 24dp, green tint
  Label: 13sp, grey #757575
  Value: 14sp, dark #212121, right-aligned

────────────────────────────────────
MY CROPS CARD:
────────────────────────────────────
  * Header: "My Crops / ನನ್ನ ಬೆಳೆಗಳು" 
             + "Edit ✏️" (green link)
  * Grid of crop chips (same style as profile setup)
  * Currently selected crops shown in green filled state
  * "Add More" plus chip at end

────────────────────────────────────
PREFERENCES CARD:
────────────────────────────────────
  * Header: "Preferences / ಆದ್ಯತೆಗಳು"
  
  Settings rows with toggle switches:
  
  Row 1: [🔔 icon] "Daily Tip Notification" 
         [Toggle: ON/OFF]  ← Currently ON
  
  Row 2: [🌙 icon] "Evening Reminder (7 PM)"
         [Toggle: ON/OFF]
  
  Row 3: [🌐 icon] "App Language"
         [Dropdown: Kannada / English]
  
  Row 4: [📶 icon] "Offline Mode (Save tips locally)"
         [Toggle: ON/OFF]

────────────────────────────────────
SUPPORT SECTION:
────────────────────────────────────
  * "Help & Support / ಸಹಾಯ"
  List of tappable menu items:
  → "How to Use App" (FAQ)
  → "Contact Agricultural Helpline" 
    (opens phone dialer: 1800-XXX-XXXX)
  → "Rate This App" (opens Play Store)
  → "Privacy Policy"
  → "About Raitha-Varta"
  → "App Version: 1.0.0"

LOGOUT BUTTON:
  * Below support section, 16dp margin
  * Full width, 52dp height
  * Outlined red border, red text
  * "Logout / ಲಾಗ್ ಔಟ್" (bold, 16sp, red #D32F2F)
  * Left: Exit/door icon (red)
  * On tap: Confirmation dialog:
    "Are you sure you want to logout?"
    [Cancel] [Logout] 
    On Logout: Clear Firebase Auth, clear SharedPreferences,
    navigate to Language Selection Screen

EDIT PROFILE (When Edit tapped):
  * Inline editing mode OR opens edit bottom sheet
  * Pre-filled fields with current values
  * Save changes → Update Firestore + Room DB

BEHAVIORS:
- Profile data loaded from Room DB (instant) + 
  synced with Firestore
- All edits update both Firestore and Room DB
- Notification toggle: Register/unregister FCM topic
- Language change: Immediately apply, restart activity
```

---

## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
## SCREEN 13 — NOTIFICATION SCREEN
## ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

### UI Prompt:
```
Design the Notifications screen for Raitha-Varta.
Accessed via bell icon on Dashboard top bar.

LAYOUT:

TOP APP BAR:
  * Green
  * Back arrow
  * "Notifications / ಸೂಚನೆಗಳು" (white, bold)
  * Right: "Mark all read" link (white, 13sp)

NOTIFICATION LIST (Vertical scroll):

Each notification item:
  * Height: 72dp minimum (expandable)
  * Unread: Light green left border (4dp), 
    light green background #F1F8E9
  * Read: White background, no border

  Layout per item:
  [Category Icon] | [Title + Preview]    | [Time]
    48dp circular    Title: 14sp bold       12sp
    green bg          "New tip on Paddy     grey
    white icon         Pest Control"
                      Preview: 13sp grey
                      "Learn how to manage..."

Categories with different icons:
  🌾 Daily Tip → Green bg, crop icon
  ⚠️ Weather Alert → Orange bg, weather icon
  🤖 AI Response Ready → Blue bg, robot icon
  ✅ Expert Reply → Purple bg, expert icon
  📢 App Update → Grey bg, info icon

EMPTY STATE:
  * Bell outline icon (grey, 64dp)
  * "No notifications yet"
  * "ಇನ್ನೂ ಯಾವುದೇ ಸೂಚನೆ ಇಲ್ಲ"

BEHAVIORS:
- Tap on notification → Navigate to relevant screen
  (Daily tip → Dashboard, AI response → Expert Ask)
- Swipe left on notification → Delete
- FCM handles push notifications when app is in background
```

---

---

# SECTION 6 — SCREEN NAVIGATION & CONNECTION MAP

## 6.1 Complete Navigation Flow

```
══════════════════════════════════════════════════════════
                    APP LAUNCH
══════════════════════════════════════════════════════════
                        │
                        ▼
              ┌─────────────────┐
              │  SCREEN 1       │
              │  SPLASH SCREEN  │ (2.5 seconds)
              └────────┬────────┘
                       │
           Check SharedPreferences for language
           Check Firebase Auth for session
                       │
          ┌────────────┼────────────┐
          ▼            ▼            ▼
   No Language    Language Set,  Language Set,
   Preference     No Auth        Auth Exists
   Found          Session        (Returning User)
          │            │            │
          ▼            ▼            ▼
   SCREEN 2       SCREEN 3     Check Firestore
   Language       Phone Auth   for Profile
   Selection           │            │
          │            │     ┌──────┴──────┐
          ▼            ▼     ▼             ▼
   SCREEN 3       SCREEN 4  Profile      No Profile
   Phone Auth     OTP Verify Exists      (Edge case)
                       │         │             │
                       │         ▼             ▼
                       │   SCREEN 6        SCREEN 5
                       │   DASHBOARD       Profile Setup
                       │   (Load all            │
                       │    saved data)          │
                       │                        ▼
                       │                   SCREEN 6
                       │                   DASHBOARD
                       │
                  New User?
                  │       │
                  YES     NO
                  │       │
                  ▼       ▼
             SCREEN 5  SCREEN 6
             Profile   DASHBOARD
             Setup
                  │
                  ▼
             SCREEN 6
             DASHBOARD

══════════════════════════════════════════════════════════
              DASHBOARD NAVIGATION HUB
══════════════════════════════════════════════════════════

SCREEN 6 (DASHBOARD) connects to:
  │
  ├─── TAP on Flash Card ──────────────► SCREEN 7 (Tip Detail)
  │                                            │
  │                                            ├── Share → Android Share Intent
  │                                            ├── Save → Update DB + bookmark icon
  │                                            ├── Audio → TTS Playback
  │                                            └── Back → SCREEN 6
  │
  ├─── "See All Tips" link ────────────► SCREEN 8 (All Tips)
  │                                            │
  │                                            ├── Tap tip → SCREEN 7
  │                                            └── Back → SCREEN 6
  │
  ├─── "View All Stories" link ────────► SCREEN 11 (Success Stories)
  │                                            │
  │                                            ├── Tap story → Story Detail (Bottom Sheet)
  │                                            ├── "Try this tip" → SCREEN 7
  │                                            └── Back → SCREEN 6
  │
  ├─── Bell icon ──────────────────────► SCREEN 13 (Notifications)
  │                                            │
  │                                            ├── Tap notification → Relevant Screen
  │                                            └── Back → SCREEN 6
  │
  └─── Filter chips ───────────────────► Filter applied in place (same screen)

══════════════════════════════════════════════════════════
              BOTTOM NAVIGATION CONNECTIONS
══════════════════════════════════════════════════════════

BOTTOM NAV TAB 1: Home ──────────────► SCREEN 6 (Dashboard)
BOTTOM NAV TAB 2: Tips ──────────────► SCREEN 8 (All Tips)
BOTTOM NAV TAB 3: Expert Ask ────────► SCREEN 9 (Expert Ask)
BOTTOM NAV TAB 4: Saved ─────────────► SCREEN 10 (Saved Tips)
BOTTOM NAV TAB 5: Profile ───────────► SCREEN 12 (Profile)

══════════════════════════════════════════════════════════
              EXPERT ASK FLOW
══════════════════════════════════════════════════════════

SCREEN 9 (Expert Ask):
  │
  ├─── Camera button → CameraX → Image captured → Back to Screen 9
  ├─── Gallery button → FileProvider → Image selected → Back to Screen 9
  ├─── Analyze button → Upload to Firebase Storage
  │         │
  │         ▼
  │    Cloud Function triggered
  │         │
  │         ▼
  │    Gemini API called with image
  │         │
  │         ▼
  │    Response stored in Firestore
  │         │
  │         ▼
  │    AI Result displayed on SCREEN 9 (slide-up card)
  │
  ├─── History icon → Past queries list (inline section / new screen)
  └─── "Save Result" → Stored in expertQueries Firestore

══════════════════════════════════════════════════════════
              PROFILE EDIT FLOW
══════════════════════════════════════════════════════════

SCREEN 12 (Profile):
  │
  ├─── Edit My Information → Edit mode → Save → Update Firestore + Room
  ├─── Edit My Crops → Crop selection (same as setup) → Save
  ├─── Language toggle → Restart with new language
  ├─── Notification toggle → FCM subscribe/unsubscribe
  └─── Logout → Confirmation → Clear auth → SCREEN 2 (Language Selection)

══════════════════════════════════════════════════════════
              RETURNING USER COMPLETE FLOW
══════════════════════════════════════════════════════════

App Launch → Splash → Language set ✓ → Auth session exists ✓
     │
     ▼
Firebase Auth auto-signs in (no OTP needed)
     │
     ▼
Firestore fetches farmer profile by phoneNumber
     │
     ▼
Room DB checked → Last sync timestamp compared
     │
     ▼
If online: Sync new tips from Firestore → Update Room DB
If offline: Load all data from Room DB cache
     │
     ▼
Dashboard opens with:
  ✓ Farmer's name in greeting
  ✓ Today's tips based on their crop interests
  ✓ Their saved tips restored
  ✓ Their district-based weather data
  ✓ Their previously uploaded queries in Expert Ask
```

## 6.2 Screen Index Summary

| Screen # | Screen Name | Navigation Entry | Navigation Exit |
|---|---|---|---|
| 1 | Splash Screen | App Launch | → Screen 2 or 3 or 6 |
| 2 | Language Selection | First Launch / Logout | → Screen 3 |
| 3 | Phone Number Entry | Screen 2 / Splash | → Screen 4 |
| 4 | OTP Verification | Screen 3 | → Screen 5 (new) or 6 (existing) |
| 5 | Profile Setup | Screen 4 (new user only) | → Screen 6 |
| 6 | Dashboard (Home) | Auth success / Bottom Nav | → 7, 8, 9, 10, 11, 12, 13 |
| 7 | Tip Detail | Screen 6, 8, 10 | → Back to origin screen |
| 8 | All Tips | Bottom Nav Tab 2 | → Screen 7 |
| 9 | Expert Ask | Bottom Nav Tab 3 | → In-screen result |
| 10 | Saved Tips | Bottom Nav Tab 4 | → Screen 7 |
| 11 | Success Stories | Screen 6 "View All" | → Story Detail / Screen 7 |
| 12 | Profile | Bottom Nav Tab 5 | → Screen 2 (logout) |
| 13 | Notifications | Screen 6 bell icon | → Relevant screens |

---

---

# SECTION 7 — BACKEND & API WORKFLOW

## 7.1 Authentication Workflow

```
STEP 1: Farmer enters phone number on Screen 3
        ↓
STEP 2: App calls Firebase Auth: 
        auth.verifyPhoneNumber(phoneNumber, timeout, activity, callbacks)
        ↓
STEP 3: Firebase sends 6-digit OTP via SMS
        ↓
STEP 4: Farmer enters OTP on Screen 4
        ↓
STEP 5: App creates PhoneAuthCredential:
        PhoneAuthProvider.getCredential(verificationId, smsCode)
        ↓
STEP 6: App signs in:
        auth.signInWithCredential(credential)
        ↓
STEP 7: On success → Get uid from Firebase Auth
        ↓
STEP 8: Query Firestore: 
        db.collection("farmers").document(phoneNumber).get()
        ↓
STEP 9a: Document exists → Load profile → Go to Dashboard
STEP 9b: Document not exists → Go to Profile Setup
```

## 7.2 Tips Loading Workflow

```
STEP 1: Dashboard opened
        ↓
STEP 2: Check Room DB for cached tips (show immediately)
        ↓
STEP 3: If online, query Firestore:
        db.collection("tips")
          .whereArrayContains("cropCategory", farmerCrops)
          .orderBy("createdAt", DESCENDING)
          .limit(20)
          .get()
        ↓
STEP 4: Map Firestore documents to TipModel data class
        ↓
STEP 5: Save new tips to Room DB (upsert operation)
        ↓
STEP 6: Update LiveData/StateFlow → UI updates
        ↓
STEP 7: Filter applied → Query Room DB for filtered tips
```

## 7.3 Expert Ask AI Workflow

```
STEP 1: Farmer selects/captures leaf image
        ↓
STEP 2: Compress image (Bitmap → JPEG, max 800×800px)
        ↓
STEP 3: Upload to Firebase Storage:
        storage.ref("queries/{phone}/{timestamp}.jpg")
        .putFile(imageUri)
        ↓
STEP 4: Get download URL from Firebase Storage
        ↓
STEP 5: Call Firebase Cloud Function:
        POST /analyzeLeafImage
        Body: { imageUrl, cropType, farmerPhone, description }
        ↓
STEP 6: Cloud Function (Node.js):
        - Download image from Storage
        - Call Gemini API:
          model.generateContent([
            "You are an agricultural expert. 
             Analyze this {cropType} leaf image. 
             Identify any disease, pest damage, 
             or deficiency. Provide:
             1. Disease/Issue name
             2. Severity (Low/Moderate/High)
             3. Step-by-step treatment
             4. Prevention measures
             5. Pesticide/medicine recommendation with dosage
             Respond in {language} language.",
            imagePart
          ])
        ↓
STEP 7: Parse Gemini response
        ↓
STEP 8: Store in Firestore expertQueries collection
        ↓
STEP 9: Return JSON response to Android app
        ↓
STEP 10: Display AI result in slide-up card on Screen 9
```

## 7.4 Save Tip Workflow

```
Farmer taps 🔖 Save on any tip card:
        ↓
STEP 1: Toggle bookmark state in UI (instant)
        ↓
STEP 2: Insert tip to Room DB saved_tips_local (instant, offline-safe)
        ↓
STEP 3: If online, write to Firestore:
        db.collection("savedTips")
          .document(phoneNumber)
          .collection("tips")
          .document(tipId)
          .set(tipSnapshot)
        ↓
STEP 4: If offline, mark for sync → Sync when online
        ↓
STEP 5: Show brief "Saved! ✓" toast/snackbar
```

## 7.5 Weather API Integration

```
API: OpenWeatherMap Current Weather API
Endpoint: GET https://api.openweathermap.org/data/2.5/weather
Params: q={district},{state},IN&appid={API_KEY}&units=metric

Call frequency: Every 3 hours (cached in Room)

Response mapping:
{
  "weather": [{"main": "Rain", "description": "moderate rain"}],
  "main": {"temp": 28, "humidity": 70},
  "name": "Mysore"
}

Used for:
1. Weather banner on Dashboard
2. Weather-tagged tip filtering 
   (show rain tips when rain expected)
3. Weather advisory generation via Gemini
```

---

---

# SECTION 8 — AI INTEGRATION DETAILS

## 8.1 Gemini API Configuration

```kotlin
// Gradle dependency
implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

// Initialize
val generativeModel = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = BuildConfig.GEMINI_API_KEY,
    generationConfig = generationConfig {
        temperature = 0.7f
        topP = 0.8f
        maxOutputTokens = 1024
    }
)
```

## 8.2 Prompt Templates

### Disease Detection Prompt:
```
You are an expert agricultural scientist and plant 
pathologist for Karnataka, India.

Analyze the provided {cropType} plant/leaf image carefully.

Respond ONLY in {language} (Kannada or English).

Provide your analysis in this exact JSON format:
{
  "diseaseDetected": "Disease name in {language}",
  "confidence": 85,
  "severity": "Moderate",
  "description": "2-3 sentence explanation",
  "treatmentSteps": [
    "Step 1...",
    "Step 2...",
    "Step 3..."
  ],
  "pesticideRecommendation": {
    "name": "Product name",
    "dosage": "Amount per litre",
    "timing": "When to spray",
    "frequency": "How often"
  },
  "preventiveMeasures": [
    "Measure 1...",
    "Measure 2..."
  ],
  "disclaimer": "Standard disclaimer text"
}

If the image is not clear or not a plant image, 
respond with diseaseDetected: "Unable to analyze" 
and confidence: 0.
```

### Tip Summarization Prompt:
```
You are an agricultural extension officer in Karnataka.

Convert the following technical agricultural advisory 
into a simple 3-4 line flash card tip for a farmer.

Original advisory: {rawAdvisoryText}
Crop: {cropName}
Target audience: Small-scale farmers with basic literacy

Rules:
1. Use simple, direct language
2. Give ONE specific actionable instruction
3. Include specific product names and dosages if applicable
4. Maximum 60 words
5. Language: {language}
6. Start with an action verb

Output format:
{
  "title": "Short title (5-7 words)",
  "shortAdvice": "The 3-4 line tip",
  "weatherCondition": "rain|sunny|cloudy|any",
  "subCategory": "pest|fertilizer|irrigation|general"
}
```

---

---

# SECTION 9 — SECURITY & AUTHENTICATION

## 9.1 Firebase Security Rules

### Firestore Rules:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Farmers can only read/write their own profile
    match /farmers/{phoneNumber} {
      allow read, write: if request.auth != null 
        && request.auth.token.phone_number == phoneNumber;
    }
    
    // Anyone authenticated can read tips
    match /tips/{tipId} {
      allow read: if request.auth != null;
      allow write: if false; // Only admin/cloud functions
    }
    
    // Farmers can only access their own saved tips
    match /savedTips/{phoneNumber}/tips/{tipId} {
      allow read, write: if request.auth != null 
        && request.auth.token.phone_number == phoneNumber;
    }
    
    // Anyone authenticated can read success stories
    match /successStories/{storyId} {
      allow read: if request.auth != null;
      allow write: if false;
    }
    
    // Farmers can read/write their own queries
    match /expertQueries/{queryId} {
      allow read: if request.auth != null 
        && resource.data.farmerPhone == 
           request.auth.token.phone_number;
      allow create: if request.auth != null;
    }
  }
}
```

### Firebase Storage Rules:
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /queries/{phoneNumber}/{fileName} {
      allow read, write: if request.auth != null 
        && request.auth.token.phone_number == phoneNumber;
    }
    
    match /tips/{fileName} {
      allow read: if request.auth != null;
    }
  }
}
```

## 9.2 API Key Security
- Gemini API key stored in `local.properties` (gitignored)
- Accessed via `BuildConfig.GEMINI_API_KEY`
- Firebase config stored in `google-services.json`
- All API calls go through Cloud Functions (key not exposed to client)
- ProGuard enabled for release builds to obfuscate code

---

---

# SECTION 10 — OFFLINE FUNCTIONALITY

## 10.1 What Works Offline

| Feature | Offline Behavior |
|---|---|
| View cached tips | ✅ Full functionality from Room DB |
| View saved tips | ✅ Full functionality from Room DB |
| View farmer profile | ✅ From Room DB |
| Read tip details | ✅ If cached |
| Audio TTS | ✅ Android TTS works offline |
| Language switch | ✅ Local preference |
| Expert Ask | ❌ Requires internet (shows message) |
| New tip sync | ❌ Queued for when online |
| OTP Login | ❌ Requires internet |

## 10.2 Sync Strategy
```
ON APP FOREGROUND (network available):
  1. Sync farmer profile (Firestore → Room)
  2. Fetch new tips (last 7 days → Room)
  3. Upload any pending saved tip changes
  4. Update weather data

ON APP BACKGROUND:
  FCM push notification triggers:
  - Download new daily tip
  - Cache in Room DB for offline access

CONFLICT RESOLUTION:
  - Local changes (save/unsave) take priority
  - Timestamp-based merge for profile edits
  - Server-wins for tip content
```

---

---

# SECTION 11 — TESTING PLAN

## 11.1 Unit Tests

| Test | Target | Expected Result |
|---|---|---|
| Phone validation | Input "99XXXXXX89" | Returns valid |
| Phone validation | Input "1234" | Returns invalid |
| Language preference | Set "kn" | All strings in Kannada |
| Room DB insert | Insert tip | Retrieve same tip |
| OTP format | 6 digits | Valid |
| Profile save | All fields filled | Success |
| Filter chips | Select "Paddy" | Only paddy tips shown |
| Save tip | Tap bookmark | Saved to DB + Firestore |

## 11.2 Integration Tests

| Test | Scenario | Expected |
|---|---|---|
| Full auth flow | New user OTP → Profile → Dashboard | Smooth navigation |
| Returning user | Existing phone OTP | Opens dashboard with saved data |
| Offline mode | Disable WiFi → Open app | Shows cached data |
| Expert Ask | Upload real leaf photo | AI response in < 30 seconds |
| Share tip | Tap share | Android share sheet opens with content |
| Logout + Login | Logout then re-login same number | All data restored |

## 11.3 UI Tests (Espresso)
- Test card swiping (ViewPager2)
- Test filter chip selection and deselection
- Test form validation error states
- Test bottom navigation switching
- Test language switch and text update

---

---

# SECTION 12 — DEPLOYMENT PLAN

## 12.1 Build Configuration

```gradle
android {
    compileSdk 34
    
    defaultConfig {
        applicationId "com.raithavarta.app"
        minSdk 24          // Android 7.0
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
        
        buildConfigField "String", "GEMINI_API_KEY", 
                         "\"${localProperties['gemini.api.key']}\""
        buildConfigField "String", "WEATHER_API_KEY", 
                         "\"${localProperties['weather.api.key']}\""
    }
    
    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile(
                'proguard-android-optimize.txt'), 
                'proguard-rules.pro'
            signingConfig signingConfigs.release
        }
    }
    
    buildFeatures {
        compose true
        viewBinding true
        buildConfig true
    }
}
```

## 12.2 Required Permissions

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32"/>
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES"/>
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

## 12.3 Dependencies (build.gradle)

```gradle
dependencies {
    // Kotlin & Coroutines
    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.9.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    
    // Jetpack Compose
    implementation platform("androidx.compose:compose-bom:2024.06.00")
    implementation "androidx.compose.ui:ui"
    implementation "androidx.compose.material3:material3"
    implementation "androidx.compose.ui:ui-tooling-preview"
    
    // Navigation
    implementation "androidx.navigation:navigation-compose:2.7.7"
    
    // ViewPager2 (Flash Cards)
    implementation "androidx.viewpager2:viewpager2:1.1.0"
    
    // Room Database
    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.room:room-ktx:2.6.1"
    kapt "androidx.room:room-compiler:2.6.1"
    
    // Hilt (Dependency Injection)
    implementation "com.google.dagger:hilt-android:2.51"
    kapt "com.google.dagger:hilt-compiler:2.51"
    
    // Firebase
    implementation platform("com.google.firebase:firebase-bom:33.0.0")
    implementation "com.google.firebase:firebase-auth-ktx"
    implementation "com.google.firebase:firebase-firestore-ktx"
    implementation "com.google.firebase:firebase-storage-ktx"
    implementation "com.google.firebase:firebase-messaging-ktx"
    implementation "com.google.firebase:firebase-functions-ktx"
    
    // Glide (Image Loading)
    implementation "com.github.bumptech.glide:glide:4.16.0"
    kapt "com.github.bumptech.glide:compiler:4.16.0"
    
    // Retrofit (HTTP Calls)
    implementation "com.squareup.retrofit2:retrofit:2.11.0"
    implementation "com.squareup.retrofit2:converter-gson:2.11.0"
    implementation "com.squareup.okhttp3:logging-interceptor:4.12.0"
    
    // Gemini AI
    implementation "com.google.ai.client.generativeai:generativeai:0.9.0"
    
    // CameraX
    implementation "androidx.camera:camera-camera2:1.3.3"
    implementation "androidx.camera:camera-lifecycle:1.3.3"
    implementation "androidx.camera:camera-view:1.3.3"
    
    // ViewModel & LiveData
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.8.2"
    
    // DataStore (Preferences)
    implementation "androidx.datastore:datastore-preferences:1.1.1"
    
    // Shimmer Loading Effect
    implementation "com.facebook.shimmer:shimmer:0.5.0"
    
    // Testing
    testImplementation "junit:junit:4.13.2"
    androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
    androidTestImplementation "androidx.test.ext:junit:1.2.1"
}
```

---

---

# SECTION 13 — APPENDIX

## 13.1 Kannada String Translations Reference

| English | Kannada |
|---|---|
| Good Morning | ಶುಭೋದಯ |
| Good Afternoon | ಶುಭ ಮಧ್ಯಾಹ್ನ |
| Good Evening | ಶುಭ ಸಂಜೆ |
| Today's Tip | ಇಂದಿನ ಸಲಹೆ |
| Daily Farming Tip | ದೈನಂದಿನ ಕೃಷಿ ಸಲಹೆ |
| Save | ಉಳಿಸಿ |
| Share | ಹಂಚಿಕೊಳ್ಳಿ |
| Listen | ಕೇಳಿ |
| Success Stories | ಯಶಸ್ಸಿನ ಕಥೆಗಳು |
| Expert Ask | ತಜ್ಞರನ್ನು ಕೇಳಿ |
| Pest Control | ಕೀಟ ನಿಯಂತ್ರಣ |
| Fertilizer | ಗೊಬ್ಬರ |
| Irrigation | ನೀರಾವರಿ |
| Weather | ಹವಾಮಾನ |
| Profile | ಪ್ರೊಫೈಲ್ |
| Logout | ಲಾಗ್ ಔಟ್ |
| Send OTP | OTP ಕಳುಹಿಸಿ |
| Verify | ಪರಿಶೀಲಿಸಿ |
| Disease Detected | ರೋಗ ಪತ್ತೆಯಾಗಿದೆ |
| Treatment | ಚಿಕಿತ್ಸೆ |
| Precautions | ಮುನ್ನೆಚ್ಚರಿಕೆಗಳು |

## 13.2 Color Palette Reference

| Color Name | Hex Code | Usage |
|---|---|---|
| Primary Green | #2E7D32 | App bars, buttons, active elements |
| Light Green | #43A047 | Gradient, hover states |
| Dark Green | #1B5E20 | Gradient bottom, deep elements |
| Background Green | #E8F5E9 | Light backgrounds, chips |
| Surface Green | #F1F8E9 | Page backgrounds |
| Gold | #FFD700 | Logo border, stars, highlights |
| White | #FFFFFF | Cards, text on dark |
| Dark Text | #212121 | Primary text |
| Grey Text | #757575 | Secondary text, labels |
| Light Grey | #BDBDBD | Borders, disabled states |
| Background | #FAFAFA | Screen background |
| Warning Orange | #E65100 | Precautions, warnings |
| Info Blue | #1565C0 | Expert section, weather |
| Error Red | #D32F2F | Errors, delete, logout |

## 13.3 Typography Scale

| Style | Size | Weight | Usage |
|---|---|---|---|
| H1 | 28sp | Bold | Screen titles |
| H2 | 22sp | Bold | Section headers |
| H3 | 18sp | Bold | Card titles |
| Body1 | 16sp | Regular | Main content |
| Body2 | 14sp | Regular | Secondary content |
| Caption | 13sp | Regular | Labels, hints |
| Small | 12sp | Regular | Timestamps, metadata |
| Tiny | 11sp | Regular | Footnotes |

Primary Font: **Poppins** (Google Fonts)
Kannada Font: **Noto Sans Kannada** (Google Fonts)

---

## 13.4 Folder Structure

```
RaithaVarta/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/raithavarta/app/
│   │   │   │   ├── data/
│   │   │   │   │   ├── local/
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   │   ├── TipDao.kt
│   │   │   │   │   │   │   ├── FarmerDao.kt
│   │   │   │   │   │   │   └── SavedTipDao.kt
│   │   │   │   │   │   ├── entity/
│   │   │   │   │   │   │   ├── TipEntity.kt
│   │   │   │   │   │   │   └── FarmerEntity.kt
│   │   │   │   │   │   └── RaithaVartaDatabase.kt
│   │   │   │   │   ├── remote/
│   │   │   │   │   │   ├── FirestoreService.kt
│   │   │   │   │   │   ├── WeatherApiService.kt
│   │   │   │   │   │   └── GeminiService.kt
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── TipRepository.kt
│   │   │   │   │   │   ├── FarmerRepository.kt
│   │   │   │   │   │   └── ExpertQueryRepository.kt
│   │   │   │   │   └── model/
│   │   │   │   │       ├── TipModel.kt
│   │   │   │   │       ├── FarmerModel.kt
│   │   │   │   │       ├── StoryModel.kt
│   │   │   │   │       └── ExpertQueryModel.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── splash/
│   │   │   │   │   │   └── SplashActivity.kt
│   │   │   │   │   ├── auth/
│   │   │   │   │   │   ├── LanguageSelectionFragment.kt
│   │   │   │   │   │   ├── PhoneAuthFragment.kt
│   │   │   │   │   │   └── OtpVerificationFragment.kt
│   │   │   │   │   ├── profile/
│   │   │   │   │   │   ├── ProfileSetupFragment.kt
│   │   │   │   │   │   └── ProfileFragment.kt
│   │   │   │   │   ├── dashboard/
│   │   │   │   │   │   ├── DashboardFragment.kt
│   │   │   │   │   │   ├── TipFlashCardAdapter.kt
│   │   │   │   │   │   └── DashboardViewModel.kt
│   │   │   │   │   ├── tips/
│   │   │   │   │   │   ├── AllTipsFragment.kt
│   │   │   │   │   │   ├── TipDetailFragment.kt
│   │   │   │   │   │   └── TipsViewModel.kt
│   │   │   │   │   ├── expertask/
│   │   │   │   │   │   ├── ExpertAskFragment.kt
│   │   │   │   │   │   └── ExpertAskViewModel.kt
│   │   │   │   │   ├── saved/
│   │   │   │   │   │   └── SavedTipsFragment.kt
│   │   │   │   │   ├── stories/
│   │   │   │   │   │   ├── SuccessStoriesFragment.kt
│   │   │   │   │   │   └── StoriesViewModel.kt
│   │   │   │   │   └── notifications/
│   │   │   │   │       └── NotificationsFragment.kt
│   │   │   │   ├── di/
│   │   │   │   │   ├── AppModule.kt
│   │   │   │   │   └── DatabaseModule.kt
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Constants.kt
│   │   │   │   │   ├── Extensions.kt
│   │   │   │   │   ├── NetworkUtils.kt
│   │   │   │   │   └── ImageUtils.kt
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml (English)
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   └── values-kn/
│   │   │   │       └── strings.xml (Kannada)
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   │       └── (Unit test files)
│   └── build.gradle
├── functions/ (Firebase Cloud Functions)
│   ├── index.js
│   └── package.json
├── local.properties (gitignored, API keys)
├── google-services.json
└── build.gradle (project level)
```

---

## 13.5 Step-by-Step Developer Onboarding

```
SETUP STEPS FOR DEVELOPER:

1. Clone repository from GitHub
   git clone https://github.com/[repo]/raitha-varta.git

2. Open in Android Studio (Hedgehog or later)

3. Create Firebase project at console.firebase.google.com
   - Enable Phone Authentication
   - Create Firestore Database (start in test mode)
   - Enable Firebase Storage
   - Enable Firebase Cloud Functions
   - Download google-services.json → place in /app folder

4. Create local.properties (gitignored):
   gemini.api.key=YOUR_GEMINI_API_KEY_HERE
   weather.api.key=YOUR_OPENWEATHERMAP_KEY_HERE

5. Get Gemini API key: aistudio.google.com

6. Get OpenWeatherMap API key: openweathermap.org/api

7. Set up Firestore collections:
   - Create "tips" collection with sample documents
   - Create "successStories" collection with sample data

8. Deploy Cloud Functions:
   cd functions
   npm install
   firebase deploy --only functions

9. Sync Gradle → Build project → Run on device/emulator

10. Test with a real phone number (OTP works on real device)
    For emulator: Use Firebase Auth test numbers
```

---

> **END OF STANDARD OPERATING PROCEDURE DOCUMENT**
>
> **Document**: Raitha-Varta Full SOP v1.0
> **Student**: K Manjushree | USN: 1GD22CS018
> **College**: Gopalan College of Engineering and Management
> **Subject**: Android App Development Using Generative AI
>
> *This SOP contains all information required for a developer or AI code generation tool to build the complete Raitha-Varta application from scratch, including all 13 screens with detailed UI prompts, database schema, API workflows, navigation connections, security rules, testing plan, and deployment instructions.*