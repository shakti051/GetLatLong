package com.example.testuserlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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
                    }
                });
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ACCESSIBILITY_SERVICE},1);
    }
}