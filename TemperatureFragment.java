package com.example.bielczy.vmc_charts_java.db;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.bielczy.vmc_charts_java.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

    CheckBox cbTemperature;
    CheckBox cbHumidity;

    DateRange range;

    LineChart lineChart;
    BarChart barChart;

    String[] labels = new String[] {"Temperature", "Humidity", "Freez. Duration", "Freez. Consumption"};
    private Object log;

   /* boolean temperatureChecked;
    boolean humidityChecked;
    boolean durationChecked;
    boolean consumptionChecked;*/

    public static TemperatureFragment NewInstance(DateRange range){
        TemperatureFragment fr = new TemperatureFragment();
        fr.range = range;
        return fr;
    }

    public TemperatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tempeature_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cbHumidity = (CheckBox)view.findViewById(R.id.cbHumidity);
        cbTemperature = (CheckBox)view.findViewById(R.id.cbTemperature);
        lineChart = (LineChart) view.findViewById(R.id.chart);
        barChart = (BarChart) view.findViewById(R.id.barChart);
        setRetainInstance(true);

        cbTemperature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    return;
               // temperatureChecked = isChecked;
               // drawCharts(temperatureChecked, humidityChecked, durationChecked, consumptionChecked);
            }
        });

        cbHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    return;
            }
        });

    }

   //private void drawCharts(boolean temperatureChecked, boolean humidityChecked, boolean durationChecked, boolean consumptionChecked) {

   // }

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

                if((cbTemperature.isChecked()) && (!cbHumidity.isChecked())){

                    barChart.setVisibility(View.INVISIBLE);
                    lineChart.setVisibility(View.VISIBLE);

                    List<Entry> temperatureEntries = new ArrayList<>();
                    for (TemperatureLog data : dane) {
                        temperatureEntries.add(new Entry(data.uid, data.getTemperature()));
                    }

                    LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "℃");
                    temperatureDataSet.setColors(ColorTemplate.getHoloBlue());
                    temperatureDataSet.setValueTextColor(Color.parseColor("#ffffff"));

                    Legend legend = lineChart.getLegend();
                    legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                    legend.setTextColor(Color.BLACK);
                    legend.setExtra(ColorTemplate.MATERIAL_COLORS, labels);

                    LineData lineData = new LineData(temperatureDataSet);
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                }

                else if ((cbHumidity.isChecked()) && (!cbTemperature.isChecked())){

                    barChart.setVisibility(View.INVISIBLE);
                    lineChart.setVisibility(View.VISIBLE);

                    List<Entry> humidityEntries = new ArrayList<>();
                    for (TemperatureLog data : dane) {
                        humidityEntries.add(new Entry(data.uid, data.getHumidity()));
                    }

                    LineDataSet humidityDataSet = new LineDataSet(humidityEntries, "℃");
                    humidityDataSet.setColors(ColorTemplate.getHoloBlue());
                    humidityDataSet.setValueTextColor(Color.parseColor("#ffffff"));

                    Legend legend = lineChart.getLegend();
                    legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                    legend.setTextColor(Color.BLACK);
                    legend.setExtra(ColorTemplate.MATERIAL_COLORS, labels);

                    LineData lineData = new LineData(humidityDataSet);
                    lineChart.setData(lineData);
                    lineChart.invalidate();

                }

                else if((cbTemperature.isChecked()) && (!cbHumidity.isChecked())){

                    lineChart.setVisibility(View.INVISIBLE);
                    barChart.setVisibility(View.VISIBLE);

                   /* YourData[] group1 = ...;
                    YourData[] group2 = ...;*/

                    List<BarEntry> temperatureEntries = new ArrayList<>();
                    List<BarEntry> humidityEntries = new ArrayList<>();


                    for(TemperatureLog data : dane) {
                        temperatureEntries.add(new BarEntry(data.uid, data.getTemperature()));
                        humidityEntries.add(new BarEntry(data.uid, data.getHumidity()));
                    }

                    BarDataSet temperatureDataSet = new BarDataSet(temperatureEntries, "Temp.");
                    BarDataSet humidityDataSet = new BarDataSet(humidityEntries, "Humidity");

                    float groupSpace = 0.06f;
                    float barSpace = 0.02f;
                    float barWidth = 0.45f;

                    BarData data = new BarData(temperatureDataSet, humidityDataSet);
                    data.setBarWidth(barWidth); // set the width of each bar
                    barChart.setData(data);
                    barChart.groupBars(1980f, groupSpace, barSpace); // perform the "explicit" grouping
                    barChart.invalidate();
                }
                    }
                });

    }

    public static class DateRange{


        public Date start = new Date();
        public Date end = new Date();


        void setStartDate(int year, int month, int date) {
            start.setYear(year);
            start.setMonth(month);
            start.setDate(date);
        }

        void setStopDate(int year, int month, int date) {
            end.setYear(year);
            end.setMonth(month);
            end.setDate(date);
        }

        void setStartTime(int hrs, int min){
            start.setHours(hrs);
            start.setMinutes(min);

        }

        void setStopTime(int hrs, int min){
            end.setHours(hrs);
            end.setMinutes(min);
        }

    }
}
