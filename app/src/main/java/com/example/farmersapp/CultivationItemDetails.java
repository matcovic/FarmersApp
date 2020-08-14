package com.example.farmersapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CultivationItemDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CultivationItemDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView textViewData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CultivationItemDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CultivationItemDetails newInstance(String param1, String param2) {
        CultivationItemDetails fragment = new CultivationItemDetails();
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
        View contextView = inflater.inflate(R.layout.fragment_cultivation_item_details, container, false);

        textViewData = contextView.findViewById(R.id.textView_data);
        textViewData.setText(getArguments().getString("pass"));
        return contextView;
    }
}
