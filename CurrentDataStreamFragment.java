package com.example.bielczy.vmc_charts_java.db;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bielczy.vmc_charts_java.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentDataStreamFragment extends Fragment {


    public CurrentDataStreamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.current_data_stream_fragment, container, false);
    }

}
