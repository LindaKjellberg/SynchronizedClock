package com.example.synchronizedclock.client;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.net.InetAddress;

// NTP Client class to get server time from NTP server
public class NTPClient {

    // Constant to store domain name of the NTP server I will use to get the atomic time from
    private static final String SERVER_NAME = "0.se.pool.ntp.org";

    // Constant to set timeout value of 10sec to wait for response from server in ms
    private static final int TIMEOUT = 10000;

    // Stores information about time received from NTP server
    private volatile TimeInfo timeInfo;

    // Stores time difference between local system time and time from NTP server
    private volatile Long offset;

    // Class member for NTPUDPClient
    private NTPUDPClient ntpudpClient;

    // TimeStamp instance variables to store system time and remote time after fetching the time from NTP server
    private TimeStamp systemNTPTime;
    private TimeStamp remoteNTPTime;

    // Constructor method called when an instance of the NTPClient class is created. Initializes the ntpudpClient and sets a default timeout value
    public NTPClient() throws IOException {
        ntpudpClient = new NTPUDPClient();
        ntpudpClient.setDefaultTimeout(TIMEOUT);
    }

    // Method to fetch time from NTP server
    public void fetchTime() throws IOException {
        // Get IP address of NTP server
        InetAddress inetAddress = InetAddress.getByName(SERVER_NAME);
        // ntpudpClient sends request to NTP server at the IP address and receives response in a TimeInfo object
        TimeInfo timeInfo = ntpudpClient.getTime(inetAddress);
        // Compute and validate details of the NTP message packet. Computed fields include the offset and delay.
        timeInfo.computeDetails();

        // Checks if synchronization is successful (offset is not null), and then updates timeInfo and offset variables with received information
        if(timeInfo.getOffset() != null) {
            this.timeInfo = timeInfo;
            this.offset = timeInfo.getOffset();
        }
        // Get current system time as a TimeStamp object
        this.systemNTPTime = TimeStamp.getCurrentTime();


        // Get remote NTP time by adding the offset to current system time
        long currentTime = System.currentTimeMillis();
        this.remoteNTPTime = TimeStamp.getNtpTime(currentTime + offset);
    }

    // Get method for Offset
    public Long getOffset() {
        return this.offset;
    }

    // Get method for system NTP time
    public TimeStamp getSystemNTPTime() {
        return this.systemNTPTime;
    }

    // Get method for remote NTP time
    public TimeStamp getRemoteNTPTime() {
        return this.remoteNTPTime;
    }
}
