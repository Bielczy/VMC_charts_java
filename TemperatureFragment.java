package com.example.bielczy.vmc_charts_java.db;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.bielczy.vmc_charts_java.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
    CheckBox cbFreezDuration;
    CheckBox cbFreezConsump;
    Button btnChartTemperature;
    DateRangeTemperature rangeTemperature;
    LineChart lineChart;


    private Object log;

    boolean temperatureChecked;
    boolean humidityChecked;
    boolean durationChecked;
    boolean consumptionChecked;

    public static TemperatureFragment NewInstance(DateRangeTemperature range){
        TemperatureFragment fr = new TemperatureFragment();
        fr.rangeTemperature = range;
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

        btnChartTemperature = (Button) view.findViewById(R.id.btnChartTemperature);

        cbHumidity = (CheckBox) view.findViewById(R.id.cbHumidity);
        cbTemperature = (CheckBox) view.findViewById(R.id.cbTemperature);
        cbFreezDuration = (CheckBox) view.findViewById(R.id.cbFreezDuration);
        cbFreezConsump = (CheckBox) view.findViewById(R.id.cbFreezConsump);
        lineChart = (LineChart) view.findViewById(R.id.chart);
        setRetainInstance(true);


    }


    @Override
    public void onStart() {
        super.onStart();

        String startFormated = DateFormatter.toString(rangeTemperature.start);
        String stopFormated = DateFormatter.toString(rangeTemperature.end);

        Disposable d = DB.getDatabase(getContext()).temperatureLogs().getByDate(startFormated, stopFormated)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TemperatureLog>>() {


                    @Override
                    public void accept(final List<TemperatureLog> dane) throws Exception {

                      /*  btnChartTemperature.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawCharts(dane);
                            }
                        });*/
                        cbTemperature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                temperatureChecked = isChecked;
                                drawCharts(dane);
                            }
                        });

                        cbHumidity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                humidityChecked = isChecked;
                                drawCharts(dane);
                            }
                        });

                        cbFreezDuration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                durationChecked = isChecked;
                                drawCharts(dane);
                            }
                        });
                        cbFreezConsump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                consumptionChecked = isChecked;
                                drawCharts(dane);
                            }
                        });

                    }

                    private void drawCharts(final List<TemperatureLog> dane) {

                        boolean temperatureChecked = cbTemperature.isChecked();
                        boolean humidityChecked = cbHumidity.isChecked();
                        boolean freezDurationChecked = cbFreezDuration.isChecked();
                        boolean consumptionChecked = cbFreezConsump.isChecked();

                        lineChart.setVisibility(View.VISIBLE);

                        List<Entry> temperatureEntries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            temperatureEntries.add(new Entry(data.uid, data.getTemperature()));
                        }

                        List<Entry> humidityEntries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            humidityEntries.add(new Entry(data.uid, data.getHumidity()));
                        }

                        List<Entry> freezDurationEntries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            freezDurationEntries.add(new Entry(data.uid, data.getFreezingDurationS()));
                        }

                        List<Entry> freezConsumptionEntries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            freezConsumptionEntries.add(new Entry(data.uid, data.getFreezingCurrentConsumption()));
                        }

                        LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "℃");
                        temperatureDataSet.setColors(new int []{R.color.colorTemperature}, getContext());
                        temperatureDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        temperatureDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        temperatureDataSet.setDrawCircles(false);

                        LineDataSet humidityDataSet = new LineDataSet(humidityEntries, "% hum.");
                        humidityDataSet.setColors(new int []{R.color.colorHumidity}, getContext());
                        humidityDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                        humidityDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        humidityDataSet.setDrawCircles(false);

                        LineDataSet freezDurationDataSet = new LineDataSet(freezDurationEntries, "Freezing [s]");
                        freezDurationDataSet.setColors(new int[]{R.color.colorFreezingDuration}, getContext());
                        freezDurationDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        freezDurationDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        freezDurationDataSet.setDrawCircles(false);

                        LineDataSet freezConsumptionDataSet = new LineDataSet(freezConsumptionEntries, "Freezing [s]");
                        freezConsumptionDataSet.setColors(new int[]{R.color.colorFreezingConsumption}, getContext());
                        freezConsumptionDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                        freezConsumptionDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        freezConsumptionDataSet.setDrawCircles(false);

                        Legend legend = lineChart.getLegend();
                        legend.setEnabled(true);
                        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                        legend.setTextColor(Color.BLACK);

                        if (!temperatureChecked && !humidityChecked && !freezDurationChecked && !consumptionChecked){

                            lineChart.setVisibility(View.INVISIBLE);
                        }

