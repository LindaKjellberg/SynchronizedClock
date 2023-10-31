package com.example.synchronizedclock;

import androidx.appcompat.app.AppCompatActivity;
import com.example.synchronizedclock.client.NTPClient;

import android.os.Bundle;
import android.widget.TextView;

import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//AppCompatActivity creates activities
public class MainActivity extends AppCompatActivity {

    private NTPClient ntpClient;
    private Timer timer;

    @Override
    //onCreate method called when activity is first created
    protected void onCreate(Bundle savedInstanceState) {
        //savedInstanceState object stores data about activities previous state
        super.onCreate(savedInstanceState);
        //Set  content view of activity layout defined in "activity_main.xml"
        setContentView(R.layout.activity_main);

        //Initialize NTPClient
        try {
            ntpClient = new NTPClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Timer that runs fetchNTPTime() every 10 seconds
        timer = new Timer();

        // This function updates the NTP time and the UI

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchNTPTime(ntpClient);
            }
        }, 0, 10 * 1000); //Delay of 0 milliseconds, repeat every 10 seconds


        /*
        //Allows application to perform network operations on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //Applies StrictMode policy to current thread, allows network related operations
        StrictMode.setThreadPolicy(policy);

        //NTPClient reference type and ntpClient variable of NTPClient. Try to create new NTPClient object, if it fails throws runtime exception
        NTPClient ntpClient;
        try {
            ntpClient = new NTPClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

    //Method to fetch NTPTime using NTPClient object as an argument, if it fails throws runtime exception
    private void fetchNTPTime(NTPClient ntpClient) {
        try {
            ntpClient.fetchTime();
        } catch (IOException e) {
            throw new RuntimeException(e); //e.printStackTrace();
        }

        // Update your UI with the new NTP time
        // ...

        // For example, you can update TextViews with new time values
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Update your UI components here
            }
        });

        //Retrieves system- and remote NTPTime using ntpClient methods from NTPClient class
        TimeStamp systemNTPTime = ntpClient.getSystemNTPTime();
        TimeStamp remoteNTPTime = ntpClient.getRemoteNTPTime();


        //Following lines updates the text field elements in UI using their IDs:

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
        currentTime.setText("Current time: " + currentDateTimeString);

    }
}