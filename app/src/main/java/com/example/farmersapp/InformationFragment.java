package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class InformationFragment extends Fragment {


    //widgets Button
    private LinearLayout click_soilCare, click_seedCare, click_cropNutrition
            , click_insects, click_technology, click_medicine;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InformationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InformationFragment newInstance(String param1, String param2) {
        InformationFragment fragment = new InformationFragment();
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
        View convertView =  inflater.inflate(R.layout.fragment_information, container, false);

        //Invoking Button
        click_soilCare = convertView.findViewById(R.id.click_soilCare);
        click_seedCare = convertView.findViewById(R.id.click_seedCare);
        click_cropNutrition = convertView.findViewById(R.id.click_cropNutrition);
        click_insects = convertView.findViewById(R.id.click_insects);
        click_technology = convertView.findViewById(R.id.click_technology);
        click_medicine = convertView.findViewById(R.id.click_medicine);

        return convertView;
    }
}