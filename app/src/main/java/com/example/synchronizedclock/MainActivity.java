package com.example.synchronizedclock;

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

// AppCompatActivity creates activities
public class MainActivity extends AppCompatActivity {
    private UIHandler uiHandler;
    private NTPClient ntpClient;
    private Handler handler;

    @Override
    // onCreate method called when activity is first created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view of activity layout defined in "activity_main.xml"
        setContentView(R.layout.activity_main);

        // Allows application to perform network operations on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        // Applies StrictMode policy to current thread, allows network related operations
        StrictMode.setThreadPolicy(policy);

        // Instantiate the new classes
        uiHandler = new UIHandler();

        // Instantiate handler
        handler = new Handler();

        // Instantiate NTPClient
        try {
            ntpClient = new NTPClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Stores a local variable with activity to be able to pass it to the UI Handler
        Activity activity = this;

        // This creates a runnable object to be used to run code every X seconds
        Runnable runTask = new Runnable() {
            @Override
            public void run() {
                // If device has network connectivity show system time, else show network time
                if(isNetworkAvailable()) {
                    uiHandler.updateUIUsingNTPTime(ntpClient, activity);
                } else {
                    uiHandler.updateUIUsingSystemTime(activity);
                }
                // This schedules the runnable
                handler.postDelayed(this, 10000);
            }
        };
        // This starts the runnable for the first time
        handler.post(runTask);

    }

    // Returns availability of network connectivity
    private boolean isNetworkAvailable() {
        // Store the system connectivity manager in a local variable
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get current capabilities
        NetworkCapabilities capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
        boolean isAvailable = false;

        // If no capabilities was found, skip and return false.
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // If cellular was found, set availability to true.
                isAvailable = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // If WIFI was found, set availability to true.
                isAvailable = true;
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                // If ethernet was found, set availability to true.
                isAvailable = true;
            }
        }
        return isAvailable;
    }
}