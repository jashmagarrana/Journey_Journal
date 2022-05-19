package com.jashrana.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.journeyjournal.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapActivity extends AppCompatActivity {
    SupportMapFragment smf;
    FusedLocationProviderClient client;
    private GoogleMap nMap;
    PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
   @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

   @Override
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

     Task<Location> task = client.getLastLocation();
    task.addOnSuccessListener(new OnSuccessListener<Location>() {
    @Override
   public void onSuccess(final Location location) {
         smf.getMapAsync(new OnMapReadyCallback() {

   @Override
   public void onMapReady(GoogleMap googleMap) {
       nMap = googleMap;
       polylineOptions = new PolylineOptions();


        LatLng nepal = new LatLng(27.6984, 85.2994);
        googleMap.addMarker(new MarkerOptions().position(nepal).title("Nepal"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nepal,17));
        nMap.getUiSettings().setZoomControlsEnabled(true);

        nMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                nMap.clear();
                nMap.addMarker(new MarkerOptions().position(latLng).title("new position lat:"+latLng.latitude+" Lng:"+latLng.longitude));
                nMap.addCircle(new CircleOptions().center(latLng).radius(400).strokeColor((Color.RED)));
                polylineOptions.add(latLng);
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                nMap.addPolyline(polylineOptions);

           }
       });
                    }
                });
            }
        });
    }
}