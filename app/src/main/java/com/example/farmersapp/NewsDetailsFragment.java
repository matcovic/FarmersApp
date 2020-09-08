package com.example.farmersapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.farmersapp.adapter.ListNews_Adapter;
import com.example.farmersapp.adapter.ListSuccessStories_Adapter;
import com.example.farmersapp.util.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.farmersapp.adapter.ListNews_Adapter.CULTIVATION_RELATED_NEWS;
import static com.example.farmersapp.adapter.ListNews_Adapter.FUNDING_NEWS;
import static com.example.farmersapp.adapter.ListNews_Adapter.OTHERS_NEWS;


public class NewsDetailsFragment extends Fragment {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private TextView newsArticle_details;
    private ImageView newsImage_details;
    private ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewsDetailsFragment newInstance(String param1, String param2) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        //Invoking toolbar
        toolbar = view.findViewById(R.id.toolbar);
        collapsing_toolbar_layout = view.findViewById(R.id.collapsing_toolbar_layout);
        progressBar = view.findViewById(R.id.progressBar);

        newsArticle_details = view.findViewById(R.id.newsArticle_details);
        newsImage_details = view.findViewById(R.id.newsImage_details);


      String title = getArguments().getString(ListNews_Adapter.NEWS_TITLE);
      String id = getArguments().getString(ListNews_Adapter.NEWS_ID);
      String article = getArguments().getString(ListNews_Adapter.NEWS_ARTICLE);
      int status = getArguments().getInt(ListNews_Adapter.NEWS_STATUS);
        Log.d("checked",id);



        //Here set News Title
        collapsing_toolbar_layout.setTitle(title);

        //Set Article
        newsArticle_details.setText(article);


        StorageReference storageReference ;
        if(status==CULTIVATION_RELATED_NEWS)
        {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/cultivation_related_news/").child(id+ ".jpg");


        }
        else if(status==FUNDING_NEWS)
        {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/funding_news/").child(id+ ".jpg");


        }
        else if(status==OTHERS_NEWS)
        {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://farmersapp-31e06.appspot.com/others_news/").child(id + ".jpg");


        }
        else
        {
            storageReference = null;
        }


        GlideApp.with(newsImage_details.getContext())
                .load(storageReference)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(newsImage_details);


        //Set News ImageView
//        Picasso.get().load("gs://farmersapp-31e06.appspot.com/").into(newsImage_details, new Callback() {
//            @Override
//            public void onSuccess() {
//                progressBar.setVisibility(View.GONE);
//            }
//            @Override
//            public void onError(Exception e) {
//                e.printStackTrace();
//            }
//        });

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return view;
    }
}