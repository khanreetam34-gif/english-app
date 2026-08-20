# v31 build guide

1. Open the project root in Android Studio.
2. Let Gradle sync complete.
3. Build > Make Project.
4. Build > Build APK(s).
5. Install `app/build/outputs/apk/debug/app-debug.apk` on the Android/cloud Android device.
6. Run the v31 test plan from `V30_TEST_PLAN.md`.

If Gradle reports a dependency/version issue, use Android Studio's suggested compatible version for the ML Kit text-recognition dependency; the OCR implementation is isolated in `OcrEngine.java`.
