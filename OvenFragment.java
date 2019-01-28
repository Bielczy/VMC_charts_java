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
import android.widget.RadioGroup;

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
public class OvenFragment extends Fragment {

    CheckBox cbMag1;
    CheckBox cbMag2;
    CheckBox cbMag1Power;
    CheckBox cbMag2Power;
    CheckBox cbLamp;
    CheckBox cbLampOn;
    CheckBox cbTick;
    CheckBox cbActual;
    Button btnChartOven;
    LineChart lineChart;
    DateRangeOven rangeOven;

    boolean cbMag1Checked;
    boolean cbMag2Checked;
    boolean cbMag1PowerChecked;
    boolean cbMag2PowerChecked;

    public static OvenFragment NewInstance(OvenFragment.DateRangeOven range){
        OvenFragment frag = new OvenFragment();
        frag.rangeOven = range;
        return frag;
    }
    public OvenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.oven_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnChartOven = (Button) view.findViewById(R.id.btnChartOven);
        cbMag1 = (CheckBox) view.findViewById(R.id.cbMag1);
        cbMag2 = (CheckBox) view.findViewById(R.id.cbMag2);
        cbMag1Power = (CheckBox) view.findViewById(R.id.cbMag1Power);
        cbMag2Power = (CheckBox) view.findViewById(R.id.cbMag2Power);
        cbLamp = (CheckBox) view.findViewById(R.id.cbLamp);
        cbLampOn = (CheckBox) view.findViewById(R.id.cbLampOn);
        cbTick = (CheckBox) view.findViewById(R.id.cbTick);
        cbActual = (CheckBox) view.findViewById(R.id.cbActual);
        lineChart = (LineChart) view.findViewById(R.id.chart);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        String startFormated = DateFormatter.toString(rangeOven.start);
        String stopFormated = DateFormatter.toString(rangeOven.end);

