package com.example.bielczy.vmc_charts_java.db;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bielczy.vmc_charts_java.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {
 /*  // List<String> bla = new ArrayList<>();
//--------------------------------------------------------------------------------------------
    String start, end;
    Single<List<TemperatureLog>> temperatureLogs;
    TextView start_date, stop_date;

    public void setTemperatureLogs(Single<List<TemperatureLog>> temperatureLogs) {
        this.temperatureLogs = temperatureLogs;

        start = start_date.getText().toString();
        end = stop_date.getText().toString();
    }

//-------------------------------------------------------------------------------------------------------*/


    DateRange range;

    LineChart lineChart;
    String[] labels = new String[] {"Temperature", "Humidity", "Freez. Duration", "Freez. Consumption"};
    private Object log;

    public static TemperatureFragment NewInstance(DateRange range){
        TemperatureFragment fr = new TemperatureFragment();
        fr.range = range;
        return fr;
    }

    public TemperatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tempeature_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lineChart = (LineChart) getActivity().findViewById(R.id.chart);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        String startFormated = DateFormatter.toString(range.start);
        String stopFormated = DateFormatter.toString(range.end);

        Disposable d = DB.getDatabase(getContext()).temperatureLogs().getByDate(startFormated, stopFormated)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TemperatureLog>>() {
                    @Override
                    public void accept(List<TemperatureLog> dane) throws Exception {
                       int i =0;
                       i = 100;

                        //range.start_date;

                        //DateFormatter.toString()

                        List<Entry> entries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            entries.add(new Entry(data.uid, data.getTemperature()));
                        }

                        LineDataSet dataSet = new LineDataSet(entries, "℃");
                        dataSet.setColors(ColorTemplate.getHoloBlue());
                        dataSet.setValueTextColor(Color.parseColor("#ffffff"));

                        Legend legend = lineChart.getLegend();
                        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                        legend.setTextColor(Color.BLACK);
                        legend.setExtra(ColorTemplate.MATERIAL_COLORS, labels);

                        LineData lineData = new LineData(dataSet);
                        lineChart.setData(lineData);
                        lineChart.invalidate();
                    }
                });

    }

    public static class DateRange{


        public Date start = new Date();
        public Date end = new Date();


        public void setStartDate(int year, int month, int date) {
            start.setYear(year);
            start.setMonth(month);
            start.setDate(date);
        }

        public void setStopDate(int year, int month, int date) {
            end.setYear(year);
            end.setMonth(month);
            end.setDate(date);
        }

    }
}
