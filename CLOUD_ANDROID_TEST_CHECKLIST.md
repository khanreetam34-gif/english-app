# Cloud Android Compatibility Checklist

- [ ] APK installs without root
- [ ] App launches on the cloud Android instance
- [ ] Touch controls work
- [ ] File picker works
- [ ] WebView/network works when enabled
- [ ] HUD permission flow works when supported by the cloud provider
- [ ] App survives device restart
- [ ] App data persists where the cloud device provides persistent storage
- [ ] No physical-device-only dependency is required

The cloud phone is only the Android execution environment; this project does not implement a cloud-phone service itself.
