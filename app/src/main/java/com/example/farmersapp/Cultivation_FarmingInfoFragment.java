package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class Cultivation_FarmingInfoFragment extends Fragment {

    //widgets Button
    private LinearLayout click_landSelection, click_landPreparation, click_cropSelection
            , click_cropPlant, click_cropCare;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cultivation_FarmingInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Cultivation_FarmingInfoFragment newInstance(String param1, String param2) {
        Cultivation_FarmingInfoFragment fragment = new Cultivation_FarmingInfoFragment();
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
        Log.d("check","cultivation farming info");

        View convertView = inflater.inflate(R.layout.fragment_cultivation_farming_info, container, false);


        //Invoking Button
        click_landSelection = convertView.findViewById(R.id.click_landSelection);
        click_landPreparation = convertView.findViewById(R.id.click_landPreparation);
        click_cropSelection = convertView.findViewById(R.id.click_cropSelection);
        click_cropPlant = convertView.findViewById(R.id.click_cropPlant);
        click_cropCare = convertView.findViewById(R.id.click_cropCare);

        return  convertView;
    }
}