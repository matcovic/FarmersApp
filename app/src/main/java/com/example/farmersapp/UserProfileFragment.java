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
import android.widget.Button;
import android.widget.TextView;

import com.example.farmersapp.adapter.ListMySellingItems_Adapter;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public static final String TAG = "checked";


    TextView noSellingPostHint;
    Button buttonProfileEdit;
    List<productsListOfMarketFirestore> mData;
    List<String> addedProductsIdList;

    private RecyclerView recyclerView_mySelling_items;
    private ListMySellingItems_Adapter listMySellingItemsAdapter;
    private TextView textViewDisplayName, textViewDisplayPhoneNo, textViewDisplayDivision, textViewDisplayDistrict, textViewDisplaySubDistrict;
    private TextView textViewDisplayUnion, textViewDisplayThana, textViewDisplayVillage;
    private CollectionReference productsOfMarketCollectionRef = FirebaseFirestore.getInstance().collection("products_of_market");
    boolean noSelling_status = false;

    String mUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollectionRef = db.collection("users");


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

        mData = new ArrayList<>();

        noSellingPostHint = view.findViewById(R.id.no_sellingPost_hint);
        buttonProfileEdit = view.findViewById(R.id.button_profile_edit);

        textViewDisplayDistrict = view.findViewById(R.id.textView_dsiplay_district);
        textViewDisplayDivision = view.findViewById(R.id.textView_dsiplay_division);
        textViewDisplayName = view.findViewById(R.id.textView_display_name);
        textViewDisplayPhoneNo = view.findViewById(R.id.textView_display_phone_no);
        textViewDisplaySubDistrict = view.findViewById(R.id.textView_dsiplay_subDistrict);
        textViewDisplayThana = view.findViewById(R.id.textView_dsiplay_thana);
        textViewDisplayVillage = view.findViewById(R.id.textView_dsiplay_village);
        textViewDisplayUnion = view.findViewById(R.id.textView_dsiplay_union);


        recyclerView_mySelling_items = view.findViewById(R.id.recyclerView_mySelling_items);
        recyclerView_mySelling_items.setHasFixedSize(true);
        recyclerView_mySelling_items.setLayoutManager(new LinearLayoutManager(getActivity()));
        noSellingPostHint.setVisibility(View.INVISIBLE);

        setUpRecyclerViewManual();

        getDataToArrayFromUsers();


        return view;
    }

    private void getDataToArrayFromProductOfFarmer(final List<String> tempList) {



        productsOfMarketCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    Log.d("checked", "database is empty");
                } else {
                    List<productsListOfMarketFirestore> data = queryDocumentSnapshots.toObjects(productsListOfMarketFirestore.class);
                    listMySellingItemsAdapter = new ListMySellingItems_Adapter(mData, getContext());
                    recyclerView_mySelling_items.setAdapter(listMySellingItemsAdapter);
                    Log.d(TAG, String.valueOf(tempList));

                    Log.d(TAG,"selling item size"+data.size());
                    for (productsListOfMarketFirestore currentData : data) {
                        Log.d(TAG, currentData.getProductId());

                        if (currentData.getProductOwner().equals(mUserId)) {
                            noSelling_status = true;
                            mData.add(currentData);
                        } else {
                            Log.d(TAG, "Id is not is array");

                        }
                    }
                    if(noSelling_status)
                    {
                        noSellingPostHint.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        noSellingPostHint.setVisibility(View.VISIBLE);
                    }
//                    mData.addAll(data);
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
    private void getDataToArrayFromUsers() {

        DocumentReference docRef = usersCollectionRef.document(mUserId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        textViewDisplayDistrict.setText(document.getString("district"));
                        textViewDisplayDivision.setText(document.getString("division"));
                        textViewDisplayName.setText(document.getString("name"));
                        textViewDisplayPhoneNo.setText(document.getString("phone"));
                        textViewDisplaySubDistrict.setText(document.getString("subDivision"));
                        textViewDisplayThana.setText(document.getString("thana"));
                        textViewDisplayVillage.setText(document.getString("village"));
                        textViewDisplayUnion.setText(document.getString("union"));

                        addedProductsIdList = (List<String>) document.get("marketProductList");
                        Log.d(TAG, String.valueOf(addedProductsIdList));
                        getDataToArrayFromProductOfFarmer(addedProductsIdList);


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }

    private void setUpRecyclerViewManual() {
        recyclerView_mySelling_items.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
//    adapter = new ListMarket_Alternate_Adapter(mData,this.getContext());
//    marketRecyleView.setAdapter(adapter);
        Log.d("checked", "adapter called");

        recyclerView_mySelling_items.setLayoutManager(layoutManager);


    }

}
