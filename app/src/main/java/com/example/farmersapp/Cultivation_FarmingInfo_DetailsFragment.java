package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Cultivation_FarmingInfo_DetailsFragment extends Fragment {

    //Widgets
    private TextView farmingInfoTitle_details, farmingInfoArticle_details;
    private ImageView farmingInfoImage_details;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cultivation_FarmingInfo_DetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Cultivation_FarmingInfo_DetailsFragment newInstance(String param1, String param2) {
        Cultivation_FarmingInfo_DetailsFragment fragment = new Cultivation_FarmingInfo_DetailsFragment();
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
        View convertView = inflater.inflate(R.layout.fragment_cultivation_farming_info_details, container, false);

        //Invoking widgets
        farmingInfoTitle_details = convertView.findViewById(R.id.farmingInfoTitle_details);
        farmingInfoArticle_details = convertView.findViewById(R.id.farmingInfoArticle_details);
        farmingInfoImage_details = convertView.findViewById(R.id.farmingInfoImage_details);

        return convertView;
    }
}