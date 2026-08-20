# v31 — stability pass

Major stability improvement:
- Added Android lifecycle persistence so settings are saved when the activity pauses.
- Settings remain independent from runtime logs.
- Existing OCR, diagnostics, script inspection, feature status and cloud-Android target remain intact.

This build still does not execute proprietary/native code recovered from the original APK.
