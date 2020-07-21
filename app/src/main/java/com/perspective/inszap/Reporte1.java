package com.perspective.inszap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
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

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Reporte1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Button btnfecha;
    private Button btnfecha2;
    private Button botonaceptar,btnimprimir;
    private EditText ediF1, edif2;
    private int boton1, boton2, total, totalF = 0;
    private int re = 0, rn = 0, re2 = 0, rn2 = 0;
    private String fecha, numeros, fotografias = "", na = "", msj, fecha1 = "", fecha2 = "", na2 = "", msj2 = "",nombre;
    private ProgressBar pb;
    private JSONParser jParser = new JSONParser();
    private List<String> nal = new ArrayList<>();
    private List<String> nas = new ArrayList<>();
    private List<String> nas2 = new ArrayList<>();
    private List<String> nal2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte1);

        this.btnfecha = findViewById(R.id.btnFecha1);
        this.btnfecha2 = findViewById(R.id.btnFecha2);
        this.botonaceptar = findViewById(R.id.aceptarF);
        this.ediF1 = findViewById(R.id.etfecham1);
        this.edif2 = findViewById(R.id.etfecham2);
        this.btnimprimir=findViewById(R.id.imprimir);
        this.boton1 = 0;
        this.boton2 = 0;
        botonaceptar.setOnClickListener(this);
        btnimprimir.setVisibility(View.GONE);

        nombre = getIntent().getExtras().getString("usuario");

        Log.e("user",nombre);

        pb = findViewById(R.id.pb);

        final DataPickerDialogFragment p = new DataPickerDialogFragment();
        btnfecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.show(getSupportFragmentManager(), "data picker");
                //updateDisplay1(edif2);
                boton2 = 1;
            }
        });

        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.show(getSupportFragmentManager(), "data picker");
                boton1 = 1;
                //updateDisplay1(ediF1);
            }
        });

        /*botonaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        btnimprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final  String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Document doc = null;
                        File file = null;
                        FileOutputStream ficheroPdf = null;
                        PdfWriter write = null;
                        BaseFont bf = null;


                        try {
                            doc = new Document(PageSize.LETTER,25,25,10,20);
                            doc.setPageCount(1);
                            file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/reporte_enviadas_"+date.replace("-","_")+".pdf" );

                            if (file.exists()){
                                if (file.delete()){
                                    System.out.println("se elimino y se creo otro");
                                    file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/reporte_enviadas_"+date.replace("-","_")+".pdf" );

                                }else{
                                    System.out.println("no se pudo eliminar");
                                }
                            }
                            ficheroPdf = new FileOutputStream(file.getAbsoluteFile());

                            write = PdfWriter.getInstance(doc, ficheroPdf);


                        } catch (Exception e) {

                        }
                        try {
                            Paragraph p;

                            doc.open();

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            Bitmap bitmap = null;

                            bitmap = BitmapFactory.decodeResource(Reporte1.this.getResources(), R.drawable.reporte_enviadas_2);
                            //bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                            Image img;
                            try {
                                Font font1 = new Font(Font.HELVETICA, 9, Color.BLACK);
                                img = Image.getInstance(stream.toByteArray());
                                img.setAbsolutePosition(0, 0);

                                float width = doc.getPageSize().getWidth();
                                float height = doc.getPageSize().getHeight();

                                img.scaleToFit(width, height);
                                doc.add(img);

                                doc.add(new Paragraph(" "));
                                doc.add(new Paragraph(" ", font1));
                                doc.add(new Paragraph(" ", font1));
                                doc.add(new Paragraph(" ", font1));
                                doc.add(new Paragraph(" ", new Font(Font.BOLD, 21, Color.BLACK)));

                                Font font = new Font(Font.BOLD, 10, Color.BLACK);


                                PdfContentByte canvas = write.getDirectContent();


                                //NOMBRE INSPECTOR
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(135, 609.2f);
                                canvas.showText(nombre);
                                canvas.endText();
                                canvas.restoreState();

                                //FECHA
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(120, 583.3f);
                                canvas.showText(date);
                                canvas.endText();
                                canvas.restoreState();

                                String[] arrSplit = numeros.split(",");
                                String in[]=new String[arrSplit.length];
                                int inT[]=new int[arrSplit.length];
                                String ov[]=new String[arrSplit.length];
                                int ovt[]=new int[arrSplit.length];

                                if (Connection.validarConexion(getApplicationContext())) {
                                    GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
                                    SQLiteDatabase db = gestion.getReadableDatabase();
                                    Log.i("numeros", numeros);
                                    int mov=0;
                                    int mov2=0;

                                    for (int f = 0; f < arrSplit.length; f++){
                                        System.out.println("variable arraysplit; "+arrSplit[f]);
                                        String sql2 = "select count(numero_acta) as Total,numero_acta from Fotografia where numero_acta in (" + arrSplit[f] + ") group by numero_acta ";
                                        Cursor cursor1;
                                        cursor1 = db.rawQuery(sql2, null);
                                        totalF = 0;



                                        if (cursor1.moveToFirst()) {
                                            do {
                                                totalF = cursor1.getInt(cursor1.getColumnIndex("Total"));
                                                fotografias = cursor1.getString(cursor1.getColumnIndex("numero_acta"));
                                                System.out.println("variable fotografias: "+fotografias);
                                                if(fotografias.contains("IN")){
                                                    in[mov]=fotografias;
                                                    inT[mov]=totalF;
                                                    Log.e("fotografias: ", String.valueOf(totalF));
                                                    mov++;


                                                }else if(fotografias.contains("OV")){
                                                    ov[mov2]=fotografias;
                                                    ovt[mov2]=totalF;
                                                    mov2++;

                                                }



                                            } while (cursor1.moveToNext());
                                            Log.e("fotografias: ", fotografias);

                                        }else{
                                            if(arrSplit[f].contains("IN")){
                                                in[mov]=arrSplit[f];
                                                inT[mov]=0;

                                                mov++;


                                            }else if(arrSplit[f].contains("OV")){
                                                ov[mov2]=arrSplit[f];
                                                ovt[mov2]=0;
                                                mov2++;

                                            }
                                        }
                                    }
                                }

                                //orden de visita
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(85, 499f);
                                canvas.showText("Orden de Visita");
                                canvas.endText();
                                canvas.restoreState();
                                int renglon=15;
                                int vacio=-1;
                                int numV=0;
                                for (int i=0;i<ov.length;i++) {

                                    if (ov[i]!=null) {
                                        numV++;
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 9);
                                        canvas.moveText(85, 499f-renglon);
                                        if(ov[i].contains("'")){
                                            canvas.showText(ov[i].replace("'"," ").trim());
                                            canvas.endText();
                                            canvas.restoreState();

                                            renglon+=15;
                                        }else{
                                            canvas.showText(ov[i]);
                                            canvas.endText();
                                            canvas.restoreState();

                                            renglon+=15;
                                        }


                                    }
                                    else {
                                        vacio=i;

                                        System.out.println("posicion vacia orden visita: "+vacio);
                                        break;
                                    }



                                }
                                renglon=15;
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(230, 499f);
                                canvas.showText("Fotos");
                                canvas.endText();
                                canvas.restoreState();
                                int numVF=0;
                                for (int i=0;i<ovt.length;i++) {


                                    if (i!=vacio){
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 9);
                                        canvas.moveText(237, 499f-renglon);
                                        canvas.showText(String.valueOf(ovt[i]));
                                        canvas.endText();
                                        canvas.restoreState();
                                        if(ovt[i]!=0){
                                            numVF+=ovt[i];
                                        }
                                        renglon+=15;
                                    }else{
                                        break;
                                    }




                                }


                                //Infraccion
                                vacio=-1;
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(85, 499f-renglon-15);
                                canvas.showText("Infracción");
                                canvas.endText();
                                canvas.restoreState();
                                float hack=499f-renglon-15;
                                renglon=15;
                                int numIn=0;
                                for (int i=0;i<in.length;i++) {

                                    if(in[i]!=null){
                                        numIn++;
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 9);
                                        canvas.moveText(85, hack-renglon);
                                        if(in[i].contains("'")){
                                            canvas.showText(in[i].replace("'"," ").trim());
                                            canvas.endText();
                                            canvas.restoreState();
                                            renglon+=15;
                                        }else{
                                            canvas.showText(in[i]);
                                            canvas.endText();
                                            canvas.restoreState();
                                            renglon+=15;
                                        }


                                    }else{
                                        vacio=i;
                                        System.out.println("posicion vacia infracciones:"+vacio);
                                        break;
                                    }

                                }
                                renglon=15;


                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(230, hack);
                                canvas.showText("Fotos");
                                canvas.endText();
                                canvas.restoreState();

                                int numFin=0;
                                for (int i=0;i<inT.length;i++) {

                                    if(i!=vacio){

                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 9);
                                        canvas.moveText(237, hack-renglon);
                                        canvas.showText(String.valueOf(inT[i]));

                                        canvas.endText();
                                        canvas.restoreState();
                                        if(inT[i]!=0)
                                            numFin+=inT[i];
                                        renglon+=15;
                                    }else{
                                        break;
                                    }



                                }

                                //Totales
                               canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(85, hack-renglon-20);
                                canvas.showText("Total de Ordenes de Visita: " + String.valueOf(numV));
                                canvas.endText();
                                canvas.restoreState();

                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(85, hack-renglon-45);
                                canvas.showText("Total de Infracciones: "+ String.valueOf(numIn));
                                canvas.endText();
                                canvas.restoreState();

                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(85, hack-renglon-65);
                                canvas.showText("Total de Fotos: "+ String.valueOf(numFin+numVF));
                                canvas.endText();
                                canvas.restoreState();






                                doc.close();


                            } catch (BadElementException e) {
                                System.err.println(e.getMessage() + " BadElementException");
                            } catch (MalformedURLException e) {
                                System.err.println(e.getMessage() + " MalformedURLException");
                            } catch (IOException e) {
                                System.err.println(e.getMessage() + " IOException");
                            } catch (DocumentException e) {
                                System.err.println(e.getMessage() + " DocumentException");
                            }
                        }catch (Exception e){

                        }
                        //File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/reporte_enviadas_"+date.replace("-","_")+".pdf");

                        if(file.exists()) {
                            System.out.println(file.getName());
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setPackage("com.dynamixsoftware.printershare");
                            if (Build.VERSION.SDK_INT < 24)
                                i.setDataAndType(Uri.fromFile(file), "application/pdf");
                            else
                                i.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file), "application/pdf");
                            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(i);
                        }

                    }
                }).start();

            }

                //if(id == 1) {




        });
    }

    private void updateDisplay1(EditText v) {
        v.setText(fecha);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        String mes = "";
        //mes = monthOfYear+1 > 9 ? String.valueOf( monthOfYear+1 ) : 0 + "" + (monthOfYear+1);
        fecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        if (boton2 == 1 && boton1 == 0)
            updateDisplay1(edif2);
        boton2 = 0;
        if (boton1 == 1)
            updateDisplay1(ediF1);
        boton1 = 0;
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
            if (Connection.validarConexion(getApplicationContext())) {
                numeros = "";
                fotografias = "";
                total = 0;
                String sql = "select * from Levantamiento where fecha between '" + fecha1 + "' and '" + fecha2 + "'";
                Cursor cursor;
                GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
                SQLiteDatabase db = gestion.getReadableDatabase();
                cursor = db.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    do {
                        total++;
                        numeros += "'" + cursor.getString(cursor.getColumnIndex("numero_acta")) + "',";
                        nal.add(cursor.getString(cursor.getColumnIndex("numero_acta")));
                    } while (cursor.moveToNext());
                }
                if (!TextUtils.isEmpty(numeros)) {
                    numeros = numeros.substring(0, numeros.length() - 1);
                    Log.i("numeros", numeros);
                    String sql2 = "select count(numero_acta) as Total,numero_acta from Fotografia where numero_acta in (" + numeros + ") group by numero_acta ";
                    Cursor cursor1;
                    cursor1 = db.rawQuery(sql2, null);
                    totalF = 0;
                    if (cursor1.moveToFirst()) {
                        do {
                            totalF += cursor1.getInt(cursor1.getColumnIndex("Total"));
                            fotografias += "'" + cursor1.getString(cursor1.getColumnIndex("numero_acta")) + "',";

                        } while (cursor1.moveToNext());
                        Log.e("fotografias: ", fotografias);

                    }


                    new GetReport().execute(numeros, fotografias);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "No hay datos en ese rango de fechas", Toast.LENGTH_LONG);
                    toast.setGravity(0, 0, 15);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "No cuenta con conexión a internet", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
                toast.show();
            }
        }
    }

    public class GetReport extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            ArrayList<NameValuePair> report = new ArrayList<>();
            ArrayList<NameValuePair> report2 = new ArrayList<>();
            report.add(new BasicNameValuePair("numero", strings[0]));
            report2.add(new BasicNameValuePair("numero", strings[1]));
            na = "";
            na2 = "";

            if (Connection.validarConexion(getApplicationContext())) {
                JSONArray jsonArray = jParser.realizarHttpRequest1("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getReport.php", "POST", report);
                JSONArray jsonArray1 = jParser.realizarHttpRequest1("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getReport1.php", "POST", report2);
                nas.clear();
                try {
                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        Log.e("jsonObject", jsonObject.getString("numero_acta"));
                        na += jsonObject.getString("numero_acta") + ",";
                        nas.add(jsonObject.getString("numero_acta"));
                    }
                    for (int y = 0; y < jsonArray1.length(); y++) {
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(y);
                        na2 += jsonObject2.getString("numero_acta") + ",";
                        nas2.add(jsonObject2.getString("numero_acta"));
                        System.out.println("fotografias: " + na2);
                    }
                    if (!na2.isEmpty()) {
                        na2 = na2.substring(0, na2.length() - 1);
                    }
                    if (!na.isEmpty())
                        na = na.substring(0, na.length() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] na1 = numeros.replace("'", "").split(",");
                String[] nas = na.replace("'", "").split(",");
                String[] na3 = fotografias.replace("'", "").split(",");
                String[] nas2 = na2.replace("'", "").split(",");
                int p = 0;
                msj = "Hay " + total + " registros de " + fecha1 + " a " + fecha2 + " y se han enviado";
                re = 0;
                rn = 0;
                re2 = 0;
                rn2 = 0;
                String pend = "";
                String pend2 = "";

                for (int i = 0; i < na1.length; i++) {
                    if (Reporte1.this.nas.contains(na1[i])) {
                        Log.e("contiene a", na1[i]);
                        re++;
                    } else {
                        Log.e("no contiene a", na1[i]);
                        rn++;
                        pend += na1[i] + ", ";
                    }
                    //msj = "Hay " + total + " registros de " + fecha1 + " a " + fecha2 + " \ny se han enviado " + re + " pendientes de subir " + rn + "\n" + pend;
                    msj = "Actas generadas de " + fecha1 + " al " + fecha2 + ": " + na1.length + "\n" + "Se han enviado: " + re + "\n" + "Numero(s) de acta(s) pendiente(s) de subir: " + rn;
                }
                for (int x = 0; x < na3.length; x++) {
                    if (Reporte1.this.nas2.contains(na3[x])) {
                        Log.e("contiene f", na3[x]);
                        re2++;
                    } else {
                        Log.e("no contiene f", na3[x]);
                        rn2++;
                        pend2 += na3[x] + ", ";
                    }
                    msj2 = "Actas con fotografias generadas de " + fecha1 + " al " + fecha2 + ": " + na3.length + "\n" + "Se han enviado: " + re2 + "\n" + "Numero(s) de actas(s) con fotografia(s) pendiente(s) de subir: " + rn2;
                }
                Log.e("p", String.valueOf(p));
            }else{
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Reporte1.this);
                dialogo.setTitle("Message").setMessage("Conecte el dispositivo al internet").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogo.create().show();

            }

            return re == numeros.length();
            }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            pb.setVisibility(View.GONE);
            Log.i("result", String.valueOf(aBoolean));
            if (!aBoolean) {
                /*Toast toast = Toast.makeText(Reporte1.this,msj,Toast.LENGTH_LONG);
                toast.setGravity(0,0,15);
                toast.show();*/
                if (  rn2>0 && rn>0 || rn2<=0 && rn>0 || rn2>0 && rn<=0  ) {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(Reporte1.this);
                    dialogo.setTitle("Message").setMessage(msj + "\n" + msj2).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialogo.create().show();
                } else if(rn2<=0 && rn<=0) {
                   btnimprimir.setVisibility(View.VISIBLE);

                }

            }
        }
    }
}
