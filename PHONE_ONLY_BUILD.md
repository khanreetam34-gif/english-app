# Build from an Android phone (no PC)

The easiest phone-only route is GitHub Actions.

1. Create/sign in to a GitHub account.
2. Create a new repository (Private is fine).
3. Upload the contents of this project so `settings.gradle`, `build.gradle`, and the `app` folder are at the repository root.
4. Also upload `.github/workflows/build-apk.yml`.
5. Open the repository's **Actions** tab.
6. Select **Build Android APK**.
7. Tap **Run workflow**.
8. Wait for the workflow to finish.
9. Open the completed run and download the artifact named **English-Rebuild-debug-apk**.
10. Extract the artifact and install `app-debug.apk` on the Android/cloud Android device.

Do not upload passwords, API keys, or private credentials.

The build workflow only compiles the current rebuild. It does not add or execute the original proprietary/native runtime.
