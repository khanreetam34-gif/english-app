# Build commands

Open this folder in Android Studio, allow Gradle sync, then:

- Build > Make Project
- Build > Build APK(s)

Or from a machine with Gradle/Android SDK configured:
`gradle assembleDebug`

Expected debug APK:
`app/build/outputs/apk/debug/app-debug.apk`

This project is a normal Android APK target; it can be installed in a supported cloud Android device.
