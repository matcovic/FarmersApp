package com.example.farmersapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmersapp.model.CustomListItem_Tips;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


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
    String mDataTipsTypeImage;
    CustomListItem_Tips mDataTips;
    String typeTitle ;



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
        assert getArguments() != null;
        CustomListItem_Tips data =  getArguments().getParcelable(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(DataLoadActivity.SHARED_PREFS, MODE_PRIVATE);


        //Invoking widgets
        farmingInfoTitle_details = convertView.findViewById(R.id.farmingInfoTitle_details);
        farmingInfoArticle_details = convertView.findViewById(R.id.farmingInfoArticle_details);
        farmingInfoImage_details = convertView.findViewById(R.id.farmingInfoImage_details);

      Type typeTipsData = new TypeToken<CustomListItem_Tips>() {
        }.getType();


        Gson gson = new Gson();
       String json ;



        String tipsTypeStatus = getArguments().getString(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA_STATUS);
        switch (tipsTypeStatus)
        {
            case Cultivation_FarmingInfoFragment.CROP_CARE_STATUS:
                mDataTipsTypeImage = sharedPreferences.getString(DataLoadActivity.CROP_CARE_TIPS+DataLoadActivity.IMAGE_TAG, "");


                mDataTips = getArguments().getParcelable(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA);
                typeTitle = getArguments().getString(Cultivation_FarmingInfoFragment.TYPE_STATUS);

            break;
            case Cultivation_FarmingInfoFragment.CROP_PLANT_STATUS:
                mDataTipsTypeImage = sharedPreferences.getString(DataLoadActivity.CROP_PLANT_TIPS+DataLoadActivity.IMAGE_TAG, "");

                mDataTips = getArguments().getParcelable(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA);
                typeTitle = getArguments().getString(Cultivation_FarmingInfoFragment.TYPE_STATUS);

                break;
            case  Cultivation_FarmingInfoFragment.CROP_SELECTION_STATUS:
                mDataTipsTypeImage = sharedPreferences.getString(DataLoadActivity.CROP_SELECTION_TIPS+DataLoadActivity.IMAGE_TAG, "");

                mDataTips = getArguments().getParcelable(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA);
                typeTitle = getArguments().getString(Cultivation_FarmingInfoFragment.TYPE_STATUS);

                break;
            case Cultivation_FarmingInfoFragment.LAND_PREPARATION_STATUS:
                mDataTipsTypeImage = sharedPreferences.getString(DataLoadActivity.LAND_PREPARATION_TIPS+DataLoadActivity.IMAGE_TAG, "");

                mDataTips = getArguments().getParcelable(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA);
                typeTitle = getArguments().getString(Cultivation_FarmingInfoFragment.TYPE_STATUS);

                break;
            case Cultivation_FarmingInfoFragment.LAND_SELECTION_STATUS:

                mDataTipsTypeImage = sharedPreferences.getString(DataLoadActivity.LAND_SELECTION_TIPS+DataLoadActivity.IMAGE_TAG, "");

                mDataTips = getArguments().getParcelable(Cultivation_FarmingInfoFragment.CLICKED_SEGMENT_DATA);
                typeTitle = getArguments().getString(Cultivation_FarmingInfoFragment.TYPE_STATUS);

                break;


        }

        byte[] decodedByte = Base64.decode(mDataTipsTypeImage, 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        farmingInfoImage_details.setImageBitmap(bmp);
        farmingInfoArticle_details.setText(mDataTips.getTipsBrief());
        farmingInfoTitle_details.setText(typeTitle);


        return convertView;
    }
}