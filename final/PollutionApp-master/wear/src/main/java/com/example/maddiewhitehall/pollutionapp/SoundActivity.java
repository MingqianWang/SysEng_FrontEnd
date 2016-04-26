package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SoundActivity extends Activity {

    private TextView DBReading;
    private ImageButton button;
    private Intent intent;
    Float incomingDB = 23f;

    public static Activity level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        level = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                DBReading = (TextView) stub.findViewById(R.id.sound_reading);

                if(Double.parseDouble(MainActivityWear.soundData[0][1])==0.0){
                    DBReading.setText("-");
                }
                else{
                    DBReading.setText(MainActivityWear.soundData[0][1]);
                }



                button = (ImageButton) stub.findViewById(R.id.audioButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SoundValueActivity.num.finish();
                        intent = new Intent(SoundActivity.this, SoundValueActivity.class);
                        startActivity(intent);
                    }

                });
            }
        });
    }
}
