package com.bitcode.hardik.profilemanager.AdapterClasses;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.LocationData;
import com.bitcode.hardik.profilemanager.Fragments.FragmentEditLocation;
import com.bitcode.hardik.profilemanager.MainActivity;
import com.bitcode.hardik.profilemanager.ModelClasses.Location;
import com.bitcode.hardik.profilemanager.R;

import java.util.ArrayList;

/**
 * Created by hardik on 5/12/17.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolderLocation> {

    private Context mContext;
    private ArrayList<LocationData> mArrLocationList;

    MainActivity mainActivity ;

    public AdapterRecyclerView(Context context, ArrayList<LocationData> ArrLocationList) {
        mContext = context;
        mainActivity = new MainActivity();

    }

    @Override
    public ViewHolderLocation onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.lay_adapter_location,null);
        ViewHolderLocation viewHolderLocation=new ViewHolderLocation(view);
        return viewHolderLocation;
    }




    @Override
    public void onBindViewHolder(final ViewHolderLocation holder, int position) {

        final LocationData location=mArrLocationList.get(position);

        holder.mLocationName.setText(location.getLocationName());
        holder.mTxtOptionSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu=new PopupMenu(mContext,holder.mTxtOptionSelected);
                popupMenu.inflate(R.menu.menu_edit_location);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.editLocation:
                                MainActivity mainActivity1 = (MainActivity) mContext;
                                FragmentEditLocation fragmentEditLocation = new FragmentEditLocation();

                                mainActivity1.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentEditLocation).commit();

                                Toast.makeText(mContext,"Edit Clicked",Toast.LENGTH_LONG).show();

                            case R.id.deleteLocation:

                                Dbutil dbutil = Dbutil.getInstance(mContext);
                                dbutil.DeleteLocationData(location.getLocationId());
                                dbutil.close();
                                notifyDataSetChanged();


                                Toast.makeText(mContext,"Delete Clicked at "+location.getLocationName(),Toast.LENGTH_LONG).show();
                        }


                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        Dbutil dbutil = Dbutil.getInstance(mContext);
        mArrLocationList =  dbutil.getAllLocationList();
        dbutil.close();


        return mArrLocationList.size();
    }



    public class ViewHolderLocation extends RecyclerView.ViewHolder {

        TextView mLocationName,mTxtOptionSelected;

        public ViewHolderLocation(View itemView) {
            super(itemView);

            mLocationName= (TextView) itemView.findViewById(R.id.txtLocName);
            mTxtOptionSelected= (TextView) itemView.findViewById(R.id.txtViewLocationOption);
           mTxtOptionSelected.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();



                PopupMenu popupMenu=new PopupMenu(mContext,mTxtOptionSelected);
                popupMenu.inflate(R.menu.menu_edit_location);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId())
                        {
                            case R.id.editLocation:

                                Toast.makeText(mContext,"Edit Clicked",Toast.LENGTH_LONG).show();

                            case R.id.deleteLocation:
                                LocationData locationData = mArrLocationList.get(getAdapterPosition());
                                Dbutil dbutil = Dbutil.getInstance(mContext);
                                dbutil.DeleteLocationData(locationData.getLocationId());
                                dbutil.close();
                                notifyDataSetChanged();


                                Toast.makeText(mContext,"Delete Clicked at "+locationData.getLocationName(),Toast.LENGTH_LONG).show();
                        }


                        return false;
                    }
                });

                popupMenu.show();


               }
           });
        }
    }
}
