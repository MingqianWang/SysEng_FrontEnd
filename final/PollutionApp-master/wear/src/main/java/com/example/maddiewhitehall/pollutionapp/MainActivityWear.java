package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.widget.ImageButton;

public class MainActivityWear extends Activity {

    private TextView mTextView;
    private int notificationId = 1;
    private static ImageButton airButton, trafficButton, soundButton, lightButton;
    private static Intent intent;
    public static int airRun = 0, lightRun = 0, soundRun = 0;
    static int traffic, air, sound, light;
    static int nullStatus = Color.rgb(60,60,60), //grey
                low = Color.rgb(0,153,0), //green
                medium = Color.rgb(255,128,0),//orange
                high = Color.rgb(255,0,0); //red


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);

                trafficButton = (ImageButton) stub.findViewById(R.id.button1);
                traffic = colourGetter(trafficButton);
                soundButton = (ImageButton) stub.findViewById(R.id.button2);
                sound = colourGetter(soundButton);
                airButton = (ImageButton) stub.findViewById(R.id.button3);
                air = colourGetter(airButton);
                lightButton = (ImageButton) stub.findViewById(R.id.button4);
                light = colourGetter(lightButton);


                if (traffic != 0) {
                    trafficButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, TrafficActivity.class);

                            startActivity(intent);
                        }
                    });
                }
                if (sound != 0) {
                    soundButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, SoundValueActivity.class);

                            startActivity(intent);
                        }
                    });
                }
                if (air != 0) {
                    airButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, AirValueActivity.class);

                            startActivity(intent);
                        }
                    });
                }
                if (light != 0) {
                    lightButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, BuildingValueActivity.class);

                            startActivity(intent);
                        }
                    });
                }
            }
        });
        String[] data = getIntent().getStringArrayExtra("dataMap");
        if(data != null) {
            Log.v("MainActivityAir", data[0]);
        }
    }

    public static int colourGetter(ImageButton button /*,value array*/){
        int status;
        int orange = 0;
        int green = 0;
        int red = 0;


        //for(int k = 0; k < 3 /*value.length*/; k++){ //iterate through all the values in the thing
          //  if(/*value[k]*/ >= 7){
            //    red++;
            //}
            //else if(/*value[k]*/ >= 5){
             //   orange ++;
            //}
            //else if(/*value[k]*/!=null{
             //   green ++;
            //}
        //}

        if((red == 0)&&(orange == 1)&&(green==0)){
            status = nullStatus;
        }
        else if(red!=0){
            status = high;
        }
        else if(orange > green){
            status = medium;
        }
        else{
            status = low;
        }

        button.setBackgroundColor(status);

        return status;
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
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContentTitle("Update");
//        builder.setContentText("Your entered a new location");
//        builder.setSmallIcon(R.drawable.ic_full_cancel);
//
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        managerCompat.notify(1, builder.build());

    }


}
