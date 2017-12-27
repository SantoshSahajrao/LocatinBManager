package com.bitcode.hardik.profilemanager.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitcode.hardik.profilemanager.AdapterClasses.AdapterRecyclerView;
import com.bitcode.hardik.profilemanager.DbClasses.LocationData;
import com.bitcode.hardik.profilemanager.MainActivity;
import com.bitcode.hardik.profilemanager.Map.MapFragment;
import com.bitcode.hardik.profilemanager.Map.MapsActivity;
import com.bitcode.hardik.profilemanager.ModelClasses.Location;
import com.bitcode.hardik.profilemanager.R;

import java.util.ArrayList;


public class FragmentLocation extends Fragment {

    FloatingActionButton mBtnFloat;
    RecyclerView mRecyclerLocationList;
    ArrayList<LocationData> mArrLocationList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_location,container,false);

        mBtnFloat= (FloatingActionButton) view.findViewById(R.id.btnFloat);
        mRecyclerLocationList= (RecyclerView) view.findViewById(R.id.recyclerLocation);

        mRecyclerLocationList= (RecyclerView) view.findViewById(R.id.recyclerLocation);
        mArrLocationList=new ArrayList<>();

        AdapterRecyclerView adapterRecyclerView=new AdapterRecyclerView(getContext(),mArrLocationList);
        mRecyclerLocationList.setAdapter(adapterRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerLocationList.setLayoutManager(linearLayoutManager);


        mBtnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MainActivity mainActivity= (MainActivity) getContext();
//                FragmentLocationParameter fragmentLocationParameter=new FragmentLocationParameter();
//                Bundle bundle=new Bundle();
//                fragmentLocationParameter.setArguments(bundle);
//                mainActivity.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentLocationParameter).commit();

//                Intent intent=new Intent(getContext(), MapsActivity.class);
//                startActivity(intent);

                MapFragment mapFragment=new MapFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,mapFragment).commit();

            }
        });



        return view;
    }


    public interface OnFragmentInteractionListener {
    }
}
