package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.farmersapp.util.CurrentUserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private EditText enterNameText, phoneNoText, occupationText, birthDateText;
    Spinner birth_dayText, birth_monthText, birth_yearText, divisionText, districtText,
            subDistrictText, unionText, thanaText, villageText;
    private MaterialButton regConfirmButton;

    //Declaration for PopUp
    private EditText otpPassText;
    private Button otpConfirmButton;

    String phoneNumber;
    String sourceActivity;

    private FirebaseFirestore db;
    private FirebaseUser mUser;
    private String mUid;

    private String division_name[], district_name_barisal[], union_name_barisal[], subDivision_name_barisal[], village_name_barisal[], thana_name_barisal[];
//    private String date[], month[], year[];
    private String birth_dayOfMonth, birth_month, birth_year;


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
        birthDateText = findViewById(R.id.regActivity_birthDate);
        divisionText = findViewById(R.id.regActivity_division);
        districtText = findViewById(R.id.regActivity_district);
        subDistrictText = findViewById(R.id.regActivity_subDistrict);
        unionText = findViewById(R.id.regActivity_union);
        thanaText = findViewById(R.id.regActivity_thana);
        villageText = findViewById(R.id.regActivity_village);
        regConfirmButton = findViewById(R.id.regActivity_confimButton);

        phoneNumber = getIntent().getExtras().getString("phone");
        sourceActivity = getIntent().getExtras().getString("activity");

        //Birthday Date picker
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfmonth = calendar.get(Calendar.DAY_OF_MONTH);

        birthDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        birth_dayOfMonth = digitConversionEngToBangla(dayOfMonth);
                        birth_month = digitConversionEngToBangla(month);
                        birth_year = digitConversionEngToBangla(year);
                        String date = birth_dayOfMonth+"/"+birth_month+"/"+birth_year;
                        birthDateText.setText(date);
                    }
                },year,month,dayOfmonth);
                datePickerDialog.show();
            }
        });

        division_name = getResources().getStringArray(R.array.division_name);
        district_name_barisal = getResources().getStringArray(R.array.district_barisal);
        subDivision_name_barisal = getResources().getStringArray(R.array.subDistrict_barisal);
        union_name_barisal = getResources().getStringArray(R.array.union_gouronodi);
        thana_name_barisal = getResources().getStringArray(R.array.thana_barisal);
        village_name_barisal = getResources().getStringArray(R.array.union_gouronodi);


        ArrayAdapter<String> division_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, division_name);
        ArrayAdapter<String> district_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, district_name_barisal);
        ArrayAdapter<String> subDivision_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, subDivision_name_barisal);
        ArrayAdapter<String> union_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, union_name_barisal);
        ArrayAdapter<String> thana_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, thana_name_barisal);
        ArrayAdapter<String> village_adpter = new ArrayAdapter<String>(this, R.layout.sample_layout_spinner, R.id.sample_textView, village_name_barisal);

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
        user.put("dayOfBirth", birth_dayOfMonth);
        user.put("monthOfBirth", birth_month);
        user.put("yearOfBirth", birth_year);
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

    public String digitConversionEngToBangla(int value) {
        StringBuilder result = new StringBuilder();
        String temp = Integer.toString(value);

        for(int i = 0; i<temp.length(); i++) {
            if(temp.charAt(i) == '0')
                result.append("০");
            else if(temp.charAt(i) == '1')
                result.append("১");
            else if(temp.charAt(i) == '2')
                result.append("২");
            else if(temp.charAt(i) == '3')
                result.append("৩");
            else if(temp.charAt(i) == '4')
                result.append("৪");
            else if(temp.charAt(i) == '5')
                result.append("৫");
            else if(temp.charAt(i) == '6')
                result.append("৬");
            else if(temp.charAt(i) == '7')
                result.append("৭");
            else if(temp.charAt(i) == '8')
                result.append("৮");
            else if(temp.charAt(i) == '9')
                result.append("৯");
        }
        return result.toString();
    }



}
