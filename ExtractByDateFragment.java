package com.example.bielczy.vmc_charts_java.db;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bielczy.vmc_charts_java.R;

import java.util.function.Function;


/**
 * A simple {@link Fragment} subclass.
 */
 public  class ExtractByDateFragment extends Fragment{

    TextView start_date, stop_date, start_time, stop_time;
    Button btnDateStart, btnDateStop, btnTimeStart, btnTimeStop;

    private TemperatureFragment.DateRangeTemperature rangeTemperature = new TemperatureFragment.DateRangeTemperature();
    private OvenFragment.DateRangeOven rangeOven = new OvenFragment.DateRangeOven();

    private String start, end;
    private String timeStart, timeStop;

   /* @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("start_date", start_date);
        outState.putString("stop_date", stop_date);
        outState.putString("start_time", start_time);
        outState.putString("stop_time", stop_time);
    }*/

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        start_date = savedInstanceState.getString("start_date");
        stop_date = savedInstanceState.getString("stop_date");
        start_time = savedInstanceState.getString("start_time");
        stop_time = savedInstanceState.getString("stop_time");
    }*/


    public ExtractByDateFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.extract_by_date_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDateStart = view.findViewById(R.id.btnDateStart);
        btnDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateStartPickerDialog();
            }
        });

        btnDateStop = view.findViewById(R.id.btnDateStop);
        btnDateStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateStopPickerDialog();
            }
        });

       btnTimeStart = view.findViewById(R.id.btnTimeStart);
        btnTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeStartPickerDialog();
            }
        });

        btnTimeStop = view.findViewById(R.id.btnTimeStop);
        btnTimeStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeStopPickerDialog();
            }
        });



        view.findViewById(R.id.btnShowTemperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.extractByDateMainContiner, TemperatureFragment.NewInstance(rangeTemperature))
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.btnShowOven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.extractByDateMainContiner, OvenFragment.NewInstance(rangeOven))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void showDateStartPickerDialog() {
        DatePickerStartFragment newFragmentDataStart = new DatePickerStartFragment();
        newFragmentDataStart.onDateStartSelectedCallback = new DatePickerStartFragment.OnDateStartSelected() {
            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {
                rangeTemperature.setStartDate(year, month, day);
                rangeOven.setStartDate(year, month, day);
            }
        };
        newFragmentDataStart.show(getActivity().getSupportFragmentManager(), "datePickerStart");
    }

    public void showDateStopPickerDialog() {
        DatePickerStopFragment newFragmentDataStop = new DatePickerStopFragment();
        newFragmentDataStop.onDataStopSelectedCallback = new DatePickerStopFragment.OnDateStopSelected(){

            @Override
            public void onSelected(DatePicker view, int year, int month, int day) {
                rangeTemperature.setStopDate(year, month, day);
                rangeOven.setStopDate(year, month, day);
            }
        };
        newFragmentDataStop.show(getActivity().getSupportFragmentManager(), "datePickerStop");
    }

    public void showTimeStartPickerDialog() {
        TimePickerStartFragment newFragmentTimeStart = new TimePickerStartFragment();
        newFragmentTimeStart.onTimeStartSelectedCallback = new TimePickerStartFragment.onTimeStartSelected(){

            @Override
            public void onSelected(TimePicker view, int hrs, int min) {
                rangeTemperature.setStartTime(hrs, min);
                rangeOven.setStartTime(hrs, min);
            }
        };
        newFragmentTimeStart.show(getActivity().getSupportFragmentManager(), "timePickerStart");
    }

    public void showTimeStopPickerDialog() {
        TimePickerStopFragment newFragmentTimeStop = new TimePickerStopFragment();
        newFragmentTimeStop.onTimeStopSelectedCallback = new TimePickerStopFragment.onTimeStopSelected() {
            @Override
            public void onSelected(TimePicker view, int hrs, int min) {
                rangeTemperature.setStopTime(hrs, min);
                rangeOven.setStopTime(hrs, min);
            }
        };
        newFragmentTimeStop.show(getActivity().getSupportFragmentManager(), "timePickerStop");
    }

}
