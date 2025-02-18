# AliveKeeper Android App
## Overview
The KeepAliveService Android app ensures that the device does not go to sleep by running a foreground service. 
This service periodically logs the current date and time every 5 seconds while the app is running. 
The service can be started and stopped via buttons in the app.

## Key Features:
- Prevents the device from sleeping when the service is active.
- Periodically logs the current date and time every 5 seconds.
- Allows the service to be started and stopped via buttons in the app.
- The service operates in the foreground, ensuring the device remains awake even when the screen is locked.

## Prerequisites
- Android Studio (or another IDE with Android development support).
- A physical Android device or emulator running Android 11 or later.
