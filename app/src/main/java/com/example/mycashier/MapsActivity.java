package com.example.mycashier;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mycashier.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Button btnnormal,btnsatellite,btnterrain,btnhybrid,btnnone;
    LocationManager locationManager;
    public static final int REQUEST_LOCATION_PERMISSSION = 1;
    double latitute,longitude;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSSION );
        }
        else if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION_PERMISSSION);

        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    5000,
                    10,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {

                            latitute = location.getLatitude();
                            longitude = location.getLongitude();

                            Geocoder geocoder = new Geocoder(MapsActivity.this);

                            try {
                                List<Address> addressList = geocoder.getFromLocation(latitute,longitude,1);

                                address = addressList.get(0).getAddressLine(0)+","+
                                          addressList.get(0).getLocality()+","+
                                          addressList.get(0).getCountryName();

                                LatLng myCurrentLocation = new LatLng(latitute,longitude);
                                mMap.addMarker(new MarkerOptions().position(myCurrentLocation).title(address));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation,16),
                                        5000,null);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                   5000,
                   10,
                   new LocationListener() {
                       @Override
                       public void onLocationChanged(@NonNull Location location) {

                           latitute = location.getLatitude();
                           longitude = location.getLongitude();

                           Geocoder geocoder1 = new Geocoder(MapsActivity.this);

                           try {
                               List<Address> addressList1 = geocoder1.getFromLocation(latitute,longitude,1);

                               address = addressList1.get(0).getAddressLine(0)+","+
                                         addressList1.get(0).getLocality()+","+
                                         addressList1.get(0).getCountryName()+","+
                                         addressList1.get(0).getCountryCode();

                               LatLng myCurrentLocation1 = new LatLng(latitute,longitude);
                               mMap.addMarker(new MarkerOptions().position(myCurrentLocation1).title(address));
                               mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation1,16),
                                       5000,null);


                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                       }
                   });
        }

        btnnormal = findViewById(R.id.btnNormal);
        btnsatellite = findViewById(R.id.btnSatellite);
        btnterrain = findViewById(R.id.btnTerrain);
        btnhybrid = findViewById(R.id.btnHybrid);
        btnnone = findViewById(R.id.btnNone);

        btnnormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        btnsatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        btnterrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });

        btnhybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        btnnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}