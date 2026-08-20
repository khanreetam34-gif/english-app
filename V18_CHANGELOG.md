# v18

Added safe local integration helpers:
- AssetService can list/check/copy bundled assets.
- OcrService accepts a selected image and returns a structured preparation result.
- ScriptService can inspect the bundled `script.lr` asset without executing it.
- No original script/native binary is executed automatically.
- This remains a normal Android APK intended for supported cloud Android phones.
