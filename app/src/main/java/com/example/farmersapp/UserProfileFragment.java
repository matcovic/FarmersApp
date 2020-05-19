package com.example.farmersapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.farmersapp.adapter.ListMySellingItems_Adapter;

public class UserProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    TextView no_sellingPost_hint;
    Button profileEditButton;

    private RecyclerView recyclerView_mySelling_items;
    private ListMySellingItems_Adapter listMySellingItemsAdapter;



    public UserProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        no_sellingPost_hint = view.findViewById(R.id.no_sellingPost_hint);
        profileEditButton = view.findViewById(R.id.profileEditButton);

        recyclerView_mySelling_items = view.findViewById(R.id.recyclerView_mySelling_items);
        recyclerView_mySelling_items.setHasFixedSize(true);
        recyclerView_mySelling_items.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
