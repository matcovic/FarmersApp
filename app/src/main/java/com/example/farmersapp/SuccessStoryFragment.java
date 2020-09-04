package com.example.farmersapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.farmersapp.adapter.ListSuccessStories_Adapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

public class SuccessStoryFragment extends Fragment {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar_layout;
    private TextView storyArticle_collapse;
    private ImageView storyImage_details;
    private ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SuccessStoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SuccessStoryFragment newInstance(String param1, String param2) {
        SuccessStoryFragment fragment = new SuccessStoryFragment();
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
        View view =  inflater.inflate(R.layout.fragment_success_story, container, false);

        //Invoking toolbar
        toolbar = view.findViewById(R.id.toolbar);
        collapsing_toolbar_layout = view.findViewById(R.id.collapsing_toolbar_layout);
        progressBar = view.findViewById(R.id.progressBar);

        storyArticle_collapse = view.findViewById(R.id.storyArticle_collapse);
        storyImage_details = view.findViewById(R.id.storyImage_details);

        Bundle args = getArguments();
        collapsing_toolbar_layout.setTitle(args.getString(ListSuccessStories_Adapter.STORY_TITLE));
        storyArticle_collapse.setText(args.getString(ListSuccessStories_Adapter.STORY_ARTICLE));
        Picasso.get().load(args.getString(ListSuccessStories_Adapter.STORY_IMAGE_URL)).into(storyImage_details, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });


        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return view;
    }
}