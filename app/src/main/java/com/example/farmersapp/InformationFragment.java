package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Objects;

public class InformationFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String CLICKED_INFO_STATUS = "clickedInfoStatus";
    public static final String CROP_NUTRITION_INFO = "crop_nutrition_info";
    public static final String SOIL_CARE_INFO = "soil_care_info";
    public static final String SEED_CARE_INFO = "seed_care_info";
    public static final String INSECTS_INFO = "insects_info";
    public static final String TECHNOLOGY_INFO = "technology_info";
    public static final String PESTICIDES_INFO = "pesticides_info";
    public static final String FARMING_INSTRUMENT_INFO = "farming_instrument_info";

    Bundle bundle;

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
        View convertView = inflater.inflate(R.layout.fragment_information, container, false);
        bundle = new Bundle();
        //Invoking Button
        //widgets Button
        LinearLayout click_soilCare = convertView.findViewById(R.id.click_soilCare);
        LinearLayout click_seedCare = convertView.findViewById(R.id.click_seedCare);
        LinearLayout click_cropNutrition = convertView.findViewById(R.id.click_cropNutrition);
        LinearLayout click_insects = convertView.findViewById(R.id.click_insects);
        LinearLayout click_technology = convertView.findViewById(R.id.click_technology);
        LinearLayout click_medicine = convertView.findViewById(R.id.click_medicine);
        LinearLayout click_farming_instrument_info = convertView.findViewById(R.id.click_farming_instrument_info);


        click_cropNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        bundle.putString(CLICKED_INFO_STATUS,CROP_NUTRITION_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        click_insects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bundle.putString(CLICKED_INFO_STATUS,INSECTS_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        click_seedCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString(CLICKED_INFO_STATUS,SEED_CARE_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        click_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bundle.putString(CLICKED_INFO_STATUS,PESTICIDES_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        click_soilCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString(CLICKED_INFO_STATUS,SOIL_CARE_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        click_technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString(CLICKED_INFO_STATUS,TECHNOLOGY_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        click_farming_instrument_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString(CLICKED_INFO_STATUS,FARMING_INSTRUMENT_INFO);
                Fragment fragment =InformationListFragment.newInstance("","");
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return convertView;
    }

}