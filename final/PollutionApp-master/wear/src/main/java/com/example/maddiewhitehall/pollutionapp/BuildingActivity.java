package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class BuildingActivity extends Activity {

    private TextView LUXReading;
    private ImageButton button;
    private Intent intent;
    Float incomingUV = 0.004f;

    public static Activity level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        level = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                LUXReading = (TextView) stub.findViewById(R.id.light_reading);

                LUXReading.setText(Float.toString(incomingUV));

                button = (ImageButton) stub.findViewById(R.id.lightButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuildingValueActivity.num.finish();
                        intent = new Intent(BuildingActivity.this, BuildingValueActivity.class);
                        startActivity(intent);
                    }

                });
            }
        });
    }
}
