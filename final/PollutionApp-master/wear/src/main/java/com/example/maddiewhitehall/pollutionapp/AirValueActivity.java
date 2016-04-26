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

    int [][] gridLayout = {{R.id.Grid1a,R.id.Grid1b,R.id.Grid1c,R.id.Grid1d,R.id.Grid1e,R.id.Grid1f,R.id.Grid1g,R.id.Grid1h,R.id.Grid1i,R.id.Grid1j},{R.id.Grid2a,R.id.Grid2b,R.id.Grid2c,R.id.Grid2d,R.id.Grid2e,R.id.Grid2f,R.id.Grid2g,R.id.Grid2h,R.id.Grid2i,R.id.Grid2j},
            {R.id.Grid3a,R.id.Grid3b,R.id.Grid3c,R.id.Grid3d,R.id.Grid3e,R.id.Grid3f,R.id.Grid3g,R.id.Grid3h,R.id.Grid3i,R.id.Grid3j},{R.id.Grid4a,R.id.Grid4b,R.id.Grid4c,R.id.Grid4d,R.id.Grid4e,R.id.Grid4f,R.id.Grid4g,R.id.Grid4h,R.id.Grid4i,R.id.Grid4j},
                    {R.id.Grid5a,R.id.Grid5b,R.id.Grid5c,R.id.Grid5d,R.id.Grid5e,R.id.Grid5f,R.id.Grid5g,R.id.Grid5h,R.id.Grid5i,R.id.Grid5j},{R.id.Grid6a,R.id.Grid6b,R.id.Grid6c,R.id.Grid6d,R.id.Grid6e,R.id.Grid6f,R.id.Grid6g,R.id.Grid6h,R.id.Grid6i,R.id.Grid6j}};

    public static Activity num;
    int airLevel = 0;

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

                for(int b=0;b<scale.length;b++){
                    for(int c=0;c<scale[b].length;c++){
                        scale[b][c] = (RelativeLayout) stub.findViewById(gridLayout[b][c]);
                    }
                }


                //make a method for this.. public void createScale(int scaleNum, RelativeLayout scale[int n][])
                for (int n = 0; n < scale.length; n++) {
                    airLevel = (int)(Double.parseDouble(MainActivityWear.airData[n][0]));
                    for (int k = 0; k < airLevel; k++) {
                        int colour = 0;

                        if(airLevel<3){
                            colour = MainActivityWear.low;
                        }
                        else if(airLevel>7){
                            colour = MainActivityWear.high;
                        }
                        else{
                            colour = MainActivityWear.medium;
                        }

                        scale[n][k].setBackgroundColor(colour);
                    }
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




