package com.bitcode.hardik.profilemanager.AdapterClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcode.hardik.profilemanager.DbClasses.Dbutil;
import com.bitcode.hardik.profilemanager.DbClasses.ProfileData;
import com.bitcode.hardik.profilemanager.Fragments.FragmentEditLocation;
import com.bitcode.hardik.profilemanager.Fragments.FragmentEditProfile;
import com.bitcode.hardik.profilemanager.MainActivity;
import com.bitcode.hardik.profilemanager.ModelClasses.Profile;
import com.bitcode.hardik.profilemanager.R;

import java.util.ArrayList;

/**
 * Created by hardik on 6/12/17.
 */

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ViewHolderProfile> {

    private Context mContext;
    private ArrayList<ProfileData> mProfileList;
    ArrayList<String> profiles;

    ProfileData profileData;

    public AdapterProfile(Context Context) {
        this.mContext = Context;

        profiles =new ArrayList<>();
        profiles.add("Normal");
        profiles.add("Silent");
        profiles.add("Vibrate");



        Dbutil dbutil = Dbutil.getInstance(mContext);
        mProfileList =  dbutil.getAllProfileList();
        dbutil.close();



        for (int i=0;i<mProfileList.size();i++)
        {
            ProfileData profileData = mProfileList.get(i);
            String proName = profileData.getProfileName();
            profiles.add(proName);

        }



    }

    @Override
    public ViewHolderProfile onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.lay_adapter_profile,null);
        ViewHolderProfile viewHolderProfile=new ViewHolderProfile(view);
        return viewHolderProfile;
    }

    @Override
    public void onBindViewHolder(final ViewHolderProfile holder, int position) {

    //    Profile profileData = profiles.get(position);

        holder.mTxtCustomProfile.setText(profiles.get(position));


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class ViewHolderProfile extends RecyclerView.ViewHolder {

        private TextView mTxtCustomProfile,mTxtEditProfile;

        public ViewHolderProfile(View itemView) {
            super(itemView);

            mTxtCustomProfile= (TextView) itemView.findViewById(R.id.txtCustomProfileName);
            mTxtEditProfile= (TextView) itemView.findViewById(R.id.txtViewOptions);
            mTxtEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    PopupMenu popupMenu=new PopupMenu(mContext,mTxtEditProfile);
                    popupMenu.inflate(R.menu.menu_edit_profile);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            switch (menuItem.getItemId()){

                                case R.id.editProfile:

                                    MainActivity mainActivity1 = (MainActivity) mContext;
                                    FragmentEditProfile fragmentEditProfile = new FragmentEditProfile();

                                    mainActivity1.fragmentManager.beginTransaction().replace(R.id.content_frame,fragmentEditProfile).commit();


                                    Toast.makeText(mContext,"Edit Clicked",Toast.LENGTH_LONG).show();

                                case R.id.deleteProfile:

                                    Dbutil dbutil = Dbutil.getInstance(mContext);
                                    String da = profiles.get(getAdapterPosition());
                                    dbutil.DeleteProfileData(profiles.get(getAdapterPosition()));
                                    dbutil.close();

                                    profiles.remove(getAdapterPosition());
                                    notifyDataSetChanged();

//                                    Toast.makeText(mContext,"Delete Clicked"+profiles.get(getAdapterPosition()),Toast.LENGTH_LONG).show();

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
