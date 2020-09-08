package com.example.farmersapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmersapp.adapter.SliderForPopup_Adapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.SliderView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class AddNewItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Spinner spinnerRegion, spinnerArea, spinnerCatagory;
    private RadioButton radioButtonUsed, radioButtonNew, radioButtonBoth, radioButton;
    private RadioGroup radioGroup;
    private TextView textViewErrorMessage;
    private EditText editTextTitle, editTextDescription, editTextPrice;
    private Button buttonSubmit, buttonPreview;
    private String region[], dhaka[], sylhet[], category[];
    private int pos;
    private String userId;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Bitmap compressed1, compressed2;
    private Uri imageUri1 = null, imageUri = null;
    private ProgressBar progressBar;
    private ImageView imageView1, imageView2;
    private StorageReference storageReference;
    private DocumentReference databaseReferenceId;
    private boolean ch = false, ch1 = false;
    private CollectionReference userCollectionRef = FirebaseFirestore.getInstance().collection("users");

    private int IMAGE_PICKER_SELECT = 0;
    private int calledChoose;

    private int productId;
    private TextView textViewProductTitle, textViewProductPrice, textViewProductLoctaion, textViewProductConditionType, textViewProductCategory, textViewProductTime;
    private TextView textViewOwnerNumber, textViewProductDescription, TextViewProductConditionTypeLebel;


    private String productIdString;
    private String imageUriString;
    private String imageUriString1;

    private SliderView sliderView;
    private ScrollView scrollView;
    private RelativeLayout relativeLayout;

    String productCondition;

    private AlertDialog dialog;
    private ProgressDialog progressDialog;


    private String mParam1;
    private String mParam2;

    public static AddNewItemFragment newInstance(String param1, String param2) {
        AddNewItemFragment fragment = new AddNewItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddNewItemFragment() {
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

        final View contentView = inflater.inflate(R.layout.fragment_add_new_item, container, false);

        spinnerArea = contentView.findViewById(R.id.spinner_area);
        spinnerRegion = contentView.findViewById(R.id.spinner_region);
        spinnerCatagory = contentView.findViewById(R.id.spinner_category);
        radioGroup = contentView.findViewById(R.id.radio_group);
        radioButtonNew = contentView.findViewById(R.id.radioButton_new);
        radioButtonUsed = contentView.findViewById(R.id.radioButton_used);
        radioButtonBoth = contentView.findViewById(R.id.radioButton_both);

        buttonPreview = contentView.findViewById(R.id.button_preview);
        buttonSubmit = contentView.findViewById(R.id.button_submit);
        imageView1 = contentView.findViewById(R.id.imageView_demopic1);
        imageView2 = contentView.findViewById(R.id.imageView_demopic2);

        textViewErrorMessage = contentView.findViewById(R.id.textView_error_message);

        editTextTitle = contentView.findViewById(R.id.editText_title);
        editTextPrice = contentView.findViewById(R.id.editText_price);
        editTextDescription = contentView.findViewById(R.id.editText_description);

        scrollView = contentView.findViewById(R.id.scrollview_item_info);

        progressDialog = new ProgressDialog(getContext());


        firebaseAuth = FirebaseAuth.getInstance();

        userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        databaseReferenceId = firebaseFirestore.document("productId/currectId");

        databaseReferenceId.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                productId = Integer.parseInt(Objects.requireNonNull(documentSnapshot.getString("id")));

                productIdString = Integer.toString(productId);
                Log.d("checked", productIdString);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "Id collection open failed");
            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        choseImage();
                        calledChoose = 1;
                    }
                } else {
                    choseImage();

                    calledChoose = 1;
                }

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        choseImage();

                        calledChoose = 2;
                    }
                } else {
                    choseImage();

                    calledChoose = 2;
                }

            }
        });


        region = getResources().getStringArray(R.array.region);
        dhaka = getResources().getStringArray(R.array.Dhaka_area);
        sylhet = getResources().getStringArray(R.array.sylhet_area);
        category = getResources().getStringArray(R.array.category);


        ArrayAdapter<String> adapterRegion = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, region);
        adapterRegion.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapterRegion);

        ArrayAdapter<String> adapterDhaka = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, dhaka);

        adapterDhaka.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerArea.setAdapter(adapterDhaka);

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), region[position], Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    Log.d("checked", "spinner at dhaka");

                    ArrayAdapter<String> adapterDhaka = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, dhaka);

                    adapterDhaka.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerArea.setAdapter(adapterDhaka);

                } else {
                    Log.d("checked", "spinner at sylhet");

                    ArrayAdapter<String> adapterSylhet = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, sylhet);

                    adapterSylhet.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    spinnerArea.setAdapter(adapterSylhet);


                }


                Log.d("checked", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, category);

        adapterCategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerCatagory.setAdapter(adapterCategory);

        spinnerCatagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)//Pesticides
                {

                    radioButtonNew.setText("Retail");
                    radioButtonUsed.setText("Wholesale");
                    radioButtonBoth.setVisibility(View.VISIBLE);

                } else if (position == 1)//crops
                {
                    radioButtonNew.setText("Retail");
                    radioButtonUsed.setText("Wholesale");
                    radioButtonBoth.setVisibility(View.VISIBLE);
                } else if (position == 2)//Instrument
                {
                    radioButtonNew.setText("New");
                    radioButtonUsed.setText("Used");
                    radioButtonBoth.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int seleted = radioGroup.getCheckedRadioButtonId();
                radioButton = contentView.findViewById(seleted);
                productCondition = radioButton.getText().toString();
            }
        });


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String productRegion = spinnerRegion.getSelectedItem().toString();
                final String productArea = spinnerArea.getSelectedItem().toString();
                final String productCategory = spinnerCatagory.getSelectedItem().toString();
                final String productTitle = editTextTitle.getText().toString();
                final String productDescription = editTextDescription.getText().toString();
                final String productPrice = editTextPrice.getText().toString();

                Log.d("checkedDes", productDescription);
                if (!TextUtils.isEmpty(productArea) && !TextUtils.isEmpty(productRegion) && !TextUtils.isEmpty(productCategory) && !TextUtils.isEmpty(productCondition) && !TextUtils.isEmpty(productDescription) && !TextUtils.isEmpty(productPrice) && !TextUtils.isEmpty(productTitle) && imageUri != null && imageUri1 != null) {

                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(R.color.fui_transparent);
                    progressDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


                    final StorageReference riversRef = storageReference.child("user_image1/" + productId + ".jpg");
                    UploadTask uploadTask = riversRef.putFile(imageUri);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageUriString = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString();
                            Log.d("checked 1", imageUriString);

                            Log.d("checked", "image1 upload task ok");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("checked", "image upload failed 1");


                        }
                    });

                    final StorageReference riversRef1 = storageReference.child("user_image2/" + productId + ".jpg");
                    UploadTask uploadTask1 = riversRef1.putFile(imageUri1);

                    uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Log.d("checked", "image2 upload task ok");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("checked", "image upload failed 2");


                        }
                    });

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }
                            return riversRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();


                                imageUriString = downloadUri.toString();
                                Log.d("checked 1", downloadUri.toString());
                                ch = true;
                                if (ch1) {
                                    storeData(productRegion, productArea, productCategory, productTitle, productDescription, productPrice, productCondition);
                                }


                            } else {
                                Log.d("checked", "download uri error 1");

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("checked", e.toString());
                        }
                    });

                    Task<Uri> uriTask1 = uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw Objects.requireNonNull(task.getException());
                            }
                            return riversRef1.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();


                                assert downloadUri != null;
                                imageUriString1 = downloadUri.toString();
                                ch1 = true;


                                if (ch) {
                                    storeData(productRegion, productArea, productCategory, productTitle, productDescription, productPrice, productCondition);
                                }
                                Log.d("checked 2", downloadUri.toString());
                            } else {
                                Log.d("checked", "download uri error 2");

                            }
                        }
                    });

                } else {
                    if (TextUtils.isEmpty(productCondition)) {
                        textViewErrorMessage.setText("Fill the Condition/Type!!!!");
                    } else if (TextUtils.isEmpty(productDescription)) {
                        Log.d("checked", productDescription);
                        textViewErrorMessage.setText("Description!!!!");
                    } else if (TextUtils.isEmpty(productPrice)) {
                        textViewErrorMessage.setText("Price!!!!");
                    } else if (TextUtils.isEmpty(productTitle)) {
                        textViewErrorMessage.setText("Title!!!!");
                    } else if (imageUri1 == null || imageUri == null) {
                        textViewErrorMessage.setText("Select images!!!!");

                    } else {
                        textViewErrorMessage.setText("Check all the fields Again!!!!");
                    }


                }

            }
        });


        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                createOTPpopupDialog();
            }
        });

        return contentView;
    }


    @SuppressLint("SetTextI18n")
    private void createOTPpopupDialog() {


        Uri images[] = new Uri[]{imageUri, imageUri1};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View contextView = getLayoutInflater().inflate(R.layout.fragment_market_item_details_imageslider, null);
        textViewOwnerNumber = contextView.findViewById(R.id.textView_number);
        textViewProductCategory = contextView.findViewById(R.id.textView_productCategory);
        textViewProductConditionType = contextView.findViewById(R.id.textView_productConditonType);
        textViewProductDescription = contextView.findViewById(R.id.textView_productDescripation);
        textViewProductLoctaion = contextView.findViewById(R.id.textView_productLocation);
        textViewProductPrice = contextView.findViewById(R.id.textView_productPrice);
        textViewProductTime = contextView.findViewById(R.id.textView_productTime);
        textViewProductTitle = contextView.findViewById(R.id.textView_productitle);
        sliderView = contextView.findViewById(R.id.imageSlider);


        SliderForPopup_Adapter adapter = new SliderForPopup_Adapter(getContext(), images);

        sliderView.setSliderAdapter(adapter);


//                textViewOwnerNumber.setText(documentSnapshot.getString());
        textViewProductCategory.setText(spinnerCatagory.getSelectedItem().toString());
        textViewProductConditionType.setText(productCondition);
        textViewProductDescription.setText(editTextDescription.getText().toString());
        textViewProductLoctaion.setText(spinnerArea.getSelectedItem().toString() + ", " + spinnerRegion.getSelectedItem().toString());

        textViewProductPrice.setText("৳" + editTextPrice.getText().toString());
//                textViewProductTime.setText(documentSnapshot.getString());
        textViewProductTitle.setText(editTextTitle.getText().toString());

        builder.setView(contextView);
        dialog = builder.create();
        dialog.show();


    }

    private void storeData(String productRegion, String productArea, String productCategory, String productTitle, String productDescription, String productPrice, String productCondition) {

        DateFormat df = SimpleDateFormat.getDateTimeInstance();
        String dateText = df.format(new Date());

        String banglaDateTime = banglaDateTimeMaker(dateText);


        Map<String, String> productData = new HashMap<>();
        productData.put("productOwner", userId);
        productData.put("productId", productIdString);
        productData.put("productRegion", productRegion);
        productData.put("productArea", productArea);
        productData.put("productCategory", productCategory);
        productData.put("productTitle", productTitle);
        productData.put("productDescription", productDescription);
        productData.put("productPrice", productPrice);
        productData.put("productCondition", productCondition);
        productData.put("productUploadedTime", banglaDateTime);
        productData.put("productSoldStatus", "no");


        DocumentReference userDocRef = userCollectionRef.document(userId);

        userDocRef.update("marketProductList", FieldValue.arrayUnion(productId));


        firebaseFirestore.collection("products_of_market").document(productIdString).set(productData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


                progressDialog.dismiss();
                progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                Fragment fragment = new MarketFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Toast.makeText(getContext(), "product is uploaded successfully", Toast.LENGTH_SHORT).show();

            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "(FIRESTORE Error) : " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });


        productId++;

        productIdString = Integer.toString(productId);
        Map<String, String> mp = new HashMap<>();
        mp.put("id", productIdString);
        databaseReferenceId.set(mp);


    }

    public String banglaDateTimeMaker(String dateText) {
        StringBuilder dateTime = new StringBuilder();

        String[] tokens = dateText.split(" ");


        for (int i = 0; i < tokens.length; i++) {
            StringBuilder temp = new StringBuilder();

            if (i == 1 || i == 4) {
                switch (tokens[i]) {
                    case "January":
                        temp = new StringBuilder("জানু");

                        break;
                    case "February":
                        temp = new StringBuilder("ফেব্রু");

                        break;
                    case "March":
                        temp = new StringBuilder("মার্চ");

                        break;
                    case "April":
                        temp = new StringBuilder("এপ্রিল");
                        break;
                    case "May":
                        temp = new StringBuilder("মে");
                        break;
                    case "June":
                        temp = new StringBuilder("মে");
                        break;
                    case "July":
                        temp = new StringBuilder("মে");
                        break;
                    case "August":
                        temp = new StringBuilder("মে");
                        break;
                    case "September":
                        temp = new StringBuilder("মে");
                        break;
                    case "October":
                        temp = new StringBuilder("মে");
                        break;
                    case "November":
                        temp = new StringBuilder("মে");
                        break;
                    case "December":
                        temp = new StringBuilder("মে");
                        break;
                    case "am":
                        temp = new StringBuilder("সকাল");
                        break;
                    case "pm":
                        temp = new StringBuilder("রাত");
                        break;
                }
            } else {
                temp = new StringBuilder();
                for (int j = 0; j < tokens[i].length(); j++) {
                    if ('0' <= tokens[i].charAt(j) && tokens[i].charAt(j) <= '9') {
                        if (tokens[i].charAt(j) == '0') {
                            temp.append("০");

                        } else if (tokens[i].charAt(j) == '1') {
                            temp.append("১");

                        } else if (tokens[i].charAt(j) == '2') {
                            temp.append("২");
                        } else if (tokens[i].charAt(j) == '3') {
                            temp.append("৩");
                        } else if (tokens[i].charAt(j) == '4') {
                            temp.append("৪");
                        } else if (tokens[i].charAt(j) == '5') {
                            temp.append("৫");
                        } else if (tokens[i].charAt(j) == '6') {
                            temp.append("৬");
                        } else if (tokens[i].charAt(j) == '7') {
                            temp.append("৭");
                        } else if (tokens[i].charAt(j) == '8') {
                            temp.append("৮");
                        } else if (tokens[i].charAt(j) == '9') {
                            temp.append("৯");
                        }
                    } else {
                        temp.append(":");
                    }
                }

            }
            tokens[i] = temp.toString();
        }
        for (String str : tokens) {
            dateTime.append(str).append(" ");
        }
        Log.d("checked", dateTime.toString());
        return dateTime.toString();

    }

    private void choseImage() {
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(1, 1)
//                .start(getActivity());
        Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICKER_SELECT);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            String path = getPathFromCameraData(data, this.requireActivity());
            Log.i("PICTURE", "Path: " + path);
            if (path != null) {

                if (calledChoose == 1) {
                    imageUri = data.getData();
                    Log.d("checkeduri", Objects.requireNonNull(data.getData()).toString());
                    imageView1.setImageURI(imageUri);
                } else if (calledChoose == 2) {
                    imageUri1 = data.getData();
                    Log.d("checkeduri", Objects.requireNonNull(data.getData()).toString());
                    imageView2.setImageURI(imageUri1);

                } else {
                    Log.d("checker", "onActivity result error");
                }
            }
        }

    }

    private static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        assert selectedImage != null;
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }


}
