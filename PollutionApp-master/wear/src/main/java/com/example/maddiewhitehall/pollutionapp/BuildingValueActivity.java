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

public class BuildingValueActivity extends Activity {

    private TextView mTextView;
    private ImageButton lightButton;
    private Intent intent;
    RelativeLayout [] scale = new RelativeLayout[5];
    public static Activity num;
    int uvLevel = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        num = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_value);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                scale[0] = (RelativeLayout) stub.findViewById(R.id.Grid1a);
                scale[1] = (RelativeLayout) stub.findViewById(R.id.Grid1b);
                scale[2] = (RelativeLayout) stub.findViewById(R.id.Grid1c);
                scale[3] = (RelativeLayout) stub.findViewById(R.id.Grid1d);
                scale[4] = (RelativeLayout) stub.findViewById(R.id.Grid1e);

                for (int n = 0; n < uvLevel; n++) {
                    scale[n].setBackgroundColor(Color.rgb(0, 0, 0));
                }


                lightButton = (ImageButton) stub.findViewById(R.id.lightButton);
                lightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        if(MainActivityWear.lightRun==1){
                            BuildingActivity.level.finish();
                        }
                        intent = new Intent(BuildingValueActivity.this, BuildingActivity.class);
                        startActivity(intent);
                        MainActivityWear.lightRun = 1;
                    }
                });

            }
        });
    }
}
