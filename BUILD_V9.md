# v9

This build moves several real assets from the supplied APK into the Android rebuild instead of using placeholders.

Target:
- English UI
- Normal Android APK
- Installable inside a supported cloud Android phone
- Recoverable OCR/script assets preserved for authorized implementation

Native `.so` libraries remain excluded from automatic execution because their original interfaces/source are not available in the rebuild.
