package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farmersapp.adapter.ListInformation_Adapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class InformationListDetailsFragment extends Fragment {

    //Widgets
    private TextView informationTitle_details, informationArticle_details;
    private ImageView informationImage_details;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InformationListDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InformationListDetailsFragment newInstance(String param1, String param2) {
        InformationListDetailsFragment fragment = new InformationListDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView =inflater.inflate(R.layout.fragment_information_list_details, container, false);

        //Invoking widgets
        informationTitle_details = convertView.findViewById(R.id.informationTitle_details);
        informationArticle_details = convertView.findViewById(R.id.informationArticle_details);
        informationImage_details = convertView.findViewById(R.id.informationImage_details);
        Bundle args = getArguments();
        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/"+args.getString(ListInformation_Adapter.INFO_STATUS)+"/")
                .child(args.getString(ListInformation_Adapter.INFO_DETAILS_PHOTO)+".jpg");

        informationArticle_details.setText(args.getString(ListInformation_Adapter.INFO_DETAILS_BRIEF));
        Glide.with(requireContext())
                .load(storageReference)
                .into(informationImage_details);

        informationTitle_details.setText(args.getString(ListInformation_Adapter.INFO_DETAILS_TITLE));

        return convertView;
    }
}