package com.example.testuserlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient client;
    TextView textView1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textview1);
        button = findViewById(R.id.getLocation);
        client = LocationServices.getFusedLocationProviderClient(this);
        requestPermission();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null)
                            textView1.setText("Lattitude: "+ location.getLatitude()+"\n"+
                                 "Longitude"+   location.getLongitude());
                        /***Using Geocoder getting the physical address of Lat Long**/
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (listAddresses != null && listAddresses.size() > 0) {

                                Log.i("PlaceInfo", listAddresses.get(0).toString());

                                String address = "";

                                if (listAddresses.get(0).getSubThoroughfare() != null) {

                                    address += listAddresses.get(0).getSubThoroughfare() + " ";

                                }

                                if (listAddresses.get(0).getThoroughfare() != null) {

                                    address += listAddresses.get(0).getThoroughfare() + ", ";

                                }

                                if (listAddresses.get(0).getLocality() != null) {

                                    address += listAddresses.get(0).getLocality() + ", ";

                                }

                                if (listAddresses.get(0).getPostalCode() != null) {

                                    address += listAddresses.get(0).getPostalCode() + ", ";

                                }

                                if (listAddresses.get(0).getCountryName() != null) {

                                    address += listAddresses.get(0).getCountryName();

                                }

                                Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
                               }
                            } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ACCESSIBILITY_SERVICE},1);
    }
}