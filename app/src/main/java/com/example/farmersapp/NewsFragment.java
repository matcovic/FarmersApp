package com.example.farmersapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farmersapp.adapter.ListNews_Adapter;
import com.example.farmersapp.model.NewsItem;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private MaterialCardView cultivationRelated_Button, bhortuki_Button, others_Button;
    private RecyclerView newsRecyclerView;
TextView totalCountCultivationNewsTextView,totalCountFundingTextView,totalCountOthersTextView;

    private ListNews_Adapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseFirestore firebaseFirestore;
    List<NewsItem> mDataCultivationNews;
    List<NewsItem> mDataFundingNews;
    List<NewsItem> mDataOthersNews;
    List<NewsItem > mData;






    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_news, container, false);

        //Invoking widgets
        cultivationRelated_Button = view.findViewById(R.id.cultivationRelated_Button);
        bhortuki_Button = view.findViewById(R.id.bhortuki_Button);
        others_Button = view.findViewById(R.id.others_Button);
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        totalCountCultivationNewsTextView = view.findViewById(R.id.totalContentCultivation_textView);
        totalCountFundingTextView = view.findViewById(R.id.totalContentBhortuki_textView);
        totalCountOthersTextView = view.findViewById(R.id.totalContentOthers_textView);



        firebaseFirestore = FirebaseFirestore.getInstance();
        mDataCultivationNews = new ArrayList<>();
        mDataFundingNews = new ArrayList<>();
        mDataOthersNews = new ArrayList<>();
        mData = new ArrayList<>();

        final CollectionReference cultivationRelatedNewsCollectionReference = firebaseFirestore.collection("Cultivation_Related_News");
        final CollectionReference fundingNewsCollectionReference = firebaseFirestore.collection("Funding_News");
        final CollectionReference otherNewsCollectionReference = firebaseFirestore.collection("Others_News");

        setUpRecyclerViewManual();


        cultivationRelatedNewsCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint({"LongLogTag", "SetTextI18n"})
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<NewsItem> data = queryDocumentSnapshots.toObjects(NewsItem.class);
                    mDataCultivationNews.addAll(data);
                    totalCountFundingTextView.setText(HomeFragment.digitConversionEngToBangla(mDataCultivationNews.size()));
                    Log.d("checked mdata cultivation size", String.valueOf(mDataCultivationNews.size()));
                }

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });



        fundingNewsCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint({"LongLogTag", "SetTextI18n"})
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<NewsItem> data = queryDocumentSnapshots.toObjects(NewsItem.class);

                    mDataFundingNews.addAll(data);
                    adapter = new ListNews_Adapter(mDataCultivationNews, getContext(),ListNews_Adapter.CULTIVATION_RELATED_NEWS);
                    newsRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    totalCountCultivationNewsTextView.setText(HomeFragment.digitConversionEngToBangla(mDataFundingNews.size()));
                    Log.d("checked mdata funding size", String.valueOf(mDataFundingNews.size()));
                }

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });

        otherNewsCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint({"LongLogTag", "SetTextI18n"})
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<NewsItem> data = queryDocumentSnapshots.toObjects(NewsItem.class);
                    mDataOthersNews.addAll(data);
                    Log.d("checked mdata others size", String.valueOf(mDataOthersNews.size()));
                    totalCountOthersTextView.setText(HomeFragment.digitConversionEngToBangla(mDataOthersNews.size()));
                }

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "data load failed");
            }
        });


        cultivationRelated_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter = new ListNews_Adapter(mDataCultivationNews, getContext(),ListNews_Adapter.CULTIVATION_RELATED_NEWS);
                newsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

        bhortuki_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new ListNews_Adapter(mDataFundingNews, getContext(),ListNews_Adapter.FUNDING_NEWS);
                newsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

        others_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new ListNews_Adapter(mDataOthersNews, getContext(),ListNews_Adapter.OTHERS_NEWS);
                newsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });




        return view;
    }

    private void setUpRecyclerViewManual() {
        newsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        newsRecyclerView.setLayoutManager(layoutManager);


    }

}