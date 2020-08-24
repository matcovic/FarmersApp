package com.example.farmersapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmersapp.adapter.ListInformation_Adapter;
import com.example.farmersapp.model.InformationItem;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class InformationListFragment extends Fragment {

    RecyclerView InfoRecyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceCropNutritionInfo = firebaseFirestore.collection("Crop_Nutrition_Info");
    private CollectionReference collectionReferenceFarmingInstrumentInfo = firebaseFirestore.collection("Farming_Instrument_Info");
    private CollectionReference collectionReferenceInsectsInfo = firebaseFirestore.collection("Insects_Info");
    private CollectionReference collectionReferencePesticidesInfo = firebaseFirestore.collection("Pesticides_Info");
    private CollectionReference collectionReferenceSeedCareInfo = firebaseFirestore.collection("Seed_Care_Info");
    private CollectionReference collectionReferenceSoilCareInfo = firebaseFirestore.collection("Soil_Care_Info");
    private CollectionReference collectionReferenceTechnologyInfo = firebaseFirestore.collection("Technology_Info");
    ListInformation_Adapter adapter;
    List<InformationItem> mData;
    String typeStatus="";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InformationListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InformationListFragment newInstance(String param1, String param2) {
        InformationListFragment fragment = new InformationListFragment();
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
        View convertView = inflater.inflate(R.layout.fragment_information_list, container, false);

        InfoRecyclerView = convertView.findViewById(R.id.Info_recyclerView);
        mData = new ArrayList<>();
        assert getArguments() != null;
        typeStatus = getArguments().getString(InformationFragment.CLICKED_INFO_STATUS);
        Log.d("checked",typeStatus);
        setUpRecyclerViewManual();
        mData.clear();
        assert typeStatus != null;

        if(typeStatus.equals(InformationFragment.CROP_NUTRITION_INFO))
        {
            getDataToArray(collectionReferenceCropNutritionInfo);

        }
        else if(typeStatus.equals(InformationFragment.INSECTS_INFO))
        {
            getDataToArray(collectionReferenceInsectsInfo);
        }
        else if(typeStatus.equals(InformationFragment.PESTICIDES_INFO))
        {
            getDataToArray(collectionReferencePesticidesInfo);

        }
        else if(typeStatus.equals(InformationFragment.SEED_CARE_INFO))
        {
            getDataToArray(collectionReferenceSeedCareInfo);

        }
        else if(typeStatus.equals(InformationFragment.SOIL_CARE_INFO))
        {
            getDataToArray(collectionReferenceSoilCareInfo);

        }
        else if(typeStatus.equals(InformationFragment.TECHNOLOGY_INFO))
        {
//                getDataToArray(collectionReferenceTechnologyInfo);

        }
        else if(typeStatus.equals(InformationFragment.FARMING_INSTRUMENT_INFO))
        {
            getDataToArray(collectionReferenceFarmingInstrumentInfo);

        }

        return convertView;
    }


    private void getDataToArray(CollectionReference CollectionReference) {

        CollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<InformationItem> data = queryDocumentSnapshots.toObjects(InformationItem.class);
                    adapter = new ListInformation_Adapter(mData, getContext(), typeStatus);
                    InfoRecyclerView.setAdapter(adapter);
                    mData.addAll(data);
                    Log.d("checked success:", "ok " + mData);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


    }

    private void setUpRecyclerViewManual() {
        InfoRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        InfoRecyclerView.setLayoutManager(layoutManager);


    }

}