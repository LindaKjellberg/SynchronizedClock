package com.example.synchronizedclock;

import androidx.appcompat.app.AppCompatActivity;
import com.example.synchronizedclock.client.NTPClient;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //@Composable
        //onCreate
        //communicate with text fields
        //serverTime
        final TextView serverTime = (TextView) findViewById(R.id.serverTime);
        serverTime.setText("Display serverTime = NPT");

        //timeDifference
        final TextView timeDifference = (TextView) findViewById(R.id.timeDifference);
        timeDifference.setText("Display timeDifference = serverTime - systemTime");

        //systemTime
        final TextView systemTime = (TextView) findViewById(R.id.systemTime);
        systemTime.setText("Display systemTime");

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