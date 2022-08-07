package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.Manifest;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Double latitude, longitude;
    private static final String TAG ="Latihan";
    private static final int ANGKA_IZIN = 2;
    Button BtLokasi,BtMap;
    EditText txtlongitude,txtlatitude;
    TextView Tview1,Tview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestLokasi();
        txtlatitude =(EditText) findViewById(R.id.TxtLatitude);
        txtlongitude =(EditText) findViewById(R.id.TxtLongitude);
        Tview1 = (TextView) findViewById(R.id.view1);
        Tview2 = (TextView) findViewById(R.id.view2);
        BtLokasi =(Button) findViewById(R.id.BtnLokasi);
        BtMap = (Button) findViewById(R.id.BtnMap);
        BtLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilloc();
            }
        });
        BtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q=" + (latitude.toString()) + "," + (longitude.toString())));
                startActivity(intent);
            }
        });
    }

    private void RequestLokasi(){
        Log.w(TAG, "Izin Lokasi Dibutuhkan");
        final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if(!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(this, permissions, ANGKA_IZIN);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = (view) -> {
            ActivityCompat.requestPermissions(thisActivity, permissions, ANGKA_IZIN);
        };
    }


    private void ambilloc(){
        try {
            RequestLokasi();
            FindLocation gps = new FindLocation(MainActivity.this);
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            txtlatitude.setText(latitude.toString());
            txtlongitude.setText(longitude.toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}