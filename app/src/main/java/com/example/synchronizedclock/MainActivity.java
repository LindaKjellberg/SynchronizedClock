package com.example.synchronizedclock;
//import com.example.synchronizedclock.textView.UIHandler.java;

import androidx.appcompat.app.AppCompatActivity;
import com.example.synchronizedclock.client.NTPClient;
import com.example.synchronizedclock.UI.UIHandler;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import java.io.IOException;

//AppCompatActivity creates activities
public class MainActivity extends AppCompatActivity {
    private UIHandler uiHandler;
    private NTPClient ntpClient;
    private Handler handler;

    @Override
    //onCreate method called when activity is first created
    protected void onCreate(Bundle savedInstanceState) {
        //savedInstanceState object stores data about activities previous state
        super.onCreate(savedInstanceState);
        //Set  content view of activity layout defined in "activity_main.xml"
        setContentView(R.layout.activity_main);

        //Allows application to perform network operations on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //Applies StrictMode policy to current thread, allows network related operations
        StrictMode.setThreadPolicy(policy);

        //Instantiate the new classes
        uiHandler = new UIHandler();

        //Initialize handler
        handler = new Handler();

        //Initialize NTPClient
        try {
            ntpClient = new NTPClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Activity activity = this;
        Runnable runTask = new Runnable() {
            @Override
            public void run() {
                //If airplane mode is ON show system time, else show network time
                if(isNetworkAvailable()) {
                    uiHandler.updateUIUsingNTPTime(ntpClient, activity);
                } else {
                    uiHandler.updateUIUsingSystemTime(activity);
                }
                handler.postDelayed(this, 10000);
            }
        };

        handler.post(runTask);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
        boolean isAvailable = false;

        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                isAvailable = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                isAvailable = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}