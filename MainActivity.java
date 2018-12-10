package com.example.bielczy.vmc_charts_java;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.bielczy.vmc_charts_java.db.TemperatureFragment;
import com.example.bielczy.vmc_charts_java.db.ExtractByDateFragment;
import com.example.bielczy.vmc_charts_java.db.StartFragment;

public class MainActivity extends AppCompatActivity {
    boolean isCommonFragment = true;

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
}
