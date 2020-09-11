package com.example.farmersapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmersapp.model.CustomListItem_Diseases;

public class Cultivation_Disease_DetailsFragment extends Fragment {

    //Widgets
    private TextView diseaseTitle_details, diseaseType_details, diseaseScienteficName_details,
            diseaseBrief_details, diseaseCause_details, diseaseChemicalControl_details,
            diseaseBiologicalControl_details;
    private ImageView diseaseImage_details;
    CustomListItem_Diseases mData;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Cultivation_Disease_DetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Cultivation_Disease_DetailsFragment newInstance(String param1, String param2) {
        Cultivation_Disease_DetailsFragment fragment = new Cultivation_Disease_DetailsFragment();
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

         mData =    getArguments().getParcelable(DataLoadActivity.DATA_DISEASE);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertView = inflater.inflate(R.layout.fragment_cultivation_disease_details, container, false);

        //Invoking widgets
        diseaseTitle_details = convertView.findViewById(R.id.diseaseTitle_details);
        diseaseType_details = convertView.findViewById(R.id.diseaseType_details);
        diseaseScienteficName_details = convertView.findViewById(R.id.diseaseScienteficName_details);
        diseaseBrief_details = convertView.findViewById(R.id.diseaseBrief_details);
        diseaseCause_details = convertView.findViewById(R.id.diseaseCause_details);
        diseaseChemicalControl_details = convertView.findViewById(R.id.diseaseChemicalControl_details);
        diseaseBiologicalControl_details = convertView.findViewById(R.id.diseaseBiologicalControl_details);
        diseaseImage_details = convertView.findViewById(R.id.diseaseImage_details);

        CustomListItem_Diseases customListItem_diseases = getArguments().getParcelable(DataLoadActivity.DATA_DISEASE);

        diseaseTitle_details.setText(customListItem_diseases.getDiseaseTitle());
        diseaseType_details.setText(customListItem_diseases.getDiseaseType());
        diseaseScienteficName_details.setText(customListItem_diseases.getDiseaseScientificName());
        diseaseBrief_details.setText(customListItem_diseases.getDiseaseBrief());
        diseaseCause_details.setText(customListItem_diseases.getDiseaseCause());
        diseaseChemicalControl_details.setText(customListItem_diseases.getDiseaseChemicalControl());
        diseaseBiologicalControl_details.setText(customListItem_diseases.getDiseaseBiologicalControl());

        byte[] decodedByte = Base64.decode(mData.getDiseasePhoto(), 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
      diseaseImage_details.setImageBitmap(bmp);

        return convertView;
    }
}