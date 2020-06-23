package com.perspective.inszap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reporte1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Button btnfecha;
    private Button btnfecha2;
    private Button botonaceptar;
    private EditText ediF1,edif2;
    private int boton1,boton2,total = 0;
    private String fecha,numeros = "",na = "",msj,fecha1 = "", fecha2= "";
    private ProgressBar pb;
    private JSONParser jParser = new JSONParser();
    private List<String> nal = new ArrayList<>();
    private List<String> nas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte1);

        this.btnfecha=findViewById(R.id.btnFecha1);
        this.btnfecha2=findViewById(R.id.btnFecha2);
        this.botonaceptar = findViewById(R.id.aceptarF);
        this.ediF1=findViewById(R.id.etfecham1);
        this.edif2=findViewById(R.id.etfecham2);
        this.boton1=0;
        this.boton2=0;
        botonaceptar.setOnClickListener(this);

        pb = findViewById(R.id.pb);

        final DataPickerDialogFragment p= new DataPickerDialogFragment();
        btnfecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.show(getSupportFragmentManager(),"data picker");
                //updateDisplay1(edif2);
                boton2=1;
            }
        });

        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.show(getSupportFragmentManager(),"data picker");
                boton1=1;
                //updateDisplay1(ediF1);
            }
        });

        /*botonaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }
    private void updateDisplay1( EditText v) {
        v.setText(fecha);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        String mes = "";
        //mes = monthOfYear+1 > 9 ? String.valueOf( monthOfYear+1 ) : 0 + "" + (monthOfYear+1);
        fecha= dayOfMonth+"/"+(monthOfYear + 1)+"/"+year;
        if (boton2==1 && boton1==0)
        updateDisplay1(edif2);
        boton2=0;
        if(boton1 == 1)
            updateDisplay1(ediF1);
        boton1=0;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.aceptarF) {
            Log.i("boton", ediF1.getText().toString().trim() + " " + edif2.getText().toString().trim());
            validate();
        }
    }

    public void validate() {
        ediF1.setError(null);
        edif2.setError(null);

        fecha1 = ediF1.getText().toString().trim();
        fecha2 = edif2.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(fecha)) {
            ediF1.setError("Seleccione una fecha");
            focusView = ediF1;
            cancel = true;
        }

        if (TextUtils.isEmpty(fecha1)) {
            edif2.setError("Seleccione una fecha");
            focusView = ediF1;
            cancel = true;
        }

        if (cancel)
            focusView.requestFocus();
        else {
            if(Connection.validarConexion(getApplicationContext())) {
                numeros  = "";
                total = 0;
                String sql = "select * from Levantamiento where fecha between '"+fecha1+"' and '"+fecha2+"'";
                Cursor cursor;
                GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
                SQLiteDatabase db = gestion.getReadableDatabase();
                cursor = db.rawQuery(sql,null);
                if(cursor.moveToFirst()) {
                    do {
                        total ++;
                        numeros += "'" + cursor.getString(cursor.getColumnIndex("numero_acta")) + "',";
                        nal.add(cursor.getString(cursor.getColumnIndex("numero_acta")));
                    }while (cursor.moveToNext());
                }
                if(!TextUtils.isEmpty(numeros)) {
                    numeros = numeros.substring(0, numeros.length() - 1);
                    Log.i("numeros",numeros);
                    new GetReport().execute(numeros);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"No hay datos en ese rango de fechas",Toast.LENGTH_LONG);
                    toast.setGravity(0,0,15);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),"No cuenta con conexión a internet",Toast.LENGTH_LONG);
                toast.setGravity(0,0,15);
                toast.show();
            }
        }
    }

    public class GetReport extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            ArrayList<NameValuePair> report = new ArrayList<>();
            report.add(new BasicNameValuePair("numero",strings[0]));
            na = "";

            JSONArray jsonArray = jParser.realizarHttpRequest1("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getReport.php", "POST", report);
            nas.clear();
            try {
                for (int x = 0; x < jsonArray.length(); x++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(x);
                    Log.e("jsonObject", jsonObject.getString("numero_acta"));
                    na += jsonObject.getString("numero_acta") + ",";
                    nas.add(jsonObject.getString("numero_acta"));
                }
                na = na.substring(0,na.length()-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String [] na1 = numeros.replace("'","").split(",");
            String [] nas = na.replace("'","").split(",");
            int p = 0;
            msj = "Hay " + total + " registros de " + fecha1 + " a " + fecha2 + " y se han enviado";
            int re = 0,rn = 0;
            String pend = "";

            for (int i = 0;i < na1.length;i++) {
                if(Reporte1.this.nas.contains(na1[i])) {
                    Log.e("contiene",na1[i]);
                    re++;
                } else {
                    Log.e("no contiene",na1[i]);
                    rn++;
                    pend += na1[i] + ",";
                }
                //msj = "Hay " + total + " registros de " + fecha1 + " a " + fecha2 + " \ny se han enviado " + re + " pendientes de subir " + rn + "\n" + pend;
                msj= "Actas generadas de "+ fecha1 + " al "+ fecha2 + "\n"+"Se han enviado: " + re +"\n"+"Numero(s) de acta(s) pendiente(s) de subir: "+ rn + "\n"+ "Acta(s) faltante(s) de subir:" +"\n"+
                        pend;
            }
            Log.e("p", String.valueOf(p));
            return re == numeros.length();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            pb.setVisibility(View.GONE);
            Log.i("result",String.valueOf(aBoolean));
            if(!aBoolean) {
                /*Toast toast = Toast.makeText(Reporte1.this,msj,Toast.LENGTH_LONG);
                toast.setGravity(0,0,15);
                toast.show();*/

                AlertDialog.Builder dialogo = new AlertDialog.Builder(Reporte1.this);
                dialogo.setTitle("Message").setMessage(msj).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogo.create().show();
            }
        }
    }
}