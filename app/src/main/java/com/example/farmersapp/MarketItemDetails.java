package com.example.farmersapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.farmersapp.adapter.ViewPagerImage_MarketDetails_Adapter;
import com.example.farmersapp.model.productsListOfMarketFirestore;
import com.example.farmersapp.adapter.Slider_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.SliderView;


public class MarketItemDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private TextView textViewProductTitle, textViewProductPrice, textViewProductLoctaion, textViewProductConditionType, textViewProductCategory, textViewProductTime;
    private TextView textViewOwnerNumber, textViewProductDescription, TextViewProductConditionTypeLebel;
    private Context mContext;
    private SliderView sliderView;
    private Button imageButton;
    ViewPagerImage_MarketDetails_Adapter viewPagerImageAdapter;
    String phoneNumner;


    public MarketItemDetails() {
        // Required empty public constructor
    }

    public static MarketItemDetails newInstance(String param1, String param2) {
        MarketItemDetails fragment = new MarketItemDetails();
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
        View contextView = inflater.inflate(R.layout.fragment_market_item_details_imageslider, container, false);
        mContext = contextView.getContext();

        final String productRegion;
        final String productArea;
        final String productCategory;
        final String productTitle;
        final String productDescription;
        final String productPrice;
        final String productCondition;


        textViewOwnerNumber = contextView.findViewById(R.id.textView_number);
        textViewProductCategory = contextView.findViewById(R.id.textView_productCategory);
        textViewProductConditionType = contextView.findViewById(R.id.textView_productConditonType);
        textViewProductDescription = contextView.findViewById(R.id.textView_productDescripation);
        textViewProductLoctaion = contextView.findViewById(R.id.textView_productLocation);
        textViewProductPrice = contextView.findViewById(R.id.textView_productPrice);
        textViewProductTime = contextView.findViewById(R.id.textView_productTime);
        textViewProductTitle = contextView.findViewById(R.id.textView_productitle);
        TextViewProductConditionTypeLebel = contextView.findViewById(R.id.textView_condition_type_lebel);
        imageButton = contextView.findViewById(R.id.imageButton_call);

//        viewPager = contextView.findViewById(R.id.viewPage_productsImage);
            sliderView = contextView.findViewById(R.id.imageSlider);
        if (!textViewProductCategory.getText().equals("Instrument")) {
            textViewProductConditionType.setText("Type:");
        } else {
            textViewProductConditionType.setText("Condition:");
        }


        final String productId = getArguments().getString("productId");
        DocumentReference productCollectionRef = FirebaseFirestore.getInstance().document("products_of_market/" + productId);
        final CollectionReference usesCollectionRef = FirebaseFirestore.getInstance().collection("users");


        productCollectionRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                productsListOfMarketFirestore productsDetails = documentSnapshot.toObject(productsListOfMarketFirestore.class);


                String[] images = new String[]{
                   "user_image1/"+productId+".jpg","user_image2/"+productId+".jpg"
                };


//                viewPagerImageAdapter = new ViewPagerImage_MarketDetails_Adapter(mContext, images);
//                viewPager.setAdapter(viewPagerImageAdapter);
                Slider_Adapter adapter = new Slider_Adapter(getContext(),images);

                sliderView.setSliderAdapter(adapter);

                usesCollectionRef.document(documentSnapshot.getString("productOwner")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            phoneNumner = document.getString("phone");
                            textViewOwnerNumber.setText(phoneNumner);
                        }
                        else
                        {

                        }


                    }
                }) ;


                textViewProductCategory.setText(documentSnapshot.getString("productCategory"));
                textViewProductConditionType.setText(documentSnapshot.getString("productCondition"));
                textViewProductDescription.setText(documentSnapshot.getString("productDescription"));
                textViewProductLoctaion.setText(documentSnapshot.getString("productArea") + ", " + documentSnapshot.getString("productRegion"));

                textViewProductPrice.setText("৳" + documentSnapshot.getString("productPrice"));
                textViewProductTime.setText(documentSnapshot.getString("productUploadedTime"));
                textViewProductTitle.setText(documentSnapshot.getString("productTitle"));
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                        dialIntent.setData(Uri.parse("tel:" + Uri.encode(phoneNumner)));
                        startActivity(dialIntent);

                    }
                });


//                Log.d("checked", productsDetails.getProductTitle());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        return contextView;
    }

}
