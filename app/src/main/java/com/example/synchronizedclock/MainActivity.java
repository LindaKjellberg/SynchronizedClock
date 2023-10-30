package com.example.synchronizedclock;

import androidx.appcompat.app.AppCompatActivity;
import com.example.synchronizedclock.client.NTPClient;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NTPClient ntpClient;
        try {
            ntpClient = new NTPClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // This function updates the NTP time and the UI
        // This function should be called every 10 seconds
        fetchNTPTime(ntpClient);
    }

    private void fetchNTPTime(NTPClient ntpClient) {
        try {
            ntpClient.fetchTime();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TimeStamp systemNTPTime = ntpClient.getSystemNTPTime();
        TimeStamp remoteNTPTime = ntpClient.getRemoteNTPTime();

        //@Composable
        //onCreate
        //communicate with text fields
        //serverTime
        final TextView serverTime = (TextView) findViewById(R.id.serverTime);
        serverTime.setText(remoteNTPTime.toDateString());

        //timeDifference
        final TextView timeDifference = (TextView) findViewById(R.id.timeDifference);
        timeDifference.setText("Display timeDifference = serverTime - systemTime");

        //systemTime
        final TextView systemTime = (TextView) findViewById(R.id.systemTime);
        systemTime.setText(systemNTPTime.toDateString());

        //currentTime
        final TextView currentTime = (TextView) findViewById(R.id.currentTime);
        //Update current time
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        //currentTime.setText("Display currentTime = systemTime - timeDifference");
        currentTime.setText("current tajm: " + currentDateTimeString);
        /*
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        textView is the TextView view that should display it
        stextView.setText(currentDateTimeString);
         */
    }
}