        Disposable d = DB.getDatabase(getContext()).ovenCurrentsLogs().getByDate(startFormated, stopFormated)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Consumer<List<OvenCurrentLog>>(){

                    @Override
                    public void accept(List<OvenCurrentLog> dane) throws Exception {
                         /*  btnChartOven.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawCharts(dane);
                            }
                        });*/
                        cbMag1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                cbMag1Checked = isChecked;
                                drawCharts(dane);
                            }
                        });

                        cbMag2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                cbMag2Checked = isChecked;
                                drawCharts(dane);
                            }
                        });

                        cbMag1Power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                cbMag1PowerChecked = isChecked;
                                drawCharts(dane);
                            }
                        });
                        cbMag2Power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                cbMag2PowerChecked = isChecked;
                                drawCharts(dane);
                            }
                        });

                    }
                    private void drawCharts(final List<OvenCurrentLog> dane) {

                        boolean cbMag1Checked = cbMag1.isChecked();
                        boolean cbMag2Checked = cbMag2.isChecked();
                        boolean cbMag1PowerChecked = cbMag1Power.isChecked();
                        boolean cbMag2PowerChecked = cbMag2Power.isChecked();

                        lineChart.setVisibility(View.VISIBLE);

                        List<Entry> mag1Entries = new ArrayList<>();
                        for (OvenCurrentLog data : dane) {
                            mag1Entries.add(new Entry(data.uid, data.getMag1()));
                        }

                        List<Entry> mag2Entries = new ArrayList<>();
                        for (OvenCurrentLog data : dane) {
                            mag2Entries.add(new Entry(data.uid, data.getMag2()));
                        }

                        List<Entry> mag1PowerEntries = new ArrayList<>();
                        for (OvenCurrentLog data : dane) {
                            mag1PowerEntries.add(new Entry(data.uid, data.getMag1Power()));
                        }

                        List<Entry> mag2PowerEntries = new ArrayList<>();
                        for (OvenCurrentLog data : dane) {
                            mag2PowerEntries.add(new Entry(data.uid, data.getMag2Power()));
                        }

                        LineDataSet mag1DataSet = new LineDataSet(mag1Entries, "Mag. 1");
                        mag1DataSet.setColors(new int[]{R.color.colorTemperature}, getContext());
                        mag1DataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        mag1DataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        mag1DataSet.setDrawCircles(false);

                        LineDataSet mag2DataSet = new LineDataSet(mag2Entries, "Mag. 2");
                        mag2DataSet.setColors(new int[]{R.color.colorHumidity}, getContext());
                        mag2DataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                        mag2DataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        mag2DataSet.setDrawCircles(false);

                        LineDataSet mag1PowerDataSet = new LineDataSet(mag1PowerEntries, "Mag. 1 Power");
                        mag1PowerDataSet.setColors(new int[]{R.color.colorFreezingDuration}, getContext());
                        mag1PowerDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        mag1PowerDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        mag1PowerDataSet.setDrawCircles(false);

                        LineDataSet mag2PowerDataSet = new LineDataSet(mag2PowerEntries, "Maag. 2 Power");
                        mag2PowerDataSet.setColors(new int[]{R.color.colorFreezingConsumption}, getContext());
                        mag2PowerDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                        mag2PowerDataSet.setValueTextColor(Color.parseColor("#ffffff"));
                        mag2PowerDataSet.setDrawCircles(false);

                        Legend legend = lineChart.getLegend();
                        legend.setEnabled(true);
                        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                        legend.setTextColor(Color.BLACK);

                        if (!cbMag1Checked && !cbMag2Checked && !cbMag1PowerChecked && !cbMag2PowerChecked){

                            lineChart.setVisibility(View.INVISIBLE);
                        }

// only mag1

                        if (cbMag1Checked && !cbMag2Checked && !cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1");
                            lineChart.invalidate();
                        }
//only mag2

                        if (!cbMag1Checked && cbMag2Checked && !cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag2DataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.2");
                            lineChart.invalidate();
                        }

// mag1 + mag2

                        if (cbMag1Checked && cbMag2Checked && !cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag2DataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.2");
                            lineChart.invalidate();
                        }

// only Mag1Power

                        if (!cbMag1Checked && !cbMag2Checked && cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 Power");
                            lineChart.invalidate();

                        }

// mag1+ Mag1Power

                        if (cbMag1Checked && !cbMag2Checked && cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag1PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.1 Power");
                            lineChart.invalidate();

                        }

// mag2 + Mag1Power

                        if (!cbMag1Checked && cbMag2Checked && cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag2DataSet);
                            dataSets.add(mag1PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.2 & Mag.1 Power");
                            lineChart.invalidate();
                        }

// only Mag2Power

                        if (!cbMag1Checked && !cbMag2Checked && !cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.2 Power");
                            lineChart.invalidate();

                        }

// mag1 + Mag2Power

                        if (cbMag1Checked && !cbMag2Checked && !cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.2 Power");
                            lineChart.invalidate();
                        }

// mag2 + Mag2Power

                        if (!cbMag1Checked && cbMag2Checked && !cbMag1PowerChecked && cbMag2PowerChecked) {

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag2DataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.2 & Mag.2 Power");
                            lineChart.invalidate();
                        }

// Mag1Power + Mag2Power

                        if (!cbMag1Checked && !cbMag2Checked && cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1PowerDataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 Power & Mag.2 Power");
                            lineChart.invalidate();

                        }

// mag1 + mag2 + Mag1Power

                        if (cbMag1Checked && cbMag2Checked && cbMag1PowerChecked && !cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag2DataSet);
                            dataSets.add(mag1PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.2 & Mag.1 Power.");
                            lineChart.invalidate();
                        }

// mag1 + mag2 + Mag2Power

                        if (cbMag1Checked && cbMag2Checked && !cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag2DataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.2 & Mag.2 Power.");
                            lineChart.invalidate();
                        }

// mag1 + Mag1Power + Mag2Power

                        if (cbMag1Checked && !cbMag2Checked && cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag1PowerDataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.1 Power. & Mag.2 Power.");
                            lineChart.invalidate();
                        }

// mag2 + Mag1Power + Mag2Power

                        if (!cbMag1Checked && cbMag2Checked && cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag2DataSet);
                            dataSets.add(mag1PowerDataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.2 & Mag.1 Power. & Mag.2 Power.");
                            lineChart.invalidate();
                        }

// mag1 + mag2 + Mag1Power + Mag2Power

                        if (cbMag1Checked && cbMag2Checked && cbMag1PowerChecked && cbMag2PowerChecked){

                            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                            dataSets.add(mag1DataSet);
                            dataSets.add(mag2DataSet);
                            dataSets.add(mag1PowerDataSet);
                            dataSets.add(mag2PowerDataSet);

                            LineData lineData = new LineData(dataSets);
                            lineChart.setData(lineData);
                            lineChart.getDescription().setText("Mag.1 & Mag.2 & Mag.1 Power. & Mag.2 Power.");
                            lineChart.invalidate();
                        }

                    }

                });
    }

    public static class DateRangeOven{

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
