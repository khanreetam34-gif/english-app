# Deployment

## Android
Open the Android project in Android Studio and build the debug APK.

## Cloud
The `cloud-backend` folder is a Node.js/Express service. It can be deployed to any provider that supports Node.js.

Set the Android app's authorized API endpoint to the deployed `/api` base URL.

## Important
The cloud service is a clean implementation for settings, HUD state, logs and update metadata. It does not claim to reproduce proprietary/private server behavior from compiled APK components.
