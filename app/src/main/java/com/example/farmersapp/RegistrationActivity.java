package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.farmersapp.util.CurrentUserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private EditText enterNameText, phoneNoText, occupationText;
    Spinner birth_dayText, birth_monthText, birth_yearText, divisionText, districtText,
            subDistrictText, unionText, thanaText, villageText;
    private Button regConfirmButton;

    //Declaration for PopUp
    private EditText otpPassText;
    private Button otpConfirmButton;

    String phoneNumber;
    String sourceActivity;

    private FirebaseFirestore db;
    private FirebaseUser mUser;
    private String mUid;

    private String division_name[], district_name_barisal[], union_name_barisal[], subDivision_name_barisal[], village_name_barisal[], thana_name_barisal[];
    private String date[], month[], year[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUid = mUser.getUid();
        enterNameText = findViewById(R.id.regActivity_name);
        phoneNoText = findViewById(R.id.regActivity_phoneNo);
        occupationText = findViewById(R.id.regActivity_occupation);
        birth_dayText = findViewById(R.id.regActivity_birth_day);
        birth_monthText = findViewById(R.id.regActivity_birth_month);
        birth_yearText = findViewById(R.id.regActivity_birth_year);
        divisionText = findViewById(R.id.regActivity_division);
        districtText = findViewById(R.id.regActivity_district);
        subDistrictText = findViewById(R.id.regActivity_subDistrict);
        unionText = findViewById(R.id.regActivity_union);
        thanaText = findViewById(R.id.regActivity_thana);
        villageText = findViewById(R.id.regActivity_village);
        regConfirmButton = findViewById(R.id.regActivity_confimButton);

        phoneNumber = getIntent().getExtras().getString("phone");
        sourceActivity = getIntent().getExtras().getString("activity");


        date = getResources().getStringArray(R.array.date);
        month = getResources().getStringArray(R.array.month);
        year = getResources().getStringArray(R.array.year);
        division_name = getResources().getStringArray(R.array.division_name);
        district_name_barisal = getResources().getStringArray(R.array.district_barisal);
        subDivision_name_barisal = getResources().getStringArray(R.array.subDistrict_barisal);
        union_name_barisal = getResources().getStringArray(R.array.union_gouronodi);
        thana_name_barisal = getResources().getStringArray(R.array.thana_barisal);
        village_name_barisal = getResources().getStringArray(R.array.union_gouronodi);


        ArrayAdapter<String> date_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, date);
        ArrayAdapter<String> month_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, month);
        ArrayAdapter<String> year_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, year);
        ArrayAdapter<String> division_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, division_name);
        ArrayAdapter<String> district_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, district_name_barisal);
        ArrayAdapter<String> subDivision_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, subDivision_name_barisal);
        ArrayAdapter<String> union_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, union_name_barisal);
        ArrayAdapter<String> thana_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, thana_name_barisal);
        ArrayAdapter<String> village_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, village_name_barisal);

        birth_dayText.setAdapter(date_adpter);
        birth_monthText.setAdapter(month_adpter);
        birth_yearText.setAdapter(year_adpter);
        divisionText.setAdapter(division_adpter);
        districtText.setAdapter(district_adpter);
        subDistrictText.setAdapter(subDivision_adpter);
        unionText.setAdapter(union_adpter);
        thanaText.setAdapter(thana_adpter);
        villageText.setAdapter(village_adpter);

        regConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataAsFarmer();



            }
        });
    }

    private void setDataAsFarmer() {
        CurrentUserApi currentUserApi = CurrentUserApi.getInstance(); //Global Api
        currentUserApi.setName(enterNameText.getText().toString());
        currentUserApi.setPhoneNumber(phoneNumber);
        currentUserApi.setUserId(mUid);

        Map<String, Object> user = new HashMap<>();
        List<String> list = new ArrayList<>();
        user.put("name", enterNameText.getText().toString());
        user.put("phone", phoneNoText.getText().toString());
        user.put("dayOfBirth", birth_dayText.getSelectedItem().toString());
        user.put("monthOfBirth", birth_monthText.getSelectedItem().toString());
        user.put("yearOfBirth", birth_yearText.getSelectedItem().toString());
        user.put("division", divisionText.getSelectedItem().toString());
        user.put("district", districtText.getSelectedItem().toString());
        user.put("union", unionText.getSelectedItem().toString());
        user.put("subDivision", subDistrictText.getSelectedItem().toString());
        user.put("village", villageText.getSelectedItem().toString());
        user.put("occupation", occupationText.getText().toString());
        user.put("thana", thanaText.getSelectedItem().toString());
        user.put("logedInPhoneNumber", phoneNumber);
        user.put("userUId",mUid);
        user.put("marketProductList",list);
        // user.put("userId",mUser.toString());
        Log.d("checked","register activity"+phoneNumber+"  ");

        db.collection("users").document(mUid).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        sendUserhome();
                        sendUserToDataLoadActivity();
                        Toast.makeText(RegistrationActivity.this, "You are registered!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrationActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void sendUserhome(){
        Log.d("Print","6");
        Intent homeIntent = new Intent(RegistrationActivity.this,ExploreActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        homeIntent.putExtra("phone",phoneNumber);
        startActivity(homeIntent);
        finish();
    }

    private void sendUserToDataLoadActivity()
    {
        Intent dataLoadIntent= new Intent(RegistrationActivity.this,DataLoadActivity.class);
        startActivity(dataLoadIntent);
        finish();
    }



}
