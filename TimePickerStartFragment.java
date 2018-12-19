package com.example.bielczy.vmc_charts_java.db;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bielczy.vmc_charts_java.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerStartFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    TextView start_time;
    Button btnTimeStart;
    public TimePickerStartFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {

        start_time = (TextView) getActivity().findViewById(R.id.start_time);
        btnTimeStart = (Button) getActivity().findViewById(R.id.btnTimeStart);
//
        start_time.setText(hour + " : " + minute);
    }
}

