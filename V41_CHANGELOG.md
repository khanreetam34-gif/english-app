# v41 — phone-only build workflow

Added a GitHub Actions workflow so the project can be compiled to a debug APK without a PC.

The workflow uses Java 17, Gradle 8.7, builds `:app:assembleDebug`, and uploads the generated APK as a workflow artifact.
