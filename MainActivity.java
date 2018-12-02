package com.example.bielczy.vmc_charts_java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.bielczy.vmc_charts_java.db.CommonFragment;
import com.example.bielczy.vmc_charts_java.db.SpecificFragment;

public class MainActivity extends AppCompatActivity {
    boolean isCommonFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new CommonFragment())
                .commit();
    }

    public void onSpecificBtnClick(View view) {
        if (!isCommonFragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new SpecificFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new CommonFragment())
                    .commit();
        }
        isCommonFragment = true;
    }

    public void onCommonBtnClick(View view) {
        if(isCommonFragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new CommonFragment())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new SpecificFragment())
                    .commit();
        }
        isCommonFragment = !isCommonFragment;
    }
}
