package com.example.farmersapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersapp.adapter.ListCultivation_Adapter;
import com.example.farmersapp.model.CustomListItem_Cultivation;
import com.example.farmersapp.model.CustomListItem_DiseaseCategories;
import com.example.farmersapp.model.CustomListItem_Diseases;
import com.example.farmersapp.model.DiseaseCategoriesModel;
import com.example.farmersapp.model.DiseasesModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CultivationFragment extends Fragment {

    RecyclerView CultivationRecylerView;
    ListCultivation_Adapter cultivationListAdapterCultivation;
    //    List<CustomListItem_Cultivation> mDataCrops;
    List<CustomListItem_Cultivation> mDataCrops;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";
    List<CustomListItem_DiseaseCategories> mDataDiseaseCategoriesModel;
    List<CustomListItem_Diseases> mDataDiseases;


    public static CultivationFragment newInstance() {
        return new CultivationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("check", "cultivation_fragment");
        View contentView = inflater.inflate(R.layout.cultivation_fragment, container, false);

        rootLayout = contentView.findViewById(R.id.root_layout);
        searchInput = contentView.findViewById(R.id.search_input);
        CultivationRecylerView = contentView.findViewById(R.id.cultivation_rv);
        mDataCrops = new ArrayList<>();
        mDataDiseases = new ArrayList<>();


        Type type = new TypeToken<List<CustomListItem_Cultivation>>() {
        }.getType();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(DataLoadActivity.SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(DataLoadActivity.CULTIVATION_ITEMS, "");
        mDataCrops = gson.fromJson(json, type);


        type = new TypeToken<List<CustomListItem_DiseaseCategories>>() {
        }.getType();
        gson = new Gson();
        json = sharedPreferences.getString(DataLoadActivity.DISEASE_CATEGORIES_MODEL, "");
        mDataDiseaseCategoriesModel = gson.fromJson(json, type);

        Map<String, CustomListItem_DiseaseCategories> diseaseCategoriesMap = new HashMap<>();
        for (CustomListItem_DiseaseCategories i : mDataDiseaseCategoriesModel)
            diseaseCategoriesMap.put(i.getId(), i);



        type = new TypeToken<List<CustomListItem_Diseases>>() {
        }.getType();
        gson = new Gson();
        json = sharedPreferences.getString(DataLoadActivity.DISEASES_MODEL, "");
        mDataDiseases = gson.fromJson(json, type);
        Log.d("check disease data", String.valueOf(mDataDiseases.size()));

        Map<String, CustomListItem_Diseases> diseasesModelMap = new HashMap<>();
        for (CustomListItem_Diseases i : mDataDiseases) {

            Log.d("check" ,i.getDiseaseId());
            diseasesModelMap.put(i.getDiseaseId(), i);
        }

        searchInput.setBackgroundResource(R.drawable.search_input_style);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        Log.d("check map at first", String.valueOf(diseasesModelMap.size()));


        cultivationListAdapterCultivation = new ListCultivation_Adapter(this.getContext(), mDataCrops, diseasesModelMap, diseaseCategoriesMap);CultivationRecylerView.setAdapter(cultivationListAdapterCultivation);
        CultivationRecylerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                cultivationListAdapterCultivation.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return contentView;
    }


}
