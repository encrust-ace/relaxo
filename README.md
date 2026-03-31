# Relaxo

Relaxo is an offline ambient sound app for sleep, focus, meditation, and daily relaxation. It lets users play calming nature sounds, combine multiple sound layers, adjust the volume of each layer independently, and set a sleep timer so playback stops automatically.

The app is intentionally simple. There is no sign-in, no account creation, no subscription flow, and no cloud sync. Users open the app, choose the sounds they want, and start listening immediately.

## Product Overview

Relaxo is designed for users who want a lightweight sound mixer instead of a large streaming app.

Core use cases:

- Falling asleep with rain-based ambience
- Building a quiet focus environment for work or study
- Supporting meditation, breathing, or screen-free breaks
- Running short timed sessions before sleep

Platform details:

- Built with Flutter for Android
- Uses bundled local audio files for playback
- Supports foreground playback through an Android service

## Main Features

- Ambient nature sounds included in the app
- Rain
- Rain on tent
- Birds
- Thunder
- Rain on grass
- Play and pause control for each sound separately
- Independent volume control for every sound layer
- Layered playback so users can create their own mix
- Sleep timer presets for 10, 20, and 30 minutes
- Ongoing playback notification while audio is active
- Offline use without requiring internet streaming

## How The App Works

Relaxo loads audio from local app assets and controls playback through Android native audio APIs connected to Flutter with method channels. When audio is playing and the app moves to the background, an Android foreground service keeps playback stable and shows an ongoing notification.

Relaxo does not require a user account and does not depend on a remote server for core playback.

## Privacy Policy

Effective date: 2026-03-31

This Privacy Policy describes how Relaxo handles information when users install and use the app.

### 1. Summary

Relaxo is built to function without collecting personal user data directly.

At the time of this policy:

- Relaxo does not require account registration
- Relaxo does not collect personal profile information directly
- Relaxo does not include advertising SDKs
- Relaxo does not include third-party analytics SDKs
- Relaxo does not sell user data

### 2. Information Relaxo Collects

Relaxo does not directly collect or store personal information such as:

- Full name
- Email address
- Phone number
- Postal address
- Contacts
- Photos or videos
- Messages
- Precise location
- Financial or payment card data
- Health or fitness records

Relaxo also does not create a personal user account database.

### 3. Information Processed On Device

To provide the core app experience, Relaxo processes certain temporary app state locally on the device, such as:

- Which sound channels are currently playing
- The volume level selected for a sound channel during use
- The currently active timer value during a session

This information is used only to operate the app on the device and is not transmitted by the app to a developer-owned backend service.

### 4. Permissions And Device Access

Relaxo currently declares the Android permission below:

- `android.permission.FOREGROUND_SERVICE`

Why this permission is used:

- To keep audio playback running reliably while the app is in the background
- To display an ongoing playback notification while audio is active

Relaxo does not request runtime access to:

- Camera
- Microphone recording
- Contacts
- Call logs
- SMS
- Files and media storage
- Precise location

### 5. Advertising And Analytics

Relaxo does not include:

- Ad networks
- Personalized advertising tools
- Third-party analytics or user tracking SDKs
- Crash reporting SDKs, based on the current app configuration reviewed in this project

If a future version adds analytics, advertising, crash reporting, sign-in, cloud backup, or any other data collection feature, this Privacy Policy and the Google Play Data safety form must be updated before release.

### 6. Internet And External Links

Relaxo is intended to work offline using audio files packaged inside the app.

The app may present external information such as a donation reference. If a user leaves the app and opens a third-party website, payment page, or service, data handling on that external destination is governed by the third party's own privacy policy and terms, not by Relaxo.

### 7. Data Sharing

Because Relaxo does not directly collect personal data, it does not directly share personal data with third parties for advertising, profiling, or resale.

### 8. Data Retention And Deletion

Relaxo does not maintain remote user accounts or a remote personal-data store for app users.

Operational app state exists locally on the device only as needed for app functionality. Removing the app from the device removes app data in accordance with Android system behavior.

### 9. Children's Privacy

Relaxo is a general audience relaxation app and is not designed to knowingly collect personal information from children.

### 10. Security

Relaxo reduces privacy risk by avoiding direct collection of personal information and by keeping the core listening experience local to the device.

### 11. Changes To This Policy

This Privacy Policy may be updated if the app's features, legal obligations, or data practices change. The effective date at the top of this document will be updated when changes are made.

### 12. Contact

For privacy or support questions, use the developer contact information shown in the Google Play listing.

## Google Play Data Safety Guidance

Based on the current source code in this project, the app appears suitable for a Google Play Data safety declaration that states no user data is collected by the app directly.

Before submitting, verify that your final release build still matches these statements. In particular, re-check the app if you later add:

- Analytics packages
- Ad SDKs
- Crash reporting tools
- Web views that load external services
- Sign-in or payment features
- Any server-side API integration

Important: Google Play usually requires a privacy policy URL, not just text inside the repository. To publish, place this privacy policy on a public HTTPS page and submit that URL in Play Console.

## Development

```bash
flutter pub get
flutter run
```