// only Temperature

                       if (temperatureChecked && !humidityChecked && !freezDurationChecked && !consumptionChecked){

                           ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                           dataSets.add(temperatureDataSet);

                           LineData lineData = new LineData(dataSets);
                           lineChart.setData(lineData);
                           lineChart.getDescription().setText("Temperature");
                           lineChart.invalidate();
                       }
//only Humidity

                        if (!temperatureChecked && humidityChecked && !freezDurationChecked && !consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(humidityDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Huminidity");
                            lineChart.invalidate();
                        }

// Temperature + Humidity

                        if (temperatureChecked && humidityChecked && !freezDurationChecked && !consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(humidityDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Huminidity");
                            lineChart.invalidate();
                        }

// only FreezingDuration

                        if (!temperatureChecked && !humidityChecked && freezDurationChecked && !consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(freezDurationDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Freezing");
                            lineChart.invalidate();

                        }

// Temperature + FreezingDuration

                        if (temperatureChecked && !humidityChecked && freezDurationChecked && !consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(freezDurationDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Freezing");
                            lineChart.invalidate();

                        }

// Humidity + FreezingDuration

                        if (!temperatureChecked && humidityChecked && freezDurationChecked && !consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(humidityDataSet);
                            dataSets.add(freezDurationDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Humidity & Freezing");
                            lineChart.invalidate();
                        }

// only FreezingConsumption

                        if (!temperatureChecked && !humidityChecked && !freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Freezing Cons.");
                            lineChart.invalidate();

                        }

// Temperature + FreezingConsumption

                        if (temperatureChecked && !humidityChecked && !freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Freezing Cons.");
                            lineChart.invalidate();
                        }

// Humidity + FreezingConsumption

                        if (!temperatureChecked && humidityChecked && !freezDurationChecked && consumptionChecked) {

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(humidityDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Humidity & Freezing Cons.");
                            lineChart.invalidate();
                        }

// FreezingDuration + FreezingConsumption

                        if (!temperatureChecked && !humidityChecked && freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(freezDurationDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Freezing & Freezing Cons.");
                            lineChart.invalidate();

                        }

// Temperature + Humidity + FreezingDuration

                        if (temperatureChecked && humidityChecked && freezDurationChecked && !consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(humidityDataSet);
                            dataSets.add(freezDurationDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Humidity & Freez.");
                            lineChart.invalidate();
                        }

 // Temperature + Humidity + FreezingConsumption

                        if (temperatureChecked && humidityChecked && !freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(humidityDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Humidity & Freezing Cons.");
                            lineChart.invalidate();
                        }

// Temperature + FreezingDuration + FreezingConsumption

                        if (temperatureChecked && !humidityChecked && freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(freezDurationDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Freez. & Freezing Cons.");
                            lineChart.invalidate();
                        }

// Humidity + FreezingDuration + FreezingConsumption

                        if (!temperatureChecked && humidityChecked && freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(humidityDataSet);
                            dataSets.add(freezDurationDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Humidity & Freez. & Freezing Cons.");
                            lineChart.invalidate();
                        }

// Temperature + Humidity + FreezingDuration +  FreezingConsumption

                        if (temperatureChecked && humidityChecked && freezDurationChecked && consumptionChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(temperatureDataSet);
                            dataSets.add(humidityDataSet);
                            dataSets.add(freezDurationDataSet);
                            dataSets.add(freezConsumptionDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Temperature & Humidity & Freez. & Freezing Cons.");
                            lineChart.invalidate();
                        }

                    }

                });

    }

    public static class DateRangeTemperature{

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
