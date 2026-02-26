# Vista Board Split-Flap Simulator

A realistic Split-Flap display simulator for Google TV (Android TV), controlled remotely via a Progressive Web App (PWA).

## Features

*   **Realistic Animation:** 22x6 character grid with 3D flip mechanics and "click-clack" sound effects.
*   **Remote Control:** Update the message instantly from your phone or computer using the Web Controller.
*   **Android TV Optimized:** Designed for 10-foot UI with Leanback support.
*   **Local Network:** Communication happens entirely over your local Wi-Fi (no cloud servers).

## Installation

### 1. Install the Android App
1.  Download the latest APK from the `distribution/` folder in this repository: [app-debug.apk](distribution/app-debug.apk).
2.  Sideload the APK onto your Android TV device (e.g., using ADB or a File Manager app).
3.  Launch "Split Flap Simulator" from your apps list.

### 2. Open the Web Controller
You can access the controller in two ways:

*   **Online (GitHub Pages):** Go to `https://<your-username>.github.io/<repo-name>/` (if configured).
*   **Local File:** Download `index.html` to your device and open it in a browser.

## Usage

1.  **Start the TV App:** It will display an IP address at the bottom right (e.g., `192.168.1.50`).
2.  **Open the Web Controller:** Enter the IP address shown on the TV.
3.  **Send a Message:** Type your text and hit "Update Display".

## Troubleshooting

### "Blocked by browser" / Mixed Content Error
If you are using the Web Controller via **HTTPS** (like GitHub Pages), your browser might block the connection to your TV's local IP address (which is **HTTP**).

*   **Chrome/Edge:** Click the **Lock** or **Shield** icon in the address bar -> Site Settings -> Allow "Insecure Content" or "Mixed Content".
*   **Firefox:** Click the Lock icon -> Disable protection for this site.
*   **Alternative:** Use the controller from a local file (`file:///.../index.html`) or serve it via HTTP.

## Development

### Build from Source
```bash
./gradlew assembleDebug
```
The APK will be generated in `app/build/outputs/apk/debug/`.
