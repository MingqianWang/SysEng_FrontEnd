package com.example.maddiewhitehall.pollutionapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by sherl_000 on 2015/12/1.
 */
public class ListenerService extends WearableListenerService
{
    public static final String TAG = "MyDataMap";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";
    private static final String[] types = {"NO2","CO","light","noise","SO2","O3","PM10","PM25"};
    private int notificationId = 1;


    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        DataMap dataMap;
        for(DataEvent dataEvent:dataEvents)
        {
            if(dataEvent.getType() == DataEvent.TYPE_CHANGED)
            {
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equalsIgnoreCase(WEARABLE_DATA_PATH))
                {
                    dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                    Log.v(TAG, "DataMap received on Wearable Device" + dataMap);

                    Intent startIntent = new Intent(this, MainActivityWear.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String[] myArray = new String[32];
                    myArray[0] = String.valueOf(dataMap.getDouble("NO2_value"));
                    myArray[1] = dataMap.getString("NO2_unit");
                    myArray[2] = String.valueOf(dataMap.getDouble("NO2_raw_value"));
                    myArray[3] = dataMap.getString("NO2_raw_value_unit");
                    myArray[4] = String.valueOf(dataMap.getDouble("CO_value"));
                    myArray[5] = dataMap.getString("CO_unit");
                    myArray[6] = String.valueOf(dataMap.getDouble("CO_raw_value"));
                    myArray[7] = dataMap.getString("CO_raw_value_unit");
                    myArray[8] = String.valueOf(dataMap.getDouble("SO2_value"));
                    myArray[9] = dataMap.getString("SO2_unit");
                    myArray[10] = String.valueOf(dataMap.getDouble("SO2_raw_value"));
                    myArray[11] = dataMap.getString("SO2_raw_value_unit");
                    myArray[12] = String.valueOf(dataMap.getDouble("O3_value"));
                    myArray[13] = dataMap.getString("O3_unit");
                    myArray[14] = String.valueOf(dataMap.getDouble("O3_raw_value"));
                    myArray[15] = dataMap.getString("O3_raw_value_unit");
                    myArray[16] = String.valueOf(dataMap.getDouble("PM10_value"));
                    myArray[17] = dataMap.getString("PM10_unit");
                    myArray[18] = String.valueOf(dataMap.getDouble("PM10_raw_value"));
                    myArray[19] = dataMap.getString("PM10_raw_value_unit");
                    myArray[20] = String.valueOf(dataMap.getDouble("PM25_value"));
                    myArray[21] = dataMap.getString("PM25_unit");
                    myArray[22] = String.valueOf(dataMap.getDouble("PM25_raw_value"));
                    myArray[23] = dataMap.getString("PM25_raw_value_unit");
                    myArray[24] = String.valueOf(dataMap.getDouble("light_value"));
                    myArray[25] = dataMap.getString("light_unit");
                    myArray[26] = String.valueOf(dataMap.getDouble("light_raw_value"));
                    myArray[27] = dataMap.getString("light_raw_value_unit");
                    myArray[28] = String.valueOf(dataMap.getDouble("noise_value"));
                    myArray[29] = dataMap.getString("noise_unit");
                    myArray[30] = String.valueOf(dataMap.getDouble("noise_raw_value"));
                    myArray[31] = dataMap.getString("noise_raw_value_unit");
                    startIntent.putExtra("dataMap", myArray);

                    //startIntent.put
                    startActivity(startIntent);
                    //sendNotification(startIntent);
                }
            }
        }
    }

    private void sendNotification(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,"Update", pendingIntent).build();

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("New Location Entered" + notificationId)
                .setContentTitle("Update")
                .setSmallIcon(R.mipmap.ic_launcher)
                .extend(new NotificationCompat.WearableExtender().addAction(action))
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId++, notification);
    }

}