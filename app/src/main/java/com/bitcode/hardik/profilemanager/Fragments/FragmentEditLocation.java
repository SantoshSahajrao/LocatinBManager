package com.bitcode.hardik.profilemanager.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bitcode.hardik.profilemanager.R;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;


public class FragmentEditLocation extends Fragment {

    private TextView mTxtLocationName, mTxtProfile, mTxtRadius, mTxtSeekProgress;
    private EditText mEditLocationName;
    private Spinner mSpinner;
    private BubbleSeekBar mSeekBar;
    private Button mBtnSave, mBtnCancel;
    private int EditRadius;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_location,container,false);

        mTxtLocationName= (TextView) view.findViewById(R.id.txtViewEditLocationName);
        mTxtProfile= (TextView) view.findViewById(R.id.txtEditProfileLocation);
        mTxtRadius= (TextView) view.findViewById(R.id.txtEditRadius);
        mTxtSeekProgress= (TextView) view.findViewById(R.id.txtEditSeek);

        mEditLocationName= (EditText) view.findViewById(R.id.editTxtLocationName);

        mSpinner= (Spinner) view.findViewById(R.id.editSpinnerProfiles);

        mSeekBar= (BubbleSeekBar) view.findViewById(R.id.editBubleSeekBar);

        mBtnSave= (Button) view.findViewById(R.id.btnEditSaveLocation);
        mBtnCancel= (Button) view.findViewById(R.id.btnEditCancelLocation);


        final ArrayList<String> profiles =new ArrayList<>();
        profiles.add("Normal");
        profiles.add("Silent");
        profiles.add("Vibrate");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, profiles);
        mSpinner.setAdapter(adapter);

        mSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {

                mTxtSeekProgress.setText(String.format(String.valueOf(progress)));
                EditRadius = progress;
            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });



        return view;
    }
}
