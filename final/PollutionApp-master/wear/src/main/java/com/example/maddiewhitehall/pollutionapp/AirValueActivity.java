package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Color;

public class AirValueActivity extends Activity {

    private TextView mTextView;
    private ImageButton cloudView;
    private Intent intent;
    RelativeLayout[][] scale = new RelativeLayout[6][5];
    public static Activity num;
    int levelScale = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        num = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_value2);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);


                //sort this out please...
                scale[0][0] = (RelativeLayout) stub.findViewById(R.id.Grid1a);
                scale[0][1] = (RelativeLayout) stub.findViewById(R.id.Grid1b);
                scale[0][2] = (RelativeLayout) stub.findViewById(R.id.Grid1c);
                scale[0][3] = (RelativeLayout) stub.findViewById(R.id.Grid1d);
                scale[0][4] = (RelativeLayout) stub.findViewById(R.id.Grid1e);
                scale[1][0] = (RelativeLayout) stub.findViewById(R.id.Grid2a);
                scale[1][1] = (RelativeLayout) stub.findViewById(R.id.Grid2b);
                scale[1][2] = (RelativeLayout) stub.findViewById(R.id.Grid2c);
                scale[1][3] = (RelativeLayout) stub.findViewById(R.id.Grid2d);
                scale[1][4] = (RelativeLayout) stub.findViewById(R.id.Grid2e);
                scale[2][0] = (RelativeLayout) stub.findViewById(R.id.Grid3a);
                scale[2][1] = (RelativeLayout) stub.findViewById(R.id.Grid3b);
                scale[2][2] = (RelativeLayout) stub.findViewById(R.id.Grid3c);
                scale[2][3] = (RelativeLayout) stub.findViewById(R.id.Grid3d);
                scale[2][4] = (RelativeLayout) stub.findViewById(R.id.Grid3e);
                scale[3][0] = (RelativeLayout) stub.findViewById(R.id.Grid4a);
                scale[3][1] = (RelativeLayout) stub.findViewById(R.id.Grid4b);
                scale[3][2] = (RelativeLayout) stub.findViewById(R.id.Grid4c);
                scale[3][3] = (RelativeLayout) stub.findViewById(R.id.Grid4d);
                scale[3][4] = (RelativeLayout) stub.findViewById(R.id.Grid4e);
                scale[4][0] = (RelativeLayout) stub.findViewById(R.id.Grid5a);
                scale[4][1] = (RelativeLayout) stub.findViewById(R.id.Grid5b);
                scale[4][2] = (RelativeLayout) stub.findViewById(R.id.Grid5c);
                scale[4][3] = (RelativeLayout) stub.findViewById(R.id.Grid5d);
                scale[4][4] = (RelativeLayout) stub.findViewById(R.id.Grid5e);
                scale[5][0] = (RelativeLayout) stub.findViewById(R.id.Grid6a);
                scale[5][1] = (RelativeLayout) stub.findViewById(R.id.Grid6b);
                scale[5][2] = (RelativeLayout) stub.findViewById(R.id.Grid6c);
                scale[5][3] = (RelativeLayout) stub.findViewById(R.id.Grid6d);
                scale[5][4] = (RelativeLayout) stub.findViewById(R.id.Grid6e);


                //make a method for this.. public void createScale(int scaleNum, RelativeLayout scale[int n][])
                for (int n = 0; n < scale.length; n++) {
                    for (int k = 0; k < levelScale; k++) {
                        scale[n][k].setBackgroundColor(Color.rgb(0, 0, 0));
                    }
                    levelScale++;
                }


                cloudView = (ImageButton) stub.findViewById(R.id.cloudButton);
                cloudView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        if(MainActivityWear.airRun==1){
                            AirActivity.level.finish();
                        }
                        intent = new Intent(AirValueActivity.this, AirActivity.class);
                        startActivity(intent);
                        MainActivityWear.airRun = 1;
                    }
                });

            }
        });

    }
}




