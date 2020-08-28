package com.example.farmersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmersapp.util.CurrentUserApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FarmerLoginActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private static final String TAG = "Printing";
    private EditText phoneNumberText;
    //   private EditText pinNumberText;
//    private TextView forgotPinText;
    private Button loginButton;
//    private TextView createAccount;

    //Declaration for PopUp
    private EditText otpPassText;
    private Button otpConfirmButton;
    private ProgressBar progressBar;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks McallBack;

    private String sentVerificationId, inputOtpCode;
    String phone_number;
    private CollectionReference usersCollectionRef = FirebaseFirestore.getInstance().collection("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        phoneNumberText = findViewById(R.id.farLogActivity_enter_phoneText);
        //      pinNumberText = findViewById(R.id.farLogActivity_enter_pinText);
        //     forgotPinText = findViewById(R.id.farLogActivity_forgotPin);

        loginButton = findViewById(R.id.farLogActivity_loginButton);


        progressBar = findViewById(R.id.progressBarDialog);


        Log.d("calls", "2");

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {





                String country_code = "+88";
                phone_number = phoneNumberText.getText().toString().trim();
                String complete_phone_number = country_code + phone_number;
                if (phone_number.isEmpty()) {


                    Toast.makeText(getApplicationContext(), "Please Fill", Toast.LENGTH_LONG).show();
                } else {
                     progressBar.setVisibility(View.VISIBLE);
                    Log.d("calls", "3");

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            FarmerLoginActivity.this,
                            McallBack
                    );
                }

            }
        });
        //      createAccount.setOnClickListener(this);


        McallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredentials(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(getApplicationContext(), "verification Failed " + e, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                sentVerificationId = s;
//                Log.d("printId",s);
                Log.d("calls", "4");
                //createOTPpopupDialog();
                Intent otpIntent = new Intent(FarmerLoginActivity.this, otpActivity.class);
                otpIntent.putExtra("AuthCredentials", s);
                otpIntent.putExtra("phone", phone_number);
                Log.d("checked", "farmerloginactivity " + phone_number + "  ");
                startActivity(otpIntent);


            }
        };


    }

    private void signInWithPhoneAuthCredentials(PhoneAuthCredential credential) {
        Log.d("calls", "7");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(FarmerLoginActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    numberExistenceCheck();


                                }
                                // mLoginProgress.setVisibility(View.VISIBLE);
                            }
                        }

                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error In verifying OTP", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void registerPage() {
        Log.d("calls", "8");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //dialog.dismiss();
                Intent registerIntent = new Intent(FarmerLoginActivity.this, RegistrationActivity.class);
                registerIntent.putExtra("phone", phoneNumberText.getText().toString());
                registerIntent.putExtra("activity", "Farmer");
                startActivity(registerIntent);
                finish();
            }
        }, 2000);

    }

    private void createOTPpopupDialog() {

        builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.otp_popup, null);

        otpPassText = view.findViewById(R.id.otp_password);
        otpConfirmButton = view.findViewById(R.id.otp_button);

        otpConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = otpPassText.getText().toString().trim();

                if (!otpPassText.getText().toString().isEmpty()) {
                    Log.d("Print", "pass is: " + otpPassText.getText().toString().trim());
                    Log.d("calls", "5");
                    Log.d("PrintId", sentVerificationId);

                    PhoneAuthCredential credential;
                    credential = PhoneAuthProvider.getCredential(sentVerificationId, pass);
                    Log.d("calls", "6");
                    signInWithPhoneAuthCredentials(credential);

                } else {
                    Snackbar.make(v, "ভুল পাসওয়ার্ড! পুনরায় চেষ্টা করুন।", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("calls", "1");

        progressBar.setVisibility(View.INVISIBLE);

        if (mCurrentUser != null) {


        Log.d("checked","i broke");
            sendUserHome();

            db.collection("users").document(mCurrentUser.getUid())
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        CurrentUserApi currentUserApi = CurrentUserApi.getInstance(); //Global Api
                        String name = task.getResult().getString("name");
                        String phoneNumber = task.getResult().getString("logedInPhoneNumber");
                        String userId = task.getResult().getString("userUId");

                        currentUserApi.setName(name);
                        currentUserApi.setPhoneNumber(phoneNumber);
                        currentUserApi.setUserId(userId);

                    } else {
                        Toast.makeText(getApplicationContext(), "Document doesn't exist",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }



    private void sendUserHome() {


        Intent homeIntent = new Intent(FarmerLoginActivity.this, ExploreActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

    private void numberExistenceCheck() {

        Log.d("checked", "start number check " + phone_number);

        usersCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            boolean check = false;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        Log.d("checkedout", Objects.requireNonNull(document.getString("logedInPhoneNumber")) + " " + check);

                        if (Objects.equals(document.getString("logedInPhoneNumber"), phone_number)) {
                            Log.d("checked", "number found");

                            Log.d("checked", Objects.requireNonNull(document.getString("logedInPhoneNumber")));
                            sendUserHome();
                            check = true;
                            break;
                        }
                    }
                    if (!check) {
                        registerPage();
                    }
                } else {
                    registerPage();
                    Log.d("checked", "number not found");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("checked", "not number found");
            }
        });
    }

}
