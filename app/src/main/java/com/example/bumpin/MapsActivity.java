package com.example.bumpin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.bumpin.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Boolean btnClicked = false;
    private String tripName;
    public HashMap<String, ArrayList<MarkerOptions> > map = new HashMap<String, ArrayList<MarkerOptions> >();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        Marker marker;
//        String id;
//        WeakHashMap<Marker, String> mMarkers = new WeakHashMap<Marker, String>();
//        mMarkers.put(marker, id);
//
//        mMap.setOnMarkerClickListener(
//            new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(@NonNull Marker marker) {
//                    return false;
//                }
//            }
//        );

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                mMap.clear();
//
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                mMap.addMarker(markerOptions);
//            }
//        });

        Button newBtn = findViewById(R.id.newBtn);
        Button listBtn = findViewById(R.id.listBtn);
        Button friendBtn = findViewById(R.id.friendBtn);

        ArrayList<MarkerOptions> trip;
        PolylineOptions polylineOptions;

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnClicked == true){
                    btnClicked = false;
                    newBtn.setText("Add");
                    return;
                }
                btnClicked = true;
                newBtn.setText("Stop");

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                View v = getLayoutInflater().inflate(R.layout.edit_box, null, false);
                builder.setView(v);
                AlertDialog dialog = builder.create();

                Button addTripBtn = (Button) v.findViewById(R.id.addTripBtn);
                EditText editTextTripName = (EditText) v.findViewById(R.id.editTextTripName);

                ArrayList<MarkerOptions> trip = new ArrayList<MarkerOptions>();

                addTripBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tripName = editTextTripName.getText().toString();
                        dialog.dismiss();
                        if(tripName != null) {
                            map.put(tripName, trip);
                        }
                        else{
                            return;
                        }
                    }
                });
                dialog.show();

//                ArrayList<MarkerOptions> trip = new ArrayList<MarkerOptions>();

//                if(tripName.length() == 0){
//                    Toast.makeText(getApplicationContext(), "Empty Text!!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                map.put(tripName, trip);
                PolylineOptions polylineOptions = new PolylineOptions();

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        if(btnClicked == false) {
                            return;
                        }
                        else{
                            MarkerOptions mo = new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .title("Your marker title")
                                    .snippet("Your marker snippet");
                            mMap.addMarker(mo);
                            polylineOptions.add(latLng);
                            mMap.addPolyline(polylineOptions);
                            trip.add(mo);
                        }
                    }
                });
            }

        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final List<String> tripList = Arrays.asList(map.keySet().toArray(new String[map.size()]));

                if(map.size() == 0) return;

//                Toast.makeText(this,map.size() + "" , Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), map.size()+"", Toast.LENGTH_SHORT).show();

                final String[] items = map.keySet().toArray(new String[map.size()]);
                final List<String> selectedItems = new ArrayList<>();

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("Select Trip");
                builder.setMultiChoiceItems(items, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    selectedItems.add(items[which]);
                                }else if(selectedItems.contains(items[which])){
                                    selectedItems.remove(items[which]);
                                }
                            }
                        });

                builder.setCancelable(false);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String msg = "";
                        for (int i = 0; i < selectedItems.size(); i++) {
                            msg += "\n" + (i+1) + ":" + selectedItems.get(i) ;
                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        mMap.clear();
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
                        mMap.clear();
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

    }

    public void showMarker(List<String> keyList){

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