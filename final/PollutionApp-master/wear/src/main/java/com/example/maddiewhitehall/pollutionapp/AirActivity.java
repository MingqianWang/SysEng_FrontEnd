package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ScrollView;
import android.graphics.Color;

public class AirActivity extends Activity {

    private TextView mTextView,a,b,c,d,e,f;
    private TextView view[]= {a,b,c,d,e,f};
    private ImageButton cloudView;
    private Intent intentValue;
    public static Activity level;
    Float incomingAir[]={3.12f,0.23522f,3.44f,2.3f,0.6f,0.04f};
    CharSequence airValues[]={"","","","","",""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        level = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                //converts data into string so can be displayed
                //PUT IN OWN METHOD
                for (int j = 0; j < incomingAir.length; j++) {
                    airValues[j] = Float.toString(incomingAir[j]);
                }

                //sets each text view into array
                view[0] = (TextView) stub.findViewById(R.id.text1);
                view[1] = (TextView) stub.findViewById(R.id.text2);
                view[2] = (TextView) stub.findViewById(R.id.text3);
                view[3] = (TextView) stub.findViewById(R.id.text4);
                view[4] = (TextView) stub.findViewById(R.id.text5);
                view[5] = (TextView) stub.findViewById(R.id.text6);

                //insert value into data type
                for (int i = 0; i < 5; i++) {
                    view[i].setText(airValues[i]);
                }


                cloudView = (ImageButton) stub.findViewById(R.id.cloudButton);
                cloudView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AirValueActivity.num.finish();
                        intentValue = new Intent(AirActivity.this, AirValueActivity.class);
                        startActivity(intentValue);
                    }

                });
            }
        });
//        String[] data = getIntent().getStringArrayExtra("dataMap");
//        if(data != null) {
//            Log.v("MainActivityAir", "value:" + data[0]);
//        }
    }
}
