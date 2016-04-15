package com.example.maddiewhitehall.pollutionapp;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DataService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    GoogleApiClient mGoogleApiClient = null;
    public static final String TAG = "MyDataMap";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path"; //can be any string that starts with a forward slash

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 30000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private Location mLastLocation = null;
    private LocationRequest mLocationRequest;

    private class FetchDataTask extends AsyncTask<Void,Void,Void>
    {
        private PollutionData generateDataObject(JSONObject object) throws JSONException{

            return new PollutionData(object.getDouble("value"), object.getString("units"), object.getDouble("raw_value"), object.getString("raw_units"));
        }

        @Override
        protected Void doInBackground(Void...params)
        {
            String url = "https://fierce-peak-41091.herokuapp.com/api/all?maxDist=2000&latitude="
                + mLastLocation.getLatitude() + "&longitude=" + mLastLocation.getLongitude();
            //String url = "https://fierce-peak-41091.herokuapp.com/api/all?maxDist=2000&latitude=51.5222789&longitude=-0.1444187";

            try {
                String result = new DataFetcher().getUrlString(url);
                JSONArray response = new JSONArray(result);
                JSONObject nearest = response.getJSONObject(0);
                JSONObject data = nearest.getJSONObject("data");
                PollutionData NO2 = null;
                PollutionData CO = null;
                PollutionData light = null;
                PollutionData noise = null;
                PollutionData SO2 = null;
                PollutionData O3 = null;
                PollutionData PM10 = null;
                PollutionData PM25 = null;
                if( !data.isNull("no2")) {
                    NO2 = generateDataObject(data.getJSONObject("no2"));
                }
                if(!data.isNull("co") ) {
                    CO = generateDataObject(data.getJSONObject("co"));
                }
                if(!data.isNull("light")) {
                    light = generateDataObject(data.getJSONObject("light"));
                }
                if(!data.isNull("noise")) {
                    noise = generateDataObject(data.getJSONObject("noise"));
                }
                if(!data.isNull("so2")) {
                    SO2 = generateDataObject(data.getJSONObject("so2"));
                }
                if(!data.isNull("o3")) {
                    O3 = generateDataObject(data.getJSONObject("o3"));
                }
                if(!data.isNull("pm10")) {
                    PM10 = generateDataObject(data.getJSONObject("pm10"));
                }
                if(!data.isNull("pm25")) {
                    PM25 = generateDataObject(data.getJSONObject("pm25"));
                }
                PollutionDataCollection dataCollection = new PollutionDataCollection(NO2, CO, light, noise, SO2, O3, PM10, PM25);
                sendDataMapToDataLayer(dataCollection);
            }
            catch(JSONException je) {
                Log.e("DataService", "Failed to parse json", je);
            }
            catch(IOException ioe) {
                Log.e("DataService", "Failed to fetch URL: ", ioe);
            }
            return  null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("data service", "service started");
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(Wearable.API);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mGoogleApiClient = builder.build();
        createLocationRequest();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //sendDataMapToDataLayer(3.0f, 3.0f);
        new FetchDataTask().execute();
        startLocationUpdates();
    }

    private DataMap createDataMap(PollutionDataCollection dataCollection)
    {
        DataMap dataMap = new DataMap();
        dataMap.putLong("time", System.currentTimeMillis());
        if(dataCollection.getNO2() != null) {
            PollutionData data = dataCollection.getNO2();
            dataMap.putDouble("NO2_value",data.getValue());
            dataMap.putString("NO2_unit", data.getUnit());
            dataMap.putDouble("NO2_raw_value", data.getRawValue());
            dataMap.putString("NO2_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getCO() != null) {
            PollutionData data = dataCollection.getCO();
            dataMap.putDouble("CO_value", data.getValue());
            dataMap.putString("CO_unit", data.getUnit());
            dataMap.putDouble("CO_raw_value", data.getRawValue());
            dataMap.putString("CO_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getLight() != null) {
            PollutionData data = dataCollection.getLight();
            dataMap.putDouble("light_value",data.getValue());
            dataMap.putString("light_unit", data.getUnit());
            dataMap.putDouble("light_raw_value", data.getRawValue());
            dataMap.putString("light_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getNoise() != null) {
            PollutionData data = dataCollection.getNoise();
            dataMap.putDouble("noise_value",data.getValue());
            dataMap.putString("noise_unit", data.getUnit());
            dataMap.putDouble("noise_raw_value", data.getRawValue());
            dataMap.putString("noise_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getSO2() != null) {
            PollutionData data = dataCollection.getSO2();
            dataMap.putDouble("SO2_value",data.getValue());
            dataMap.putString("SO2_unit", data.getUnit());
            dataMap.putDouble("SO2_raw_value", data.getRawValue());
            dataMap.putString("SO2_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getO3() != null) {
            PollutionData data = dataCollection.getO3();
            dataMap.putDouble("O3_value",data.getValue());
            dataMap.putString("O3_unit", data.getUnit());
            dataMap.putDouble("O3_raw_value", data.getRawValue());
            dataMap.putString("O3_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getPM10() != null) {
            PollutionData data = dataCollection.getPM10();
            dataMap.putDouble("PM10_value",data.getValue());
            dataMap.putString("PM10_unit", data.getUnit());
            dataMap.putDouble("PM10_raw_value", data.getRawValue());
            dataMap.putString("PM10_raw_value_unit", data.getRawUnit());
        }
        if(dataCollection.getPM25() != null) {
            PollutionData data = dataCollection.getPM25();
            dataMap.putDouble("PM25_value",data.getValue());
            dataMap.putString("PM25_unit", data.getUnit());
            dataMap.putDouble("PM25_raw_value", data.getRawValue());
            dataMap.putString("PM25_raw_value_unit", data.getRawUnit());
        }

        return dataMap;
    }


    public void sendDataMapToDataLayer(PollutionDataCollection dataCollection)
    {
        if(mGoogleApiClient.isConnected())
        {
            DataMap dataMap = createDataMap(dataCollection);
            new SendDataMapToDataLayer(WEARABLE_DATA_PATH,dataMap).start();
        }
        else
        {
            Log.v(TAG, "Connection is closed");
        }

    }

    public class SendDataMapToDataLayer extends  Thread
    {
        String mPath;
        DataMap mDataMap;

        public SendDataMapToDataLayer(String path, DataMap dataMap)
        {
            this.mPath = path;
            this.mDataMap = dataMap;
        }

        @Override
        public void run()
        {
            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(WEARABLE_DATA_PATH);
            putDataMapRequest.getDataMap().putAll(mDataMap); //wrap the DataMap object inside the PutDataMapRequest
            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

            DataApi.DataItemResult dataItemResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).await(); // await() blocks the thread until we got the result
            if(dataItemResult.getStatus().isSuccess())
            {
                Log.v(TAG,"DataItem successfully sent");
            }
            else
            {
                Log.v(TAG,"error while sending DataItem");
            }

        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);


        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    protected void startLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch(SecurityException e) {
            Log.e("error", "something went wrong, unable to start location update");
        }
    }

    @Override
    public void onDestroy() {
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        Log.v("DataService", "Service has been stopped");
        super.onDestroy();
    }

    protected void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        new FetchDataTask().execute();
        Log.v("message", String.valueOf(mLastLocation.getLatitude()));
        Log.v("message", String.valueOf(mLastLocation.getLongitude()));

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
