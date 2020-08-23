package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.farmersapp.model.CustomListItem_DiseaseCategories;
import com.example.farmersapp.model.CustomListItem_Tips;

import java.io.Serializable;
import java.util.Objects;


public class Cultivation_FarmingInfoFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String CLICKED_SEGMENT_DATA = "clickedSegmentData";
    public static final String CLICKED_SEGMENT_DATA_STATUS= "clickedSegmentDataStatus";
    public static final String CROP_CARE_STATUS = "CROP_CARE";
    public static final String CROP_PLANT_STATUS = "CROP_PLANT";
    public static final String CROP_SELECTION_STATUS = "CROP_SELECTION";
    public static final String LAND_PREPARATION_STATUS = "LAND_PREPARATION";
    public static final String LAND_SELECTION_STATUS = "LAND_SELECTION";
    public static final String TYPE_STATUS = "typeStatus";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    CustomListItem_Tips mDataCropPlantTips;
    CustomListItem_Tips mDataCropSelectionTips;
    CustomListItem_Tips mDataCropCareTips;
    CustomListItem_Tips mDataLandSelectionTips;
    CustomListItem_Tips mDataLandPreparationTips;


    public Cultivation_FarmingInfoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Cultivation_FarmingInfoFragment newInstance() {
        Cultivation_FarmingInfoFragment fragment = new Cultivation_FarmingInfoFragment();
        Bundle args = new Bundle();


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
        Log.d("check", "cultivation farming info");

        View convertView = inflater.inflate(R.layout.fragment_cultivation_farming_info, container, false);


        //Invoking Button
        //widgets Button
        LinearLayout click_landSelection = convertView.findViewById(R.id.click_landSelection);
        LinearLayout click_landPreparation = convertView.findViewById(R.id.click_landPreparation);
        LinearLayout click_cropSelection = convertView.findViewById(R.id.click_cropSelection);
        LinearLayout click_cropPlant = convertView.findViewById(R.id.click_cropPlant);
        LinearLayout click_cropCare = convertView.findViewById(R.id.click_cropCare);
        assert getArguments() != null;
        mDataCropPlantTips = getArguments().getParcelable(DataLoadActivity.CROP_PLANT_TIPS);
        mDataCropSelectionTips = getArguments().getParcelable(DataLoadActivity.CROP_SELECTION_TIPS);
        mDataCropCareTips = getArguments().getParcelable(DataLoadActivity.CROP_CARE_TIPS);
        mDataLandSelectionTips = getArguments().getParcelable(DataLoadActivity.LAND_SELECTION_TIPS);
        mDataLandPreparationTips = getArguments().getParcelable(DataLoadActivity.LAND_PREPARATION_TIPS);

        click_cropCare.setOnClickListener(this);
        click_cropPlant.setOnClickListener(this);
        click_cropSelection.setOnClickListener(this);
        click_landPreparation.setOnClickListener(this);
        click_landSelection.setOnClickListener(this);




        return convertView;
    }

    @Override
    public void onClick(View v) {

       Fragment itemFragment = Cultivation_FarmingInfo_DetailsFragment.newInstance("","");
        FragmentManager fragmentManager = ((FragmentActivity) requireContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle args = new Bundle();


        switch (v.getId()) {
            case R.id.click_cropCare:
                args.putParcelable(CLICKED_SEGMENT_DATA, mDataCropCareTips);
                args.putString(CLICKED_SEGMENT_DATA_STATUS,CROP_CARE_STATUS);
                args.putString(TYPE_STATUS,getString(R.string.crop_care));
                break;
            case R.id.click_cropPlant:

                args.putParcelable(CLICKED_SEGMENT_DATA, mDataCropPlantTips);
                args.putString(CLICKED_SEGMENT_DATA_STATUS,CROP_PLANT_STATUS);
                args.putString(TYPE_STATUS,getString(R.string.crop_plant));

                break;
            case R.id.click_cropSelection:
                args.putParcelable(CLICKED_SEGMENT_DATA, mDataCropSelectionTips);
                args.putString(CLICKED_SEGMENT_DATA_STATUS,CROP_SELECTION_STATUS);
                args.putString(TYPE_STATUS,getString(R.string.crop_selection));

                break;
            case R.id.click_landPreparation:
                args.putParcelable(CLICKED_SEGMENT_DATA, mDataLandPreparationTips);
                args.putString(CLICKED_SEGMENT_DATA_STATUS,LAND_PREPARATION_STATUS);
                args.putString(TYPE_STATUS,getString(R.string.land_prepare));

                break;
            case R.id.click_landSelection:
                args.putParcelable(CLICKED_SEGMENT_DATA, mDataLandSelectionTips);
                args.putString(CLICKED_SEGMENT_DATA_STATUS,LAND_SELECTION_STATUS);
                args.putString(TYPE_STATUS,getString(R.string.land_selection));

                break;


        }
        itemFragment.setArguments(args);
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, itemFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}