# Synchronized Clock Android App

The Synchronized Clock Android App is a simple application that synchronizes your device's clock with an NTP (Network Time Protocol) server to ensure accurate timekeeping. It's particularly useful for situations where precise time synchronization is required.

## Features
- Synchronizes the device's clock with an NTP server.
- Displays synchronized time when the device has network connectivity.
- Shows the device's system time when there's no network connectivity.

## Usage
1. Open the app on your Android device.
2. The app will periodically update the displayed time based on network availability.
3. If your device is connected to the internet, it will synchronize the time with an NTP server.
4. In case of no network connectivity, the app will display the system time.

## Installation
To use this app, you can download and install it on your Android device. You can also build the app from the provided source code.

## Building the App
To build the app from the provided source code, follow these steps:

1. Clone this repository to your local machine.
`git clone git@github.com:LindaKjellberg/SynchronizedClock.git`
2. Open the project in Android Studio or your preferred Android development environment.
3. Build and run the app on an Android device or emulator.
4. Enjoy synchronized timekeeping!

## NTPClient Class
The `NTPClient` class is responsible for fetching the server time from an NTP server. It uses the Apache Commons Net library to communicate with NTP servers.

### Constants
- `SERVER_NAME`: The domain name of the NTP server used to obtain atomic time.
- `TIMEOUT`: The timeout value (10 seconds) to wait for a response from the NTP server in milliseconds.

### Methods
- `fetchTime()`: Fetches the time from the NTP server, computes the offset, and stores the local and remote NTP times.
- `getOffset()`: Returns the time offset between the local system time and the NTP server time.
- `getSystemNTPTime()`: Returns the local system time as a TimeStamp object.
- `getRemoteNTPTime()`: Returns the remote NTP time as a TimeStamp object.

### Constructor
The `NTPClient` class is initialized with a default timeout value when an instance is created.

```java
public NTPClient() throws IOException {
    ntpudpClient = new NTPUDPClient();
    ntpudpClient.setDefaultTimeout(TIMEOUT);
}
```

## UIHandler Class

The `UIHandler` class is responsible for updating the user interface (UI) of the app based on network availability. It handles both online and offline scenarios.

### Methods
- `updateUIUsingNTPTime(NTPClient ntpClient, Activity activity)`: Updates the UI with the synchronized time obtained from the NTP server.
- `updateUIUsingSystemTime(Activity activity)`: Updates the UI with the system time when there's no network connectivity.

### Usage
You can call the methods of the `UIHandler` class to update the UI elements in your Android app based on the current network connectivity and time synchronization status.

## Credits
This app was created by [Your Name] and is open-source. Contributions and bug reports are welcome.
