package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
//import android.widget.ImageButton;

public class MainActivityWear extends Activity {

    private TextView mTextView;
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


                if(traffic!=0){
                    trafficButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent = new Intent(MainActivityWear.this, TrafficActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                if(sound!=0){
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
                if(light!=0){
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

}
