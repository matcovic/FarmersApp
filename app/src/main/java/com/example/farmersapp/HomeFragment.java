package com.example.farmersapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;


public class HomeFragment extends Fragment implements LocationListener {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  //variables
  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationRequest locationRequest;
  public static final long UPDATE_INTERVAL = 1;
  public static final long FASTEST_INTERVAL = 60000;
  private String LAT, LON;

  //widgets
  private Button learnMore;
  private ImageView click_cultivation, click_microloan, click_poultry, click_disease, click_information, click_suggestion;
  RecyclerView recyclerView_stories;

  //widgets for weather
  private TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
          sunsetTxt, windTxt, pressureTxt, humidityTxt, errorText;
  private ImageView weatherIcon;
  private ProgressBar loader;
  private CardView mainContainer;
  private LinearLayout weather_Layout1, weather_Layout2;

  FragmentManager fragmentManager;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public HomeFragment() {
    // Required empty public constructor
  }

  public static HomeFragment newInstance(String param1, String param2) {
    HomeFragment fragment = new HomeFragment();
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
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
    if (ActivityCompat.checkSelfPermission(getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      getLastLocation();
    }
  }

  private void getLastLocation() {
    fusedLocationProviderClient.getLastLocation()
            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
              @Override
              public void onSuccess(Location location) {
                //Get last known location. But it could be null
                if (location != null) {
//                            locationTextview.setText(MessageFormat.format("Lat: {0} Lon: {1}",
//                                    location.getLatitude(), location.getLongitude()));

                  LAT = String.valueOf(location.getLatitude());
                  LON = String.valueOf(location.getLongitude());
                  new weatherTask().execute();

                  Log.d("Test","FROM onSuccess......LAT: " +LAT+ "  LON: " +LON);
                }

              }
            });

