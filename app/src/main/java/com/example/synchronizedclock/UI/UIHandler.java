package com.example.synchronizedclock.UI;

import android.app.Activity;
import android.widget.TextView;

import com.example.synchronizedclock.R;
import com.example.synchronizedclock.client.NTPClient;

import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class UIHandler {

    //Method to fetch NTPTime using NTPClient object as an argument, if it fails throws runtime exception
    public void fetchNTPTime(NTPClient ntpClient, Activity activity) {
        try {
            ntpClient.fetchTime();
        } catch (IOException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }


        //Retrieves system- and remote NTPTime using ntpClient methods from NTPClient class
        TimeStamp systemNTPTime = ntpClient.getSystemNTPTime();
        TimeStamp remoteNTPTime = ntpClient.getRemoteNTPTime();

        //Update UI:

        //serverTime
        final TextView serverTime = (TextView) activity.findViewById(R.id.serverTime);
        serverTime.setText(remoteNTPTime.toDateString());

        //timeDifference
        final TextView timeDifference = (TextView) activity.findViewById(R.id.timeDifference);
        timeDifference.setText("Display timeDifference = serverTime - systemTime");

        //systemTime
        final TextView systemTime = (TextView) activity.findViewById(R.id.systemTime);
        systemTime.setText(systemNTPTime.toDateString());

        //currentTime
        final TextView currentTime = (TextView) activity.findViewById(R.id.currentTime);
        //Update current time
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentTime.setText("Current time: " + currentDateTimeString);

    }
}

