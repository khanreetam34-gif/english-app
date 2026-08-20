# v6 next rebuild

This version adds the actual small visual assets recovered from the supplied APK and
a machine-readable inventory of its real layouts/assets/native libraries.

Next implementation targets:
1. Match the recoverable Main/Settings/Window/HUD/Log/Update/Web UI structures.
2. Replace user-visible Chinese strings with English equivalents.
3. Preserve Android/cloud-phone compatibility by keeping the app a normal APK.
4. Keep native/Lua components behind explicit, authorized interfaces rather than inventing behavior.

The original compiled native/Lua source is not assumed to be recoverable from the APK.
