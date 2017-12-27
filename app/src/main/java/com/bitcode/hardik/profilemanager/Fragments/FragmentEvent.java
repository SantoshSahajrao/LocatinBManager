package com.bitcode.hardik.profilemanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitcode.hardik.profilemanager.AdapterClasses.AdapterEvent;
import com.bitcode.hardik.profilemanager.MainActivity;
import com.bitcode.hardik.profilemanager.ModelClasses.Event;
import com.bitcode.hardik.profilemanager.R;

import java.util.ArrayList;

public class FragmentEvent extends Fragment {

    private FloatingActionButton mFbtn;
    private AdapterEvent mAdapterEvent;
    private ArrayList<Event>mEventList;
    private RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_event,container,false);
        mFbtn= (FloatingActionButton) view.findViewById(R.id.btnFloatingAction);

        mEventList=new ArrayList<>();
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerEvent);
        mAdapterEvent=new AdapterEvent(getActivity(),mEventList);

        mFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity= (MainActivity) getContext();
                FragmentAddEvent fragmentAddEvent=new FragmentAddEvent();
                Bundle bundle=new Bundle();
                fragmentAddEvent.setEnterTransition(new Slide(Gravity.RIGHT));
                fragmentAddEvent.setArguments(bundle);
                mainActivity.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentAddEvent,null).commit();
            }
        });


        return view;

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
