package com.example.synchronizedclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//import com.example.synchronizedclock.client.ServerTime;


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
        serverTime.setText("Display serverTime");

        //systemTime
        final TextView systemTime = (TextView) findViewById(R.id.systemTime);
        systemTime.setText("Display systemTime");

    }
}