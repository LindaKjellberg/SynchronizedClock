package com.example.synchronizedclock.UI;

import android.app.Activity;
import android.widget.TextView;

import com.example.synchronizedclock.R;
import com.example.synchronizedclock.client.NTPClient;

import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UIHandler {

    // Update UI when online:
    public void updateUIUsingNTPTime(NTPClient ntpClient, Activity activity) {
        // Try to fetch the network time from NTP Server
        try {
            ntpClient.fetchTime();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Stores the fetched system- and remote time in local variables
        TimeStamp systemNTPTime = ntpClient.getSystemNTPTime();
        TimeStamp remoteNTPTime = ntpClient.getRemoteNTPTime();

        // Update UI element
        String remoteNTPTimeValue = new SimpleDateFormat("HH:mm:ss").format(remoteNTPTime.getDate());
        final TextView serverTime = (TextView) activity.findViewById(R.id.serverTime);
        serverTime.setText(remoteNTPTimeValue);

        // Update UI element
        final TextView timeDifference = (TextView) activity.findViewById(R.id.timeDifference);
        timeDifference.setText(ntpClient.getOffset() + "ms");

        // Update UI element
        String systemNTPTimeValue = new SimpleDateFormat("HH:mm:ss").format(systemNTPTime.getDate());
        final TextView systemTime = (TextView) activity.findViewById(R.id.systemTime);
        systemTime.setText(systemNTPTimeValue);
    }

    //Update UI when offline:

    public void updateUIUsingSystemTime(Activity activity) {
        // Update UI element
        final TextView serverTime = (TextView) activity.findViewById(R.id.serverTime);
        serverTime.setText("Network unavailable");

        // Update UI element
        final TextView timeDifference = (TextView) activity.findViewById(R.id.timeDifference);
        timeDifference.setText("0ms");

        // Update UI element
        String currentSystemTimeValue = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        final TextView systemTime = (TextView) activity.findViewById(R.id.systemTime);
        systemTime.setText(currentSystemTimeValue);
    }
}

