package com.example.bumpin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.bumpin.AccountService.ApiService;
import com.example.bumpin.AccountService.json_pk;
import com.example.bumpin.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public Geocoder geocoder;
    private ActivityMapsBinding binding;
    private Boolean btnClicked = false;
    private String tripName;
    private int tripNumber = 0;
    private ArrayList<MarkerOptions> trip = new ArrayList<>();
    public HashMap<String, ArrayList<MarkerOptions> > map = new HashMap<String, ArrayList<MarkerOptions> >();
    public ArrayList<String> selectedItems = new ArrayList<>();
    private String str_id;
    // Retrofit
    private Retrofit retrofit;
    private ApiService apiService;
    private Call<json_pk> int_call;
    private Call body_call;
    private JSONObject jsonObject;
    private int count;

    private String tripString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        str_id = getIntent().getStringExtra("id");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        //total map //////////////////////////////////////////////////////////////////////

        retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        body_call = apiService.total_Path(str_id);

        body_call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("trip retrieve","fucl");
                if(response.isSuccessful()){

                    Log.e("trip retrieve","done");
                    try {

                        Log.e("trip retrieve","done");
                        jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        int count =0;
                        try {
                            count = Integer.parseInt((String) jsonObject.get("0"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (jsonObject != null){
                            try {
                                count = Integer.parseInt((String) jsonObject.get("0"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject tmpObject;
                            for (int i = 1; i <= count; i ++){
                                try {
                                    String index = String.valueOf(i);
                                    tmpObject = (JSONObject) jsonObject.get(index);
                                    String data =tmpObject.get("data").toString();  //error
                                    String tN = tmpObject.get("tripName").toString();
                                    ArrayList<MarkerOptions> trip = stringToLoc( data, tN);
                                    map.put(tN, trip);
                                    selectedItems.add(tN);
                                    Log.e("trip retrieve","done");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        // traverse jsonObject and only pass string to stringToLoc
                        // count the number of key value in object

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("trip get success", " ");

                }
                else{
                    Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                            Toast.LENGTH_LONG).show();
                    Log.e("trip get",  String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("trip get", t.getMessage());
            }
        });
        //total map //////////////////////////////////////////////////////////////////////
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(MapsActivity.this);



        Button newBtn = findViewById(R.id.newBtn);
        Button listBtn = findViewById(R.id.listBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        Button friendBtn = findViewById(R.id.friendBtn);

        trip = new ArrayList<MarkerOptions>();
        PolylineOptions polylineOptions;

        showMarker(selectedItems);

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                trip = new ArrayList<MarkerOptions>();
                if(btnClicked == true){
                    btnClicked = false;
                    newBtn.setText("Add");
//                    if(trip.size() > 0){
//                        map.put(tripName, trip);
//                    }
                    tripString = locToString(trip);

                    //Trip added//////////////////////////////////////////////////////////////////////
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    apiService = retrofit.create(ApiService.class);
                    int_call = apiService.add_Path( str_id, tripName, tripString, tripNumber);

                    //int_call = apiService.add_Path( "uN1", "tN1", "tS1", 1);
                    int_call.enqueue(new Callback<json_pk>() {
                        @Override
                        public void onResponse(Call<json_pk> call, Response<json_pk> response) {
                            if(response.isSuccessful()){
                                String pk =  response.body().toString();

                                Log.e("trip post success",  pk);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "error= "+ String.valueOf(response.code()),
                                        Toast.LENGTH_LONG).show();
                                Log.e("trip post",  String.valueOf(response.code()));
                            }
                        }

                        @Override
                        public void onFailure(Call<json_pk> call, Throwable t) {
                            Log.e("trip post", t.getMessage());
                        }
                    });
                    //////////////////////////////////////////////////////////////////////////////////

                    // Change state variable
                    btnClicked = false;
                    newBtn.setText("Add");
                    if(trip.size() > 0){
                        map.put(tripName, trip);
                    }
                    tripNumber = 0;
                    return;
                }
                trip = new ArrayList<MarkerOptions>();
                btnClicked = true;
                newBtn.setText("Stop");

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                View v = getLayoutInflater().inflate(R.layout.edit_box, null, false);
                builder.setView(v);
                AlertDialog dialog = builder.create();

                Button addTripBtn = (Button) v.findViewById(R.id.addTripBtn);
                EditText editTextTripName = (EditText) v.findViewById(R.id.editTextTripName);



                addTripBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tripName = editTextTripName.getText().toString();
                        dialog.dismiss();
                        if(tripName != null) {
                            map.put(tripName, trip);
                            selectedItems.add(tripName);
                        }
                        else{
                            return;
                        }
                    }
                });
                dialog.show();

                PolylineOptions polylineOptions = new PolylineOptions();
                // add pin point
                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        if(btnClicked == false) {
                            return;
                        }
                        else{
                            tripNumber++;
                            try {
                                List<Address> myList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                                Address address = (Address) myList.get(0);
                                String addressStr = "";
                                addressStr += address.getAddressLine(0);
                                MarkerOptions mo = new MarkerOptions()
                                        .position(latLng)
                                        .draggable(true)
                                        .title(tripName + " " + tripNumber + "번째 장소")
                                        .snippet(addressStr);
                                mMap.addMarker(mo);
                                polylineOptions.add(latLng);
                                mMap.addPolyline(polylineOptions);
                                trip.add(mo);

                            }catch (IOException e){
                                System.out.println(e.toString());
                                MarkerOptions mo = new MarkerOptions()
                                        .position(latLng)
                                        .draggable(true)
                                        .title(tripName + " " + tripNumber + "번째 장소")
                                        .snippet("No address info");
                                mMap.addMarker(mo);
                                polylineOptions.add(latLng);
                                mMap.addPolyline(polylineOptions);
                                trip.add(mo);
                            }
                        }
                    }
                });
            }

        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map.size() == 0) return;

                final String[] items = map.keySet().toArray(new String[map.size()]);
