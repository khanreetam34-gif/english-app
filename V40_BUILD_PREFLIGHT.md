# v40 Build Preflight

Before the first APK build:

1. Open the project root in Android Studio.
2. Confirm an Android SDK is installed.
3. Confirm the Android Gradle Plugin/Gradle versions are supported by the installed Android Studio.
4. Sync Gradle.
5. Resolve any dependency prompts, especially ML Kit OCR.
6. Build the debug APK.
7. Install the APK on the target Android/cloud Android environment.
8. Run `V39_FINAL_CHECKLIST.md`.

Expected debug output:
`app/build/outputs/apk/debug/app-debug.apk`

The source project itself does not contain a proprietary original runtime or private backend.
