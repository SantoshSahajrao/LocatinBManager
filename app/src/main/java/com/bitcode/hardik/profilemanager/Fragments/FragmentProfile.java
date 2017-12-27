package com.bitcode.hardik.profilemanager.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitcode.hardik.profilemanager.AdapterClasses.AdapterProfile;
import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.DefaultProfileData;
import com.bitcode.hardik.profilemanager.DbClasses.ProfileData;
import com.bitcode.hardik.profilemanager.MainActivity;
import com.bitcode.hardik.profilemanager.ModelClasses.Profile;
import com.bitcode.hardik.profilemanager.R;

import java.util.ArrayList;


public class FragmentProfile extends Fragment {

    private TextView mTxtNormal,mTxtSilent, mTxtVibrate,mTxtDefaultProfile;
    private FloatingActionButton fab;
    private LinearLayout linearLayout;
    private int counter = 0;
    private TextView textView;
    private RecyclerView mRecyclerView;
    private AdapterProfile mAdapterProfile;
    private ArrayList<ProfileData>mDefaulteProfileList;
    int Count = 0;







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile,null);

        mTxtDefaultProfile= (TextView) view.findViewById(R.id.txtViewDefaultProfile);

        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerProfile);



        Dbutil dbutil= Dbutil.getInstance(getContext());
        ProfileData mDefaultProfile = dbutil.getDefaulteProfileList();
        dbutil.close();

        String DefaultProfileName = mDefaultProfile.getProfileName();
        if (DefaultProfileName.equals("DefaultProfile"))
        {
            mTxtDefaultProfile.setText("Tap Here To set Default Profile");

        }else
        {

            mTxtDefaultProfile.setText(DefaultProfileName);
        }





        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapterProfile=new AdapterProfile(getActivity());
        mRecyclerView.setAdapter(mAdapterProfile);

        Bundle bundle=new Bundle();
        bundle.getSerializable("profile");

        mTxtDefaultProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity= (MainActivity) getContext();
                FragmentDefaultProfile fragmentDefaultProfile=new FragmentDefaultProfile();
                fragmentDefaultProfile.setEnterTransition(new Slide(Gravity.RIGHT));
                mainActivity.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentDefaultProfile).commit();


            }
        });



       // linearLayout= (LinearLayout) view.findViewById(R.id.list);
        fab= (FloatingActionButton) view.findViewById(R.id.floatingBtn);


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView = new TextView(getContext());
//                linearLayout.addView(textView);
//                mArrList.add(counter,textView);
//                textView.setGravity(Gravity.LEFT);
//                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                textView.setTextSize(24);
//                textView.setPadding(16,16,16,16);
//                textView.setText("Custom Profile"+counter);
//                textView.setLayoutParams(layoutParams);
//                counter++;
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        MainActivity mainActivity= (MainActivity) getContext();
//                        FragmentCustomProfile fragmentCustomProfile=new FragmentCustomProfile();
//                        Bundle bundle=new Bundle();
//                        fragmentCustomProfile.setArguments(bundle);
//                        mainActivity.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentCustomProfile).commit();
//                    }
//                });

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity mainActivity= (MainActivity) getContext();
                        FragmentCustomProfile fragmentCustomProfile=new FragmentCustomProfile();
                        Bundle bundle=new Bundle();
                        fragmentCustomProfile.setEnterTransition(new Slide(Gravity.RIGHT));
                        fragmentCustomProfile.setArguments(bundle);
                        mainActivity.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentCustomProfile).commit();
                    }
                });
        return view;
        }

    public interface OnFragmentInteractionListener {
    }
}


