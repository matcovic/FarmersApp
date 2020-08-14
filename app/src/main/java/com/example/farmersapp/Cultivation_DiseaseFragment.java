package com.example.farmersapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farmersapp.adapter.ListCharaRoponDhap_Adapter;
import com.example.farmersapp.adapter.ListFolonDhap_Adapter;
import com.example.farmersapp.adapter.ListFosolKatarDhap_Adapter;
import com.example.farmersapp.adapter.ListPushpoDhap_Adapter;
import com.example.farmersapp.adapter.ListUdhvidhBorodhonDhap_Adapter;
import com.example.farmersapp.model.CropsModel;
import com.example.farmersapp.model.CustomListItem_DiseaseCategories;
import com.example.farmersapp.model.CustomListItem_Diseases;
import com.example.farmersapp.model.DiseaseCategoriesModel;
import com.example.farmersapp.model.DiseasesModel;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static android.content.Context.MODE_PRIVATE;

public class Cultivation_DiseaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    List<DiseasesModel> mDataDiseasesModel;


    public Cultivation_DiseaseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Cultivation_DiseaseFragment newInstance(String param1, String param2) {
        Cultivation_DiseaseFragment fragment = new Cultivation_DiseaseFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_cultivation_disease, container, false);
        RecyclerView recyclerViewFosolKatarDhap,recyclerViewPushpoDhap,recyclerViewUdhvidhBorodhonDhap,recyclerViewCharaRoponDhap,recyclerViewFolonDhap;
        contentView = inflater.inflate(R.layout.fragment_cultivation_disease, container, false);
        recyclerViewCharaRoponDhap = contentView.findViewById(R.id.chara_ropon_dhap_recyclerView);
        recyclerViewFolonDhap = contentView.findViewById(R.id.folon_dhap_recyclerView);
        recyclerViewFosolKatarDhap = contentView.findViewById(R.id.fosol_katar_dhap_recyclerView);
        recyclerViewPushpoDhap = contentView.findViewById(R.id.pushso_dhap_recyclerView);
        recyclerViewUdhvidhBorodhonDhap = contentView.findViewById(R.id.udhvidh_bordhon_dhap_recyclerView);

        CustomListItem_DiseaseCategories dataOfDiseaseCategories = (CustomListItem_DiseaseCategories) getArguments().getParcelable("data");
        Map<String, CustomListItem_Diseases> diseasesModelMap = (Map<String, CustomListItem_Diseases>) getArguments().getSerializable("dataMap");

        Log.d("check map", String.valueOf(diseasesModelMap.size()));



        setUpRecyclerViewManual(recyclerViewCharaRoponDhap);
        setUpRecyclerViewManual(recyclerViewFolonDhap);
        setUpRecyclerViewManual(recyclerViewFosolKatarDhap);
        setUpRecyclerViewManual(recyclerViewPushpoDhap);
        setUpRecyclerViewManual(recyclerViewUdhvidhBorodhonDhap);


        ListCharaRoponDhap_Adapter listCharaRoponDhap_adapter = new ListCharaRoponDhap_Adapter(getContext(),dataOfDiseaseCategories.getCharaRoponDhap(),diseasesModelMap);
        ListFolonDhap_Adapter listFolonDhap_adapter = new ListFolonDhap_Adapter(getContext(),dataOfDiseaseCategories.getFolonDhap(),diseasesModelMap);
        ListFosolKatarDhap_Adapter listFosolKatarDhap_adapter = new ListFosolKatarDhap_Adapter(getContext(),dataOfDiseaseCategories.getFosolKatarDhap(),diseasesModelMap);
        ListPushpoDhap_Adapter listPushpoDhap_adapter = new ListPushpoDhap_Adapter(getContext(),dataOfDiseaseCategories.getPushpoDhap(),diseasesModelMap);
        ListUdhvidhBorodhonDhap_Adapter listUdhvidhBorodhonDhap_adapter = new ListUdhvidhBorodhonDhap_Adapter(getContext(),dataOfDiseaseCategories.getUdhvidhBordhonDhap(),diseasesModelMap);


        recyclerViewCharaRoponDhap.setAdapter(listCharaRoponDhap_adapter);
        recyclerViewFolonDhap.setAdapter(listFolonDhap_adapter);
        recyclerViewFosolKatarDhap.setAdapter(listFosolKatarDhap_adapter);
        recyclerViewPushpoDhap.setAdapter(listPushpoDhap_adapter);
        recyclerViewUdhvidhBorodhonDhap.setAdapter(listUdhvidhBorodhonDhap_adapter);

        return contentView;
    }

    private void setUpRecyclerViewManual(RecyclerView tempRecyclerView) {
        tempRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        tempRecyclerView.setLayoutManager(layoutManager);


    }
}