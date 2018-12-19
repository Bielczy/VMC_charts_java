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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Button showTemperatureButton = (Button)findViewById(R.id.btnTemperature);
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

    }

    public void showDateStartPickerDialog(View view) {
        DatePickerStartFragment newFragmentDataStart = new DatePickerStartFragment();
        newFragmentDataStart.onDateStartSelectedCallback = new DatePickerStartFragment.onDateStartSelected() {
            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {

            }
        };

        newFragmentDataStart.show(getSupportFragmentManager(), "datePickerStart");
    }

    public void showDateStopPickerDialog(View view) {
        DatePickerStopFragment newFragmentDataStop = new DatePickerStopFragment();
        newFragmentDataStop.onDataStopSelectedCallback = new DatePickerStopFragment.onDateStopSelected(){

            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {

            }
        };
        newFragmentDataStop.show(getSupportFragmentManager(), "datePickerStop");
    }

    public void showTimeStartPickerDialog(View v) {
        TimePickerStartFragment newFragmentTimeStart = new TimePickerStartFragment();
        newFragmentTimeStart.onTimeStartSelectedCallback = new TimePickerStartFragment.onTimeStartSelected(){

            @Override
            public void onSelected(TimePicker view, int hour, int minute) {

            }
        };
        newFragmentTimeStart.show(getSupportFragmentManager(), "timePickerStart");
    }

    public void showTimeStopPickerDialog(View v) {
        TimePickerStopFragment newFragmentTimeStop = new TimePickerStopFragment();
        newFragmentTimeStop.onTimeStopSelectedCallback = new TimePickerStopFragment.onTimeStopSelected() {
            @Override
            public void onSelected(TimePicker view, int hour, int minute) {

            }
        };

        newFragmentTimeStop.show(getSupportFragmentManager(), "timePickerStop");
    }


}
