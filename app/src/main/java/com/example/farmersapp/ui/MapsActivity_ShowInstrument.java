package com.example.farmersapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.farmersapp.R;
import com.example.farmersapp.model.Instrument;
import com.example.farmersapp.util.CurrentUserApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MapsActivity_ShowInstrument extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private static final int ACCESS_LOCATION_REQ_CODE = 10001;
    private static final float DEFAULT_ZOOM = 15;
    private String[] INSTRUMENT_TYPE_LIST = {"ধান রোপণ যন্ত্র", "ট্র্যাক্টর", "হাল চাষের মেশিন"}; //Strings must be in order
    //widgets
    private Button showCategoryButton;
    private AlertDialog dialogForDetails; //dialog for instrument details

    //variables
    private GoogleMap mMap;
    private Geocoder geocoder;
    private View mapView;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Instrument_Owners");
    private ArrayList<Instrument> instrumentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_show_instrument);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapView = mapFragment.getView();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        getInstrumentList();
        showInstrumentTypeSelectorDialog();

        //button
        showCategoryButton = findViewById(R.id.showCategoryButton);
        showCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInstrumentTypeSelectorDialog();
            }
        });
    }

    private void showInstrumentTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = getString(R.string.select_category);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fDialogTitle);

        int checkItem = -1;
        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                INSTRUMENT_TYPE_LIST,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 0:
                                showCategoryButton.setText(INSTRUMENT_TYPE_LIST[item]);
                                mMap.clear();
                                showInstrumentList("ধান রোপণ যন্ত্র");
                                break;
                            case 1:
                                showCategoryButton.setText(INSTRUMENT_TYPE_LIST[item]);
                                mMap.clear();
                                showInstrumentList("ট্র্যাক্টর");

                                break;
                            case 2:
                                showCategoryButton.setText(INSTRUMENT_TYPE_LIST[item]);
                                mMap.clear();
                                showInstrumentList("হাল চাষের মেশিন");
                                break;
                        }
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showInstrumentList(String type) {
        for(Instrument instrument : instrumentList) {
            if(instrument.getInstrumentType().equals(type)) {
                LatLng latLng = new LatLng(instrument.getInstrumentGeoPoint().getLatitude(),
                        instrument.getInstrumentGeoPoint().getLongitude());
                Log.d("Test","Using LatLng___"+latLng.latitude+"  "+latLng.longitude);
                Log.d("Test",instrument.getInstrumentAddress());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
                markerOptions.title(instrument.getInstrumentType());
                markerOptions.snippet(instrument.getInstrumentPrice());
                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(instrument);
//                marker.setID(instrument.getInstrumentImageUrl());
            }
        }
    }

    private void getInstrumentList() {
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    Log.e("Test","onEvent: Listen failed",e);
                }
                if(queryDocumentSnapshots != null) {
                    //Clear the list and add all the instruments again
                    instrumentList.clear();
                    instrumentList = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Instrument instrument = doc.toObject(Instrument.class);
                        instrumentList.add(instrument);
                    }
                    Log.d("Test", "onEvent: instrument list size: "+instrumentList.size());
                }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new MarkerInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            enableUserLocation();
            zoomToUserLocation();

            //Creating MyLocation Button
            if (mapView != null &&
                    mapView.findViewById(Integer.parseInt("1")) != null) {
                // Get the button view
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                // and next place it, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        locationButton.getLayoutParams();
                // position on right top
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.setMargins(0, 30, 30, 0);
            }

        }else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}
                        , ACCESS_LOCATION_REQ_CODE);
            }else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}
                        , ACCESS_LOCATION_REQ_CODE);
            }
        }
    }

    private void zoomToUserLocation() {
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d("Test","From zoomToUserLocation>>>> LATLNG:" +location.getLatitude()+"   "+location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
            }
        });
    }

    private void enableUserLocation() {
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ACCESS_LOCATION_REQ_CODE) {
            if(grantResults.length >0 &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
                zoomToUserLocation();
            }else {

            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Instrument instrument = (Instrument)marker.getTag();
        getMoreDetails(instrument);
    }

    private void getMoreDetails(final Instrument instrument) {
        ImageView showImageView;
        Button dismissButton, contactOwnerButton;
        TextView showTypeTextView, showPriceTextView, showNameTextView, showNumberTextView, showAddrssTextView;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.pop_window_instrument_details,null);

        dismissButton = view.findViewById(R.id.dismissPop);
        showImageView = view.findViewById(R.id.showImageView);
        showTypeTextView = view.findViewById(R.id.showTypeTextView);
        showPriceTextView = view.findViewById(R.id.showPriceTextView);
        showNameTextView = view.findViewById(R.id.showNameTextView);
        showNumberTextView = view.findViewById(R.id.showNumberTextView);
        showAddrssTextView = view.findViewById(R.id.showAddrssTextView);
        contactOwnerButton = view.findViewById(R.id.contactOwnerButton);

        StringBuilder price = new StringBuilder(instrument.getInstrumentPrice());

        Picasso.get().load(instrument.getInstrumentImageUrl()).fit().into(showImageView);
        showTypeTextView.setText(instrument.getInstrumentType());
        showPriceTextView.setText(price.append(" টাকা"));
        showNameTextView.setText(instrument.getOwnerName());
        showNumberTextView.setText(instrument.getOwnerPhone());
        showAddrssTextView.setText(instrument.getInstrumentAddress());

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForDetails.dismiss();
            }
        });

        contactOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phone intent
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + Uri.encode(instrument.getOwnerPhone())));
                startActivity(dialIntent);
            }
        });

        dialogBuilder.setView(view);
        dialogForDetails = dialogBuilder.create();
        dialogForDetails.setCanceledOnTouchOutside(false);
        dialogForDetails.show();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        marker.showInfoWindow();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                marker.showInfoWindow();

            }
        }, 10);

        return true;
    }

}
