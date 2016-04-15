package com.example.maddiewhitehall.pollutionapp;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivityPhone extends AppCompatActivity{

    final private String STARTUPMESSAGE = "The application has started. You can close the app on the phone.";
    private TextView mStartUpMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartUpMessageTextView = (TextView)findViewById(R.id.StartUpMessage);
        Intent intent = new Intent(this, DataService.class);
        startService(intent);
        mStartUpMessageTextView.setText(STARTUPMESSAGE);
        Log.v("main", "data service started");

    }

    public void startUpdateEvent(View view) {

    }

    public void stopUpdateEvent(View view) {
        stopService(new Intent(this, DataService.class));
    }

}