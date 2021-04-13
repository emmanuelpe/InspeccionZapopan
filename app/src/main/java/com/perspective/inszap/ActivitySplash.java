package com.perspective.inszap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class ActivitySplash extends AppCompatActivity {
    private static int PERMISSION_REQUEST_CODE = 1234;
    String[] appPermissions = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkAndRequestPermissions();
        Context context = this.getApplicationContext();


        SharedPreferences _prefs = getSharedPreferences("datos",Context.MODE_PRIVATE);
        int valorPassPreferences = _prefs.getInt("id", 0);
        String valorPassPreferences2 = _prefs.getString("usuario", "0");
        String direccion=_prefs.getString("direccion","0");

        //sharedPreferences = null;

        if(valorPassPreferences==0){
            Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(ActivitySplash.this,Descarga.class);
            //Intent intent = new Intent(MainActivity.this,TestActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("direccion", direccion);
            bundle.putString("usuario", valorPassPreferences2);
            bundle.putInt("id", valorPassPreferences);
            finish();
        }

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        //onDestroy();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    public boolean checkAndRequestPermissions(){
        List<String> ListPermissionsNeeded = new ArrayList<>();
        for(String perm : appPermissions){
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED)
            {
                ListPermissionsNeeded.add(perm);

            }
        }
        if(!ListPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,ListPermissionsNeeded.toArray(new String[ListPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }
}
