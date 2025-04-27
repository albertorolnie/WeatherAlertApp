# Weather Alert App

## Overview
The **Weather Alert App** provides users with real-time weather updates and alerts for their location. It leverages weather data from external sources to notify users of important weather changes, such as severe weather events, temperature changes, and storm warnings. The app features an intuitive interface with weather charts to visualize temperature trends and other relevant weather data.

## Features
- **Real-Time Weather Alerts:** Get notifications about severe weather conditions, such as thunderstorms, heavy rain, or temperature extremes.
- **Weather Charts:** Visualize temperature and weather trends using interactive charts.
- **Location-Based Alerts:** The app automatically detects your location and provides alerts relevant to your area.
- **Intuitive Interface:** Easy-to-use interface with a clean design for a seamless user experience.

## Requirements
- **Android 6.0 (API Level 26) or higher**
- **Internet connection** to fetch real-time weather data.

## How it Works
1. **Weather Data Fetching:**
   - The app fetches real-time weather data from a weather API (e.g., OpenWeatherMap).
   - It continuously monitors the weather conditions based on your location.

2. **Notifications:**
   - Whenever significant weather changes are detected (such as a storm, extreme temperature, etc.), the app sends you a notification to alert you of the event.
   - You can customize the alert types and thresholds in the app settings.

3. **Weather Visualization:**
   - The app displays weather data through dynamic charts, showing temperature variations over time.
   - The data is updated regularly to keep you informed about the latest conditions.

4. **Location Tracking:**
   - The app uses the device's location to provide localized weather data. You can also manually enter a location to receive weather information for that area.

## How to Use
### 1. Install the App
   Download the app from the Google Play Store and install it on your Android device.

### 2. Allow Permissions
   - The app requires permission to access your location to provide accurate weather alerts.
   - Grant the app permission to send notifications for weather alerts.

### 3. Set Up Alerts
   - Upon opening the app, it will automatically detect your location and start showing relevant weather information.
   - You can customize your notification settings (e.g., which weather events you want to be alerted about, the threshold for temperature alerts).

### 4. View Weather Data
   - The main screen displays the current weather conditions (e.g., temperature, wind speed, humidity).
   - Use the chart view to explore weather trends and historical data.

### 5. Receive Alerts
   - When a significant weather event occurs, the app will notify you. You can click on the notification to open the app and get more detailed information.

## Technologies Used
- **Android Development:** Kotlin, Jetpack Compose, Room Database for local storage.
- **Weather API:** The app fetches real-time weather data from a weather API (e.g., OpenWeatherMap).
- **MPAndroidChart:** Used for rendering interactive weather charts.

## Troubleshooting
If you encounter any issues with the app:
- Ensure that your device's internet connection is stable for fetching weather data.
- Make sure you have granted the necessary permissions (location and notifications) in your device's settings.

## Contributing
Feel free to contribute to the app by reporting issues or submitting pull requests. If you have any ideas or enhancements, we are always open to suggestions.

## License
This project is licensed under the MIT License.
