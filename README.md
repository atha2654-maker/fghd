# Vande MUN Connect (Android Studio + Kotlin + Jetpack Compose)

A complete, MVVM-structured Android Studio project scaffold for a modern MUN community app with Firebase backend integration.

## Tech Stack
- Kotlin + Jetpack Compose (Material 3)
- MVVM architecture
- Firebase Authentication, Firestore, Realtime Database, Storage, Cloud Messaging
- Hilt DI
- Apache POI for Excel (`.xlsx`) generation
- Agora + WebRTC dependencies for conference/call integration

## Project Structure

```text
VandeMUNConnect/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/vandemunconnect/
│       │   ├── MainActivity.kt
│       │   ├── VandeMUNApplication.kt
│       │   ├── data/
│       │   │   ├── model/Models.kt
│       │   │   └── repository/*.kt
│       │   ├── di/AppModule.kt
│       │   ├── service/*.kt
│       │   ├── ui/
│       │   │   ├── navigation/*.kt
│       │   │   ├── screens/*.kt
│       │   │   ├── theme/*.kt
│       │   │   └── viewmodel/*.kt
│       │   └── util/ExcelExporter.kt
│       └── res/
├── firebase-firestore.rules
├── firebase-storage.rules
├── firebase-database.rules.json
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Included Feature Coverage

### Authentication
- Login screen supporting phone OTP + email code entry flow
- Direct verification screen for code confirmation
- Redirect to dashboard after verification

### Profile System
- `UserProfile` includes:
  - profile photo URL
  - name
  - portfolio
  - committee
  - contact
  - bio
  - WhatsApp delegation input
  - admin badge flag
  - owner badge flag
- Firebase Storage upload helper in `ProfileRepository`

### Chat System
- 1-to-1 and public room ready data model (`ChatMessage`)
- Text and voice-message URL fields
- Top-right call actions in UI
- Voice signal channel bridge via Realtime Database

### Conference System
- `ConferenceManager` abstraction with Agora + WebRTC setup points
- Prepared flow for:
  - public conference
  - individual conference
  - group conference
  - screen sharing / whiteboard (to be wired via provider SDK views)

### Excel Auto System
- `ExcelExporter.exportDelegates(...)` creates ordered `.xlsx` file
- Includes name, portfolio, committee
- Intended to be used behind admin-only UI action

### Bottom Tabs
- Home / Chat / Events / Planner / Profile

### Events
- Event entity includes name, location, date, fee, enrollment link, prize, past-event flag
- Enrollment action button in events tab UI scaffold

### Planner
- Reminder model + repository
- Worker scaffold for notifications

### UI Requirements
- Compose Material 3 modern UI scaffold
- Light/Dark mode supported
- Custom app icon resources included
- MUN-themed palette

## Firebase Setup Instructions
1. Create Firebase project and Android app with package name:
   - `com.vandemunconnect`
2. Download `google-services.json` into:
   - `app/google-services.json`
   - Note: project sync/build works without this file, but Firebase runtime features require it.
3. Enable services:
   - Authentication: Phone + Email/Password (or Email Link/OOB strategy)
   - Firestore
   - Realtime Database
   - Storage
   - Cloud Messaging
4. Publish rules from included files:
   - `firebase-firestore.rules`
   - `firebase-storage.rules`
   - `firebase-database.rules.json`
5. Add custom claims for admins (recommended):
   - `admin: true` for excel/events management

## API Integration Instructions (WebRTC / Agora)

### Agora
1. Create Agora project and get `APP_ID`.
2. Store in secure config (BuildConfig field or encrypted remote config).
3. Initialize `RtcEngine` and pass into `ConferenceManager.initializeAgora(...)`.
4. Use `startVoiceCall(channelId)` and add video setup via Agora callbacks.

### WebRTC
1. Use your signaling server + TURN/STUN.
2. Build `PeerConnectionFactory`, pass to `initializeWebRtc(...)`.
3. Exchange SDP/ICE via Firebase Realtime Database or your server.
4. For screen share, use foreground service + `MediaProjection` integration.



## Code-only PR Note
- This repository can be kept code-only for pull requests (no committed binaries).
- If `gradle/wrapper/gradle-wrapper.jar` is missing locally, regenerate wrapper files with:
  - `gradle wrapper --gradle-version 8.14.3`

## Build Instructions (Android Studio)
1. Open project in latest stable Android Studio.
2. Let Gradle sync.
3. Add `local.properties` if needed with SDK path.
4. (Recommended) Add Firebase `google-services.json` for full Firebase runtime configuration.
5. Build debug APK:
   - **Build > Build Bundle(s) / APK(s) > Build APK(s)**

## Steps to Build Signed APK
1. In Android Studio: **Build > Generate Signed Bundle / APK**
2. Select **APK**
3. Create/select keystore
4. Choose `release` build type
5. Finish wizard to generate signed APK

## Final APK Build Path
- Debug APK:
  - `app/build/outputs/apk/debug/app-debug.apk`
- Release APK:
  - `app/build/outputs/apk/release/app-release.apk`

> Note: actual APK files are generated after running the corresponding build task locally in Android Studio/CLI.


## Java Fresh App Module (No deletion of existing Kotlin app)
- Added a **new** Java module: `:app_java` (existing Kotlin `:app` kept unchanged).
- Open in Android Studio and select run configuration for `app_java`.
- Entry point: `com.vandemunconnect.javaapp.ui.MainActivity`.
- This Java module includes: login/verification flow, dashboard with Home/Chat/Events/Planner/Profile tabs, local interactive forms/lists, and Firebase dependencies scaffold.

