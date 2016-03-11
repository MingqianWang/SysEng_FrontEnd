package com.example.mingqianwang.locationgetter;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation = null;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates;
    private Button mStartUpdatesButton;
    private Button mStopUpdatesButton;
    private TextView mLongitudeTextView;
    private TextView mLatitudeTextView;

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 60000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartUpdatesButton = (Button) findViewById(R.id.STARTUPDATES);
        mStopUpdatesButton = (Button) findViewById(R.id.STOPUPDATES);

        mLongitudeTextView = (TextView) findViewById(R.id.longitudeTextView);
        mLatitudeTextView = (TextView) findViewById(R.id.latitudeTextView);

        mRequestingLocationUpdates = false;
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();
    }

    public void onConnected(Bundle bundle) {
        try{
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //Log.v("message",String.valueOf(mLastLocation.getLatitude()));
            //Log.v("message",String.valueOf(mLastLocation.getLongitude()));
            mLatitudeTextView.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeTextView.setText(String.valueOf(mLastLocation.getLongitude()));
        }
        }catch (SecurityException e) {
            Log.e("error", "something went wrong, unable to get location");
        }
    }

    public void getLocationOnClick(View view) {
        try{
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                Log.v("message",String.valueOf(mLastLocation.getLatitude()));
                Log.v("message",String.valueOf(mLastLocation.getLongitude()));
            }
        }catch (SecurityException e) {
            Log.e("error", "something went wrong, unable to get location");
        }
    }

    public void startLocationUpdatesHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    public void stopLocationUpdatesHandler(View view) {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch(SecurityException e) {
            Log.e("error", "something went wrong, unable to start location update");
        }
    }

    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }

    protected void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);


        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //Log.v("message",String.valueOf(mLastLocation.getLatitude()));
        //Log.v("message",String.valueOf(mLastLocation.getLongitude()));
        mLatitudeTextView.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitudeTextView.setText(String.valueOf(mLastLocation.getLongitude()));
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
