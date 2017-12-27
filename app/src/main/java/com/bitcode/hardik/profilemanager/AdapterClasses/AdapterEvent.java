package com.bitcode.hardik.profilemanager.AdapterClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitcode.hardik.profilemanager.ModelClasses.Event;
import com.bitcode.hardik.profilemanager.R;

import java.util.ArrayList;

/**
 * Created by hardik on 6/12/17.
 */

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolderEvent> {

    private Context mContext;
    private ArrayList<Event>mEventList;

    public AdapterEvent(Context Context, ArrayList<Event> EventList) {
        this.mContext = Context;
        this.mEventList = EventList;
    }

    @Override
    public AdapterEvent.ViewHolderEvent onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.lay_adapter_event,null);
        ViewHolderEvent viewHolderEvent=new ViewHolderEvent(view);
        return viewHolderEvent;
    }

    @Override
    public void onBindViewHolder(AdapterEvent.ViewHolderEvent holder, int position) {

        final Event event=mEventList.get(position);

        holder.mEventName.setText(event.getEventName());
        holder.mEventProfileName.setText(event.getProfileName());
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class ViewHolderEvent extends RecyclerView.ViewHolder {

        private TextView mEventName,mEventProfileName;
        public ViewHolderEvent(View itemView) {
            super(itemView);

            mEventName= (TextView) itemView.findViewById(R.id.txtEventName);
            mEventProfileName= (TextView) itemView.findViewById(R.id.txtProfileName);
        }
    }
}