//                final List<String> selectedItems = new ArrayList<>();
                selectedItems = new ArrayList<>();

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Select Trip");
                builder.setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                        selectedItems.add(items[which]);
                                }
                            }
                        });

                builder.setCancelable(false);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        showMarker(selectedItems);

                        Log.e("valid", "shoould not be here");

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // handle the neutral button of the dialog to clear
                // the selected items boolean checkedItem
                builder.setNeutralButton("Select ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < items.length; i++) {
                            if(selectedItems.contains(items[i])){
                                continue;
                            }
                            else{
                                selectedItems.add(items[i]);
                            }
                        }
//                        mMap.clear();
                        showMarker(selectedItems);
                    }
                });
                builder.show();
//                builder.create();
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map.size() == 0) return;
//                Toast.makeText(this,map.size() + "" , Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), map.size()+"", Toast.LENGTH_SHORT).show();

                final String[] items = map.keySet().toArray(new String[map.size()]);
//                final List<String> selectedItems = new ArrayList<>();
                final List<String> deletedItems = new ArrayList<>();

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Delete Trip");
                builder.setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    deletedItems.add(items[which]);
                                    if(selectedItems.contains(items[which])) {
                                        selectedItems.remove(items[which]);
//                                        map.remove(items[which]);
                                    }
                                }
                            }
                        });

                builder.setCancelable(false);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                        for(String s: deletedItems){
                            map.remove(s);
                            // delete each trip name///////////////////////////////////////////////////
                            retrofit = new Retrofit
                                    .Builder()
                                    .baseUrl(ApiService.API_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            apiService = retrofit.create(ApiService.class);
                            int_call = apiService.delete_Path(str_id, s);
                            //                              Log.e("path remove", "suceess");
                            int_call.enqueue(new Callback<json_pk>(){
                                                 @Override
                                                 public void onResponse(Call<json_pk> call, Response<json_pk> response) {
                                                     try {
                                                         Log.e("path remove", "suceess");

                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(Call<json_pk> call, Throwable t) {
                                                     Log.e("path remove", "페일!");
                                                 }
                                             }

                            );
                            ////////////////////////////////////////////////////////////////////////////
                        }

                        //
//                        mMap.clear();
                        showMarker(selectedItems);
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // handle the neutral button of the dialog to clear
                // the selected items boolean checkedItem
                builder.show();
            }
        });

