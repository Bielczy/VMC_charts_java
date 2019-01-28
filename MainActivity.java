package com.example.bielczy.vmc_charts_java;


import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.bielczy.vmc_charts_java.db.DatePickerStopFragment;
import com.example.bielczy.vmc_charts_java.db.DatePickerStartFragment;
import com.example.bielczy.vmc_charts_java.db.ExtractByDateFragment;
import com.example.bielczy.vmc_charts_java.db.TimePickerStartFragment;
import com.example.bielczy.vmc_charts_java.db.TimePickerStopFragment;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Button showTemperatureButton = (Button)findViewById(R.id.btnTemperature);
        Button showOvenButton = (Button)findViewById(R.id.btnOven);

        showTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.buttons_container, new ExtractByDateFragment())
                        .addToBackStack(null)
                        .commit();
            }

        });

        showOvenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.buttons_container, new ExtractByDateFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

}
