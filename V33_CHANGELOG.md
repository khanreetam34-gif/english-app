# v33 — final integration cleanup

Major cleanup:
- Feature Status now reads from one central `FeatureRegistry`.
- Removes duplicated feature-state text from the Activity.
- Copy Feature Status uses the same central registry.
- Existing OCR, Settings/HUD persistence, Logs, Diagnostics, Script inspection and cloud-Android target remain unchanged.
