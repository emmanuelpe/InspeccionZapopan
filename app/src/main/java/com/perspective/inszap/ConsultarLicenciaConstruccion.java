package com.perspective.inszap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ConsultarLicenciaConstruccion extends AppCompatActivity implements View.OnClickListener {

    private EditText etIdLicencia,etNombre,etCalle,etNumero,etFraccionamiento;
    private Button btnBuscar;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private List<String> datos = new ArrayList<>();
    private GestionBD gestionarBD = null;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_licencia_construccion);

        etIdLicencia = findViewById(R.id.etIdLicencia);
        etNombre = findViewById(R.id.etNombre);
        etCalle = findViewById(R.id.etCalle);
        etNumero = findViewById(R.id.etNumero);
        etFraccionamiento = findViewById(R.id.etFraccionamiento);
        btnBuscar = findViewById(R.id.btnBuscar);
        lv = findViewById(R.id.lv);
        btnBuscar.setOnClickListener(this);
        adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.simple_list_item,datos);
        lv.setAdapter(adapter);
        gestionarBD = new GestionBD(this,"inspeccion",null,1);
        db = gestionarBD.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM vs_InspM2", null);
        Log.i("que", "SELECT * FROM vs_InspM2");

        Log.v("count",c.getCount() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscar:
                if(validarCampos() < 5)
                    new BuscarL().execute(etIdLicencia.getText().toString(),etNombre.getText().toString(),etCalle.getText().toString(),etNumero.getText().toString(),etFraccionamiento.getText().toString());
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar por lo menos un filtro de busqueda",Toast.LENGTH_LONG);
                    toast.setGravity(0,0,15);
                    toast.show();
                }
                break;
        }
    }

    public int validarCampos() {
        int count = 0;
        if(etIdLicencia.getText().toString().trim().equalsIgnoreCase(""))
            count++;
        if(etNombre.getText().toString().trim().equalsIgnoreCase(""))
            count++;
        if(etCalle.getText().toString().trim().equalsIgnoreCase(""))
            count++;
        if(etNumero.getText().toString().trim().equalsIgnoreCase(""))
            count++;
        if(etFraccionamiento.getText().toString().trim().equalsIgnoreCase(""))
            count++;
        return count;
    }

    public class BuscarL extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            buscarL(strings);
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(datos.isEmpty()) {
                Toast toast = Toast.makeText(ConsultarLicenciaConstruccion.this, "No se encontro licencia", Toast.LENGTH_SHORT);
                toast.setGravity(0,0,15);
                toast.show();
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void buscarL(String... strings) {
        String condicion = "";
        if(!strings[0].equalsIgnoreCase(""))
            condicion += " and IdLicencia like'%"+strings[0].trim()+"%'";
        if(!strings[1].equalsIgnoreCase(""))
            condicion += " and NombrePropietario like '%"+strings[1].trim()+"%'";
        if(!strings[2].equalsIgnoreCase(""))
            condicion += " and Calle like '"+strings[2].trim()+"'";
        if(!strings[3].equalsIgnoreCase(""))
            condicion += " and Numero like '"+strings[3].trim()+"'";
        if(!strings[4].equalsIgnoreCase(""))
            condicion += " and Fraccionamiento like '%"+strings[4].trim()+"%'";
        datos.clear();
        try{
            Cursor c = db.rawQuery("SELECT * FROM vs_InspM2 where 1=1 " + condicion, null);
            Log.i("que", "SELECT * FROM vs_InspM2 where 1=1 " + condicion);
            if(c.moveToFirst()){
                Log.i("no", c.getCount() + "");
                do{
                    datos.add("IdLicencia:" + c.getString(c.getColumnIndex("IdLicencia")) + " Propietario:" + c.getString(c.getColumnIndex("NombrePropietario")) + " Calle:" + c.getString(c.getColumnIndex("Calle")) + " Número:" + c.getString(c.getColumnIndex("Numero")) + " Fraccionamiento:" + c.getString(c.getColumnIndex("Fraccionamiento")) + " Giro:" + c.getString(c.getColumnIndex("GiroConstruccionLic")) + " m2:" + c.getString(c.getColumnIndex("m2")) + " m3:" + c.getString(c.getColumnIndex("m3"))+ " mL:" + c.getString(c.getColumnIndex("mL")));
                }while(c.moveToNext());

            }
            c.close();
        }catch (SQLiteException e) {
            Log.i("ERROR FATAL", e.getMessage());
        }finally{

        }
    }
}