//        showMarker(selectedItems);


    }

    public void showMarker(ArrayList<String> keyList){
        mMap.clear();
        for(String s: keyList){
            if(map.containsKey(s)){
                ArrayList<MarkerOptions> markerOptions = map.get(s);
                PolylineOptions polylineOptions = new PolylineOptions();
                for(MarkerOptions mo: markerOptions){
                    mMap.addMarker(mo);
                    polylineOptions.add(mo.getPosition());
                    mMap.addPolyline(polylineOptions);
                }
            }
            else{
                continue;
            }
        }
    }

//    public String locToString(List<MarkerOptions> trip){
//        JSONObject data = new JSONObject();
//        for(MarkerOptions mo: trip){
//            LatLng latLng = mo.getPosition()
//            data.put(Double.toString(latLng.latitude), str(latLng.longitude));
//        }
//////        return data.toJSONString();
////        Gson gson = new Gson();
////        JsonObject jsonObject = new JsonObject();
////        for(MarkerOptions mo: trip){
////            LatLng latLng = mo.getPosition();
////            jsonObject.addProperty(Double.toString(latLng.latitude), Double.toString(latLng.longitude));
////        }
////        return gson.toJson(jsonObject);
//    }


    public String locToString(List<MarkerOptions> trip){
        String ans = "";
        for(MarkerOptions mo: trip){
            LatLng latLng = mo.getPosition();
            ans += ((Double.toString(latLng.latitude) + "a" + Double.toString(latLng.longitude)) + "b");
        }

        Log.e("loctostring", ans);
        return ans;
    }

    public ArrayList<MarkerOptions> stringToLoc(@NonNull String str, String tripName) {
        ArrayList<MarkerOptions> arr = new ArrayList<>();
        String[] pairArr = str.split("b");
        int i = 1;
        for(String s : pairArr) {
            String[] singlePair = s.split("a");
            Double d1 = Double.parseDouble(singlePair[0]);
            Double d2 = Double.parseDouble(singlePair[1]);

            try {
                List<Address>myList = geocoder.getFromLocation(d1, d2, 1);
                Address address = (Address) myList.get(0);
                String addressStr = "";
                addressStr += address.getAddressLine(0);

                LatLng latLng = new LatLng(d1, d2);
                Log.e("print latLng", d1.toString());
                MarkerOptions mo = new MarkerOptions()
                        .position(latLng)
                        .draggable(true)
                        .title(tripName + " " + i + "번째 장소")
                        .snippet(addressStr);
                arr.add(mo);
            }catch (IOException e) {
                LatLng latLng = new LatLng(d1, d2);
                MarkerOptions mo = new MarkerOptions()
                        .position(latLng)
                        .draggable(true)
                        .title(tripName + " " + i + "번째 장소")
                        .snippet("No address info");
                arr.add(mo);
            }
            i++;
        }
        return arr;
    }


//    public List<MarkerOptions> stringToLoc(String str) {
//        ArrayList<MarkerOptions> arr = new ArrayList<>();
//        Gson gson = new Gson();
//        JsonElement jsonElement = gson.fromJson(str, JsonElement.class);
//        JsonObject jsonObject = jsonElement.getAsJsonObject();
//        Set<String> set = jsonObject.keySet();
//        for(String key: set){
//            Double latitude = Double.parseDouble(key);
//            Double longitude = Double.parseDouble(jsonObject.get(key));
//
//        }
//
    public void removeMarker(String[] keyList){
//        for(String s: keyList){
//            if(map.containsKey(s)){
//                ArrayList<MarkerOptions> markerOptions = map.get(s);
//                for(MarkerOptions mo: markerOptions){
//                    mo.visible(false);
//                }
//            }
//            else{
//                continue;
//            }
//        }
        mMap.clear();
    }

    public void removeMarker(MarkerOptions[] markerOptions){
//        for(MarkerOptions mo: markerOptions){
//            mo.visible(false);
//        }
    }
}