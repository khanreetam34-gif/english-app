# v23

OCR workflow is now wired into the UI:
- Select an image from Android storage.
- Decode the selected image.
- Run the local ML Kit English OCR engine.
- Display the recognized text.
- Copy OCR results to the clipboard.
- OCR success/failure is written to the persistent runtime log.

The original proprietary script/native runtime remains intentionally unexecuted.
