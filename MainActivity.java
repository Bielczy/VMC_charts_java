package com.example.bielczy.vmc_charts_java;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bielczy.vmc_charts_java.db.CurrentDataStreamFragment;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.current_data_stream:
                onCurrentTemperatureClick();
                return true;
            case R.id.extract_by_date:
                onExtractTemperatureClick();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void onCurrentTemperatureClick() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.action_container, new CurrentDataStreamFragment())
                        .commit();
    }
    private void onExtractTemperatureClick() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.action_container, new ExtractByDateFragment())
                .commit();
    }
}
