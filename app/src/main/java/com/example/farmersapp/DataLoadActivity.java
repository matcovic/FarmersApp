package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farmersapp.model.CropsModel;
import com.example.farmersapp.model.CustomListItem_Cultivation;
import com.example.farmersapp.model.CustomListItem_DiseaseCategories;
import com.example.farmersapp.model.CustomListItem_Diseases;
import com.example.farmersapp.model.DiseaseCategoriesModel;
import com.example.farmersapp.model.DiseasesModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import is.arontibo.library.ElasticDownloadView;

public class DataLoadActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "offlineData";
    public static final String DISEASE_CATEGORIES_MODEL = "DiseaseCategoriesModel";
    public static final String CROPS_MODEL = "CropsModel";
    public static final String DISEASES_MODEL = "DiseasesModel";
    public static final String CULTIVATION_ITEMS = "cultivationItems";
    public static final String DATA_DISEASE = "dataDisease";

    ProgressBar progressBarDataLoad;

    ElasticDownloadView mElasticDownloadView;

    Button mButton;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReferenceCrops = firebaseFirestore.collection("Crops");
    private CollectionReference collectionReferenceDiseases = firebaseFirestore.collection("Diseases");
    private CollectionReference collectionReferenceDiseaseCategories = firebaseFirestore.collection("Disease_Categories");

    List<CustomListItem_Cultivation> mData;
    List<CustomListItem_Diseases>mDataDisease;
    List<CustomListItem_DiseaseCategories>mDataDiseaseCategories;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean check1, check2, check3;
    int iterationCnt=1 ;
    int iterationCntDisease = 1;
    int iterationCntDiseasCategories = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_load);
        mElasticDownloadView = findViewById(R.id.elastic_download_view);
        mElasticDownloadView.startIntro();

        mButton = findViewById(R.id.next_button);

        mData = new ArrayList<>();
        mDataDisease = new ArrayList<>();
        mDataDiseaseCategories = new ArrayList<>();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserhome();
            }
        });


        check1 = false;
        check2 = false;
        check3 = false;

        collectionReferenceCrops.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<CropsModel> data = queryDocumentSnapshots.toObjects(CropsModel.class);
                    addCrops(data);
                    Log.d("datasetdata", String.valueOf(data));
                    check1 = true;
                    mElasticDownloadView.setProgress(33);

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });
        collectionReferenceDiseaseCategories.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<DiseaseCategoriesModel> data = queryDocumentSnapshots.toObjects(DiseaseCategoriesModel.class);
                    addDiseaseCategories(data);
                    Log.d("datasetdata", String.valueOf(data));
                    check2 = true;
                    mElasticDownloadView.setProgress(66);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


        collectionReferenceDiseases.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<DiseasesModel> data = queryDocumentSnapshots.toObjects(DiseasesModel.class);
                    addDisease(data);
                    Log.d("datasetdata", String.valueOf(data));
                    check3 = true;
                    mElasticDownloadView.setProgress(100);
                    mElasticDownloadView.success();


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


        if (check1 && !check2 && !check3) {
            mElasticDownloadView.setProgress(33);
        } else if (check1 && check2 && !check3) {
            mElasticDownloadView.setProgress(66);
        } else if (check1 && check2 && check3) {
            mElasticDownloadView.setProgress(100);
            mElasticDownloadView.success();
//            sendUserhome();

        }

    }

    private void addDiseaseCategories(List<DiseaseCategoriesModel> data) {

        for(DiseaseCategoriesModel categoriesModel : data)
        {
            mDataDiseaseCategories.add(new CustomListItem_DiseaseCategories(categoriesModel.getCharaRoponDhap(),categoriesModel.getFolonDhap(),categoriesModel.getFosolKatarDhap(),categoriesModel.getPushpoDhap(),categoriesModel.getUdhvidhBordhonDhap(),categoriesModel.getId()));
            if(data.size()==iterationCntDiseasCategories)
            {
                Gson gson = new Gson();
                String json = gson.toJson(mDataDiseaseCategories);
                editor.putString(DISEASE_CATEGORIES_MODEL, json);
                editor.apply();
            }
            iterationCntDiseasCategories++;
        }

        Log.d("dataset", "stored ok1");
    }

    private void addCrops(final List<CropsModel> data) {
        Log.d("checked", "crops here");

        for ( final CropsModel crop : data) {

            Log.d("checked", "crops here1 "+crop.getNameEnglish());
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/crops/").child(crop.getNameEnglish() + ".jpg");

            storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {


                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);

                    byte[] b = baos.toByteArray();

                    String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


                    mData.add(new CustomListItem_Cultivation(crop.getNameBangla(),crop.getNameEnglish(),imageEncoded));
                    if(data.size()==iterationCnt)
                    {

                        Gson gson = new Gson();
                        String json= gson.toJson(mData);
                        editor.putString(CULTIVATION_ITEMS,json);
                        editor.apply();

                    }
                    iterationCnt++;

                }
            });

        }


        Log.d("dataset", "stored ok2");
    }

    private void addDisease(final List<DiseasesModel> data) {


        for (final DiseasesModel disease : data)
        {

            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/diseases/").child(disease.getDiseaseId() + ".jpg");

            storageReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {


                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);

                    byte[] b = baos.toByteArray();

                    String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
                    mDataDisease.add(new CustomListItem_Diseases(disease.getDiseaseBiologicalControl(),disease.getDiseaseBrief(),disease.getDiseaseCause(),disease.getDiseaseChemicalControl(),disease.getDiseaseScientificName(),disease.getDiseaseTitle(),disease.getDiseaseType(),disease.getDiseaseId(),imageEncoded));
                    Log.d("check data disese size" , String.valueOf(mDataDisease.size()));
                    if(data.size()==iterationCntDisease)
                    {

                        Gson gson = new Gson();
                        String json = gson.toJson(mDataDisease);
                        editor.putString(DISEASES_MODEL, json);
                        editor.apply();

                    }



                    iterationCntDisease++;
                }});

        }

    }

    private void sendUserhome() {
        Intent homeIntent = new Intent(DataLoadActivity.this, ExploreActivity.class);
        startActivity(homeIntent);
        finish();
    }


}