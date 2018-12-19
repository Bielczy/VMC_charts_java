package com.example.bielczy.vmc_charts_java.db;


import android.arch.lifecycle.LiveData;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {
   // List<String> bla = new ArrayList<>();

    LineChart lineChart;
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
        //LiveData

        Disposable d = DB.getDatabase(getContext()).temperatureLogs().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TemperatureLog>>() {
                    @Override
                    public void accept(List<TemperatureLog> dane) throws Exception {
                        List<Entry> entries = new ArrayList<>();
                        for (TemperatureLog data : dane) {
                            entries.add(new Entry(data.uid, data.getTemperature()));
                        }

                        LineDataSet dataSet = new LineDataSet(entries, "℃");
                        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        dataSet.setValueTextColor(Color.parseColor("#ffffff"));

                                LineData lineData = new LineData(dataSet);
                                lineChart.setData(lineData);
                                lineChart.invalidate();
                    }
                });

    }
}
