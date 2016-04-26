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
    public static int count=0, countElem=0;
    static int traffic, air, sound, light;
    public static int nullStatus = Color.rgb(40,40,40), //grey
            low = Color.rgb(0,153,0), //green
            medium = Color.rgb(255,128,0),//orange
            high = Color.rgb(255,0,0); //red

    //public static String[] data;
    public static String[] nullArray = {"0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};

    public static String[][] airData = new String[6][2]; //top is value, bottom is raw data
    public static String[][] soundData = new String[1][2];
    public static String[][] lightData = new String[1][2];
    public static String[][] trafficData = {{"0"},{"0"}};

    //dummy data
    //public static String[] data = {"0.0","0.0","0.0","0.0",/**/"1","h","0.4","h",/**/"8","r","3.72","r",/**/"0.0","0.0","0.0","0.0",/**/"4","d","2.4","d",/**/"0.0","0.0","0.0","0.0",/**/"5","j","6.14","j",/**/"3","a","4.0","a"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        final String[] data = getIntent().getStringArrayExtra("dataMap");

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                //mTextView = (TextView) stub.findViewById(R.id.text);

                if(data!=null){
                    System.out.println("splitting array");
                    arraySplit(data);

                }
                else{
                    System.out.println("array is null");
                    arraySplit(nullArray);
                }

                trafficButton = (ImageButton) stub.findViewById(R.id.button1);
                traffic = colourGetter(trafficButton,trafficData);
                soundButton = (ImageButton) stub.findViewById(R.id.button2);
                sound = colourGetter(soundButton,soundData);
                airButton = (ImageButton) stub.findViewById(R.id.button3);
                air = colourGetter(airButton,airData);
                lightButton = (ImageButton) stub.findViewById(R.id.button4);
                light = colourGetter(lightButton,lightData);


                if (traffic != nullStatus) {
                    trafficButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, TrafficActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                if (sound != nullStatus) {
                    soundButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, SoundValueActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                if (air != nullStatus) {
                    airButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, AirValueActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                if (light != nullStatus) {
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
    }

    public static void arraySplit(String[] data){

        airData[0][0]=data[0];  //no2
        airData[0][1]=data[2];
        airData[1][0]=data[4];  //co
        airData[1][1]=data[6];
        airData[2][0]=data[8];  //so2
        airData[2][1]=data[10];
        airData[3][0]=data[12]; //03
        airData[3][1]=data[14];
        airData[4][0]=data[16]; //pm10
        airData[4][1]=data[18];
        airData[5][0]=data[20]; //pm25
        airData[5][1]=data[22];

        lightData[0][0]=data[24];
        lightData[0][1]=data[26];

        soundData[0][0]=data[28];
        soundData[0][1]=data[30];
    }

    public static int colourGetter(ImageButton button, String[][] data){
        int status;
        int orange = 0;
        int green = 0;
        int red = 0;
        int now;

        for(int c=0; c<data.length;c++){
            now = (int)(Double.parseDouble(data[c][0]));
            //System.out.println(now);

            if(now >= 7){
                red++;
            }
            else if(now >= 5){
                orange ++;
            }
            else if(now != 0){
                green ++;
            }
        }

        if((red == 0)&&(orange == 0)&&(green==0)){
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