    startLocationUpdates();
  }



  private void startLocationUpdates() {
    locationRequest = new LocationRequest();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(UPDATE_INTERVAL);
    locationRequest.setFastestInterval(FASTEST_INTERVAL);

    if (ActivityCompat.checkSelfPermission(getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
      Toast.makeText(getActivity(),
              "You need to enable permission to display location!",
              Toast.LENGTH_LONG)
              .show();
    }

    LocationServices.getFusedLocationProviderClient(getActivity())
            .requestLocationUpdates(locationRequest, new LocationCallback(){
              @Override
              public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null) {
                  Location location = locationResult.getLastLocation();
//                            locationTextview.setText(MessageFormat.format("Lat: {0} Lon: {1}",
//                                    location.getLatitude(), location.getLongitude()));
                  Log.d("Test","FROM onLocationResult......LAT: " +location.getLatitude()+ "  LON: " +location.getLongitude());
                }
              }

              @Override
              public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
              }
            }, null);


  }

  @Override
  public void onLocationChanged(Location location) {
    if (location != null) {
//            locationTextview.setText(MessageFormat.format("Changed - Lat: {0} Changed - Lon: {1}",
//                    location.getLatitude(), location.getLongitude()));
      Log.d("Test","FROM onLocationChanged......LAT: " +location.getLatitude()+ "  LON: " +location.getLongitude());
    }
  }


  /***
   * Weather Code
   */

   public class weatherTask extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();

      /* Showing the ProgressBar, Making the main design GONE */
      mainContainer.setVisibility(View.VISIBLE);
      loader.setVisibility(View.VISIBLE);
      errorText.setVisibility(View.GONE);
      weather_Layout1.setVisibility(View.GONE);
      weather_Layout2.setVisibility(View.GONE);
    }

    protected String doInBackground(String... args) {
      String API = "d187f6d95d8d2bdd24ea02ff5274fe08";
      String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" +LAT+ "&lon=" +LON+ "&appid=" + API);
      return response;
    }

    @Override
    protected void onPostExecute(String result) {


      try {
        JSONObject jsonObj = new JSONObject(result);
        JSONObject main = jsonObj.getJSONObject("main");
        JSONObject sys = jsonObj.getJSONObject("sys");
        JSONObject wind = jsonObj.getJSONObject("wind");
        JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

        Long updatedAt = jsonObj.getLong("dt");
        String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
        String temp = digitConversionEngToBangla(conversionOfTemp(main.getString("temp"))) + "°";
        String tempMin = digitConversionEngToBangla(conversionOfTemp(main.getString("temp_min"))) + "°";
        String tempMax = digitConversionEngToBangla(conversionOfTemp(main.getString("temp_max"))) + " / ";
        String pressure = main.getString("pressure");
        String humidity = main.getString("humidity");

        Long sunrise = sys.getLong("sunrise");
        Long sunset = sys.getLong("sunset");
        String windSpeed = wind.getString("speed");
        String weatherDescription = weather.getString("description");

        String statusIcon = weather.getString("icon");
        Log.d("test:", statusIcon);
        String iconUrl = "https://openweathermap.org/img/wn/" +statusIcon+ "@2x.png";
        Log.d("test:", iconUrl);

        String address = jsonObj.getString("name") + ", " + sys.getString("country");

        /* Populating extracted data into our views */
        addressTxt.setText(address);
        updated_atTxt.setText(updatedAtText);
        statusTxt.setText(weatherDescription.toUpperCase());
        tempTxt.setText(temp);
        temp_minTxt.setText(tempMin);
        temp_maxTxt.setText(tempMax);
        sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
        sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
        windTxt.setText(windSpeed);
        pressureTxt.setText(pressure);
        humidityTxt.setText(humidity);

        /***
         * Load Image
         */
        Picasso.get().load(iconUrl).resize(200,200).into(weatherIcon);

        /* Views populated, Hiding the loader, Showing the main design */
       loader.setVisibility(View.GONE);
        mainContainer.setVisibility(View.VISIBLE);
       weather_Layout1.setVisibility(View.VISIBLE);
        weather_Layout2.setVisibility(View.VISIBLE);


      } catch (JSONException e) {
        loader.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
      }
    }

    private int conversionOfTemp(String temp_kelvin) {
      double conversionDouble = Double.parseDouble(temp_kelvin) - 273.15;
      return (int)Math.ceil(conversionDouble);
    }
  }



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View convertView = inflater.inflate(R.layout.fragment_home, container, false);

    //Invoking widgets for weather Starts
    addressTxt = convertView.findViewById(R.id.address);
    updated_atTxt = convertView.findViewById(R.id.updated_at);
    statusTxt = convertView.findViewById(R.id.status);
    tempTxt = convertView.findViewById(R.id.temp);
    temp_minTxt = convertView.findViewById(R.id.temp_min);
    temp_maxTxt = convertView.findViewById(R.id.temp_max);
    sunriseTxt = convertView.findViewById(R.id.sunrise);
    sunsetTxt = convertView.findViewById(R.id.sunset);
    windTxt = convertView.findViewById(R.id.wind);
    pressureTxt = convertView.findViewById(R.id.pressure);
    humidityTxt = convertView.findViewById(R.id.humidity);
    weatherIcon = convertView.findViewById(R.id.weatherIcon);
    errorText = convertView.findViewById(R.id.errorText);
    loader = convertView.findViewById(R.id.loader);
    mainContainer = convertView.findViewById(R.id.mainContainer);
    weather_Layout1 = convertView.findViewById(R.id.weather_Layout1);
    weather_Layout2 = convertView.findViewById(R.id.weather_Layout2);
    //Invoking widgets for weather Ends

    click_cultivation = convertView.findViewById(R.id.click_cultivation);
    click_microloan = convertView.findViewById(R.id.click_microloan);
    click_disease = convertView.findViewById(R.id.click_disease);
    click_poultry = convertView.findViewById(R.id.click_poultry);
    click_suggestion = convertView.findViewById(R.id.click_suggestion);
    click_information = convertView.findViewById(R.id.click_information);
    learnMore = convertView.findViewById(R.id.learnMore);
    recyclerView_stories = convertView.findViewById(R.id.recyclerView_stories);

//    recyclerView_stories.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false ));


    click_information.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);
      }
    });

    click_microloan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);

      }
    });

    click_disease.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);
      }
    });

    click_poultry.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);

      }
    });

    click_cultivation.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            Fragment fragment = new CultivationFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_right,R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
          }
        },300);
      }
    });

    click_suggestion.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickAnimation(v);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        },300);
      }
    });

    learnMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      }
    });

    return convertView;
  }

  private void clickAnimation(View v) {
    Animation animShake = AnimationUtils.loadAnimation(getActivity(),R.anim.shake_button);
    v.startAnimation(animShake);
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
