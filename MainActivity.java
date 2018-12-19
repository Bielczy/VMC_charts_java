package com.example.bielczy.vmc_charts_java;


import android.content.pm.ActivityInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import com.example.bielczy.vmc_charts_java.db.DataPickerStopFragment;
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








    public void onRangeClick(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.LineChartTemperature, new ExtractByDateFragment())
                .commit();
    }

    public void showDateStartPickerDialog(View view) {
        DatePickerStartFragment newFragment = new DatePickerStartFragment();
        newFragment.onDateSelectedCallback = new DatePickerStartFragment.OnDateSelected() {
            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {

            }
        };

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
