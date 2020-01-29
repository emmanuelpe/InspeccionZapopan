package com.perspective.inszap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Licencias extends AppCompatActivity {

    private TextView tvFechaR,tvFechaC,totalLR,totalLC;
    private GestionBD gestionarBD;
    private SQLiteDatabase db;
    private Cursor c;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licencias);

        tvFechaC = findViewById(R.id.tvFechaC);
        tvFechaR = findViewById(R.id.tvFechaR);
        totalLR = findViewById(R.id.totalLR);
        totalLC = findViewById(R.id.totalLC);

        gestionarBD = new GestionBD(this, "inspeccion", null, 1);
        db = gestionarBD.getReadableDatabase();
        c = db.rawQuery("SELECT * FROM v_LicenciasReglamentos", null);
        Log.i("to", c.getCount() + "");
        totalLR.setText("Total de licencias reglamentos: " + c.getCount());
        c = db.rawQuery("SELECT * FROM vs_InspM2", null);
        Log.i("total", c.getCount() + "");
        totalLC.setText("Total de licencias construcción: " + c.getCount());
        c = db.rawQuery("SELECT fechaA FROM vs_InspM2", null);
        if (c.moveToFirst()) {
            do {
                fecha=c.getString(c.getColumnIndex("fechaA"));
            } while (c.moveToNext());
        }
        tvFechaC.setText("Fecha actualización: " + fecha);
        c = db.rawQuery("SELECT fechaA FROM v_LicenciasReglamentos", null);
        if (c.moveToFirst()) {
            do {
                fecha=c.getString(c.getColumnIndex("fechaA"));
            } while (c.moveToNext());
        }
        tvFechaR.setText("Fecha actualización: " + fecha);

    }
}
