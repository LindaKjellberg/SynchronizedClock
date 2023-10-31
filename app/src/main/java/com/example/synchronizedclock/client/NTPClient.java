package com.example.synchronizedclock.client;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

//NTP Client to get server time from NTP server
public class NTPClient {

    private static final String SERVER_NAME = "0.se.pool.ntp.org";
    private static final int TIMEOUT = 10000;

    private volatile TimeInfo timeInfo;
    private volatile  Long offset;
    //Class member of NTPUDPClient
    private NTPUDPClient ntpudpClient;
    private TimeStamp systemNTPTime;
    private TimeStamp remoteNTPTime;

    public NTPClient() throws IOException {
        ntpudpClient = new NTPUDPClient();
        ntpudpClient.setDefaultTimeout(TIMEOUT);
    }

    public void fetchTime() throws IOException {
        InetAddress inetAddress = InetAddress.getByName(SERVER_NAME);
        TimeInfo timeInfo = ntpudpClient.getTime(inetAddress);
        timeInfo.computeDetails();
        if(timeInfo.getOffset() != null) {
            this.timeInfo = timeInfo;
            this.offset = timeInfo.getOffset();
        }
        // Get current system time
        this.systemNTPTime = TimeStamp.getCurrentTime();

        // Get remote NTP time
        long currentTime = System.currentTimeMillis();
        this.remoteNTPTime = TimeStamp.getNtpTime(currentTime + offset);
    }
    public TimeStamp getSystemNTPTime() {
        return this.systemNTPTime;
    }
    public TimeStamp getRemoteNTPTime() {
        return this.remoteNTPTime;
    }
}
