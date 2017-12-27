package com.bitcode.hardik.profilemanager.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.EventData;
import com.bitcode.hardik.profilemanager.R;
import com.bitcode.hardik.profilemanager.Service1;

import java.util.ArrayList;

public class FragmentAddEvent extends Fragment {

    private TextView mTxtDate,mTxtFromTime,mTxtToTime,mTxtProfile,mTxtViewDate,mTxtViewFromTime,mTxtViewToTime;
    private Spinner mSpinner;
    int mDay,mMonth,mYears,mMin,mHour,Tmin,Thour;

    EditText mEdtEventName;
    String mProfileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=  inflater.inflate(R.layout.fragment_add_event,null);



        mEdtEventName = (EditText) view.findViewById(R.id.edtTxtEventName);
        mTxtDate= (TextView) view.findViewById(R.id.txtDate);
        mTxtFromTime= (TextView) view.findViewById(R.id.txtFromTime);
        mTxtToTime= (TextView) view.findViewById(R.id.txtToTime);
        mTxtProfile= (TextView) view.findViewById(R.id.txtprofileEvent);
        mTxtViewDate= (TextView) view.findViewById(R.id.txtViewDate);

        mTxtViewFromTime= (TextView) view.findViewById(R.id.txtViewFromTime);
        mTxtViewToTime= (TextView) view.findViewById(R.id.txtViewToTime);

        mSpinner= (Spinner) view.findViewById(R.id.spinnerEvent);


        final ArrayList<String> profiles =new ArrayList<>();
        profiles.add("Normal");
        profiles.add("Silent");
        profiles.add("Vibrate");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, profiles);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                 mProfileName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setHasOptionsMenu(true);

        mTxtViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        mDay = day;
                        mMonth = month;
                        mYears = year;

                        mTxtViewDate.setText(day+"/"+(month+1)+"/"+year);
                    }
                },2017,10,05);
                datePickerDialog.show();
            }
        });

        mTxtViewFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";

                        String minutes="";
                        if (minute < 10)
                            minutes = "0" + minute;
                        else
                            minutes = String.valueOf(minute);

                        mMin = minute;
                        mHour = hour;

                        Toast.makeText(getActivity(), " Min"+mMin+"Hours "+mHour, Toast.LENGTH_SHORT).show();

                        mTxtViewFromTime.setText(hour + ":" + minutes + " " + timeSet);

                    }
                },10,00,false);
                timePickerDialog.show();

            }
        });

        mTxtViewToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {

                        String timeSet = "";
                        if (hour > 12) {
                            hour -= 12;
                            timeSet = "PM";
                        } else if (hour == 0) {
                            hour += 12;
                            timeSet = "AM";
                        } else if (hour == 12)
                            timeSet = "PM";
                        else
                            timeSet = "AM";

                        String minutes="";
                        if (minute < 10)
                            minutes = "0" + minute;
                        else
                            minutes = String.valueOf(minute);


                        Thour = hour;
                        Tmin = minute;
                        mTxtViewToTime.setText(hour + ":" + minutes + " " + timeSet);

                    }
                },10,00,false);
                timePickerDialog.show();
            }
        });



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.saveEvent:

                EventData eventData = new EventData();
                eventData.setEventNAME(mEdtEventName.getText().toString());
                eventData.setDay(mDay);
                eventData.setMonth(mMonth);
                eventData.setYear(mYears);
                eventData.setFHour(mHour);
                eventData.setFMin(mMin);
                eventData.setTHour(Thour);
                eventData.setTMin(Tmin);
                eventData.setProfileName(mProfileName);


                Dbutil dbutil = Dbutil.getInstance(getContext());
                dbutil.AddEvent(eventData.getEventNAME(),eventData.getDay(),eventData.getMonth(),eventData.getYear(),eventData.getFHour(),eventData.getFMin(),eventData.getTHour(),eventData.getTMin(),eventData.getProfileName());
                dbutil.close();


                Intent intent = new Intent(getContext(),Service1.class);
                getActivity().startService(intent);





                Toast.makeText(getContext(), "Data Save", Toast.LENGTH_SHORT).show();




        }
        return true;
    }
}
