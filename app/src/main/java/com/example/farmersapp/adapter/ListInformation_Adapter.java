package com.example.farmersapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersapp.InformationListDetailsFragment;
import com.example.farmersapp.InformationListFragment;
import com.example.farmersapp.R;
import com.example.farmersapp.model.InformationItem;
import com.example.farmersapp.util.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.List;

public class ListInformation_Adapter extends RecyclerView.Adapter<ListInformation_Adapter.ViewHolder> {


    List<InformationItem> mData;
    Context mContext;
    String status;
    public static final String INFO_DETAILS_BRIEF = "infoDetailsBrief";
    public static final String INFO_DETAILS_TITLE = "infoDetailsTitle";
    public static final String INFO_DETAILS_PHOTO = "infoDetailsPhoto";
    public static final String INFO_STATUS = "infoStatus";

    public ListInformation_Adapter(List<InformationItem> mData, Context mContext, String status) {
        this.mData = mData;
        this.mContext = mContext;
        this.status = status;
    }


    public ListInformation_Adapter() {
    }

    @NonNull
    @Override
    public ListInformation_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_information,parent,false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ListInformation_Adapter.ViewHolder holder, final int position) {

        holder.titleTextView.setText(mData.get(position).getInfoTitle());
        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/"+status+"/").child(mData.get(position).getInfoPhoto()+".jpg");

        GlideApp.with(mContext)
                .load(storageReference)
                .centerCrop()
                .into(holder.infoImageView);
        holder.infoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = InformationListDetailsFragment.newInstance("","");
                FragmentManager fragmentManager =((FragmentActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString(INFO_DETAILS_BRIEF,mData.get(position).getInfoBrief());
                bundle.putString(INFO_DETAILS_TITLE, mData.get(position).getInfoTitle());
                bundle.putString(INFO_DETAILS_PHOTO,  mData.get(position).getInfoPhoto());
                bundle.putString(INFO_STATUS,status);

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView infoImageView;
        TextView titleTextView;
        CardView infoCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoImageView = itemView.findViewById(R.id.informationImage_list);
            titleTextView = itemView.findViewById(R.id.informationTitle_list);
            infoCardView = itemView.findViewById(R.id.cardView_listItemInfo);
        }
    }
}
