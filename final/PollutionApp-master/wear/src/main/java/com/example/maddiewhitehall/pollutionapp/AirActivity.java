package com.example.maddiewhitehall.pollutionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
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
    //raw airData


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


                //sets each text view into array
                view[0] = (TextView) stub.findViewById(R.id.text1);
                view[1] = (TextView) stub.findViewById(R.id.text2);
                view[2] = (TextView) stub.findViewById(R.id.text3);
                view[3] = (TextView) stub.findViewById(R.id.text4);
                view[4] = (TextView) stub.findViewById(R.id.text5);
                view[5] = (TextView) stub.findViewById(R.id.text6);

                //insert value into data type
                for(int i=0;i<6;i++) {
                    String input = MainActivityWear.airData[i][1];
                    Double in = Double.parseDouble(input);

                    if(in==0.0){
                        input="-";
                    }
                    view[i].setText(input);
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
    }
}
