package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SoundValueActivity extends Activity {

    private TextView mTextView;
    private ImageButton button;
    private Intent intent;
    RelativeLayout[] scale = new RelativeLayout[5];
    public static Activity num;

    int [] gridLayout = {R.id.Grid1a,R.id.Grid1b,R.id.Grid1c,R.id.Grid1d,R.id.Grid1e,R.id.Grid1f,R.id.Grid1g,R.id.Grid1h,R.id.Grid1i,R.id.Grid1j};

    int dbLevel = (int)(Double.parseDouble(MainActivityWear.soundData[0][0]));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        num = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_value);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                for(int b=0;b<scale.length;b++){
                    scale[b] = (RelativeLayout) stub.findViewById(gridLayout[b]);
                }


                for (int n = 0; n < dbLevel; n++) {
                    int colour = 0;

                    if(dbLevel<3){
                        colour = MainActivityWear.low;
                    }
                    else if(dbLevel>7){
                        colour = MainActivityWear.high;
                    }
                    else{
                        colour = MainActivityWear.medium;
                    }
                    scale[n].setBackgroundColor(colour);
                }


                button = (ImageButton) stub.findViewById(R.id.audioButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        if(MainActivityWear.soundRun==1){
                            SoundActivity.level.finish();
                        }
                        intent = new Intent(SoundValueActivity.this, SoundActivity.class);
                        startActivity(intent);
                        MainActivityWear.soundRun = 1;
                    }
                });
            }
        });
    }
}
