# Cloud API Contract

The Android rebuild keeps the cloud layer separate from the UI.

Suggested endpoints:
- GET /api/config
- GET /api/status
- POST /api/logs
- GET /api/update
- POST /api/settings

Authentication and authorization must be implemented using the owner's own backend credentials.
No private credentials or third-party game/account access is included in this project.
