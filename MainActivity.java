package com.example.bielczy.vmc_charts_java;


import android.content.pm.ActivityInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bielczy.vmc_charts_java.db.DataPickerStopFragment;
import com.example.bielczy.vmc_charts_java.db.DatePickerStartFragment;
//import com.example.bielczy.vmc_charts_java.db.DatePickerStopFragment;
import com.example.bielczy.vmc_charts_java.db.TemperatureFragment;
import com.example.bielczy.vmc_charts_java.db.ExtractByDateFragment;
import com.example.bielczy.vmc_charts_java.db.StartFragment;
import com.example.bielczy.vmc_charts_java.db.TimePickerStartFragment;
import com.example.bielczy.vmc_charts_java.db.TimePickerStopFragment;
//import com.example.bielczy.vmc_charts_java.db.TimePickerStopFragmentFragment;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.action_container, new StartFragment())
                .commit();

    }

    public void onTemperatureClick(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.action_container, new TemperatureFragment())
                .commit();
    }

    public void onRangeClick(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.LineChartTemperature, new ExtractByDateFragment())
                .commit();
    }

    public void showDateStartPickerDialog(View view) {
        DialogFragment newFragment = new DatePickerStartFragment();
        newFragment.show(getSupportFragmentManager(), "datePickerStart");

    }
    public void showTimeStartPickerDialog(View v) {
        DialogFragment newFragment = new TimePickerStartFragment();
        newFragment.show(getSupportFragmentManager(), "timePickerStart");
    }
    public void showDateStopPickerDialog(View view) {
        DialogFragment newFragment = new DataPickerStopFragment();
        newFragment.show(getSupportFragmentManager(), "datePickerStop");

    }
    public void showTimeStopPickerDialog(View v) {
        DialogFragment newFragment = new TimePickerStopFragment();
        newFragment.show(getSupportFragmentManager(), "timePickerStop");
    }


}
//TODO wyjaśnić dlaczego klasy Stop dla pickerów nie są używane
//porównać deklaracje metod start i stop
