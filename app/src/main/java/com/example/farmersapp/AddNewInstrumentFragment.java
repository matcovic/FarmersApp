package com.example.farmersapp;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.farmersapp.model.Instrument;
import com.example.farmersapp.ui.MapsActivity_AddingInstrument;
import com.example.farmersapp.util.CurrentUserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AddNewInstrumentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int GALLERY_CODE = 1;
    private static final String TAG = "ADDING INSTRUMENT";
    private String mParam1;
    private String mParam2;

    //widgets
    private String currentUserId, currentUserName, currentUserPhone;
    private String instrumentTypeText, instrumentPriceText, instrumentLocationText;
    private EditText perHourPriceEditText, setLocationEditText;
    private Spinner instrumentTypeSpinner;
    private MaterialButton confirmInstrument_reg_Button;
    private ImageView instrumentImage;
    private ProgressBar progressBar;

    //variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    //Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Instrument_Owners");
    DocumentReference documentReference = db.collection("Instrument_Owners").document();
    private Uri imageUri;

    public AddNewInstrumentFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static AddNewInstrumentFragment newInstance(String param1, String param2) {
        AddNewInstrumentFragment fragment = new AddNewInstrumentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test","onCreateCalled");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Test","onCreateViewCalled");
        View view = inflater.inflate(R.layout.fragment_add_new_instrument, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        //spinner
        instrumentTypeSpinner = view.findViewById(R.id.instrumentTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.instrumentType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instrumentTypeSpinner.setAdapter(adapter);
        instrumentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instrumentTypeText = parent.getItemAtPosition(position).toString().trim();
                Log.d("Test",instrumentTypeText+" Selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                instrumentTypeText = null;
            }
        });
        perHourPriceEditText = view.findViewById(R.id.perHourPrice);
        setLocationEditText = view.findViewById(R.id.setLocation);
        confirmInstrument_reg_Button = view.findViewById(R.id.confirmInstrument_reg_Button);
        instrumentImage = view.findViewById(R.id.instrumentImage);

        //Progress Bar
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //Getting Current user's name, phone and userId
        if(CurrentUserApi.getInstance() != null) {
            currentUserId = CurrentUserApi.getInstance().getUserId();
            currentUserName = CurrentUserApi.getInstance().getName();
            currentUserPhone = CurrentUserApi.getInstance().getPhoneNumber();
            Log.d("Test",currentUserId +" "+currentUserName+" "+currentUserPhone+ "From AddNewInstrumentFragment");
        }else {
            Log.d("Test","CurrentUser is null. From AddNewInstrumentFragment");
        }

        instrumentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting image from gallery
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);

            }
        });

        setLocationEditText.setFocusable(false);
        setLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open MapActivity
                startActivity(new Intent(getActivity(), MapsActivity_AddingInstrument.class));
            }
        });

        confirmInstrument_reg_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save Instrument
                saveInstrument();

            }
        });

        return view;
    }

    private void saveInstrument() {
        instrumentPriceText = perHourPriceEditText.getText().toString().trim();
        instrumentLocationText = setLocationEditText.getText().toString().trim();
        final String uniqueId = documentReference.getId();
        Log.d("Test","Before invoking Collection....."+documentReference.getId());

        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(instrumentPriceText) && !TextUtils.isEmpty(instrumentLocationText)
                && !TextUtils.isEmpty(instrumentTypeText) && imageUri != null) {

            final StorageReference filePath = storageReference.child("instrument_images")
                    .child("by_"+currentUserId+"_"+ Timestamp.now().getSeconds()); //by_userId_84085738

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageUrl = uri.toString();
                            //Creating Instrument Object -model
                            Instrument instrument = new Instrument();
                            instrument.setOwnerId(currentUserId);
                            instrument.setOwnerName(currentUserName);
                            instrument.setOwnerPhone(currentUserPhone);
                            instrument.setInstrumentId(uniqueId);
                            instrument.setInstrumentType(instrumentTypeText);
                            instrument.setInstrumentPrice(instrumentPriceText);
                            instrument.setInstrumentAddress(instrumentLocationText);
                            instrument.setInstrumentTimeAdded(new Timestamp(new Date()));
                            instrument.setInstrumentImageUrl(imageUrl);
                            instrument.setInstrumentGeoPoint(CurrentUserApi.getInstance().getGeoPoint_of_instrument());

                            //Invoking Collection Reference
                            collectionReference.document(uniqueId).set(instrument).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    //Transition of Fragment Code here
                                    Log.d("Test","After invoking Collection....."+documentReference.getId());
                                    Toast.makeText(getActivity(), "Instrument Ad Successfully Posted!", Toast.LENGTH_SHORT).show();
                                    Fragment fragment = new VehicleFragment();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
//                            collectionReference.add(instrument)
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    progressBar.setVisibility(View.INVISIBLE);
//                                    //Transition of Fragment Code here
//                                    Log.d("Test","After invoking Collection....."+documentReference.getId());
//                                    Toast.makeText(getActivity(), "Instrument Ad Successfully Posted!", Toast.LENGTH_LONG).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG,"onFailure: "+e.getMessage());
//                                }
//                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }else {
            Toast.makeText(getActivity(),"Fill all the fields properly",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if(data != null) {
                imageUri = data.getData(); //Getting actual path of the image
                instrumentImage.setImageURI(imageUri); //Showing image
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test","onResume");
        if(CurrentUserApi.getInstance().getAddress_of_instrument() != null) {
            setLocationEditText.setText(CurrentUserApi.getInstance().getAddress_of_instrument());
        }else {
            setLocationEditText.setHint(R.string.select_location);
        }
    }
}
