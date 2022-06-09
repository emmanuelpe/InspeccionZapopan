package com.perspective.inszap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.bixolon.printer.BixolonPrinter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReimprimirActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnImprimir;
    private Button btnImprimir1;
    private Button btnBuscar;
    private TextInputEditText etNumero;
    private TextInputLayout tilNumero;
    private JSONParser jsonParser;
    public static BixolonPrinter mBixolonPrinter;
    private AlertDialog mSampleDialog;
    private CircularProgressIndicator progressIndicator;
    private static final int RP_STORAGE = 1;

    //datos del acta
    private String numeroActa = "";
    private String meConstituyo = "";
    private String calle = "";
    private String nombreTestigo;
    private String nombreTestigo1;
    private String ifeT;
    private String ifeT1;
    private String hora;
    private String nombre;
    private String numeroInt = "";
    private String numeroExt = "";
    private String entreC = "";
    private String entreC1 = "";
    private String fraccionamiento = "";
    private String nombreV = "";
    private String seIdentifica = "";
    private String manifiestaSer = "";
    private String giro = "";
    private String medidaP = "";
    private String articulo_medida="";
    private String hechos = "";
    private String infracciones = "";
    private String manifiesta = "";
    private String gravedad = "";
    private String observacionGravedad = "";
    private  String medida = "";
    private String horaTermino = "";
    private String horainicio="";
    private  String nombreRazon = "";
    private String designado1 = "";
    private String designado2 = "";
    private String fecha = "";
    private int infraccion = 0;
    private String folio_ap="";
    private String id_c_direccion="";
    private String OV="";
    private String fechaOV="";
    private String zona="";
    private int idCInspector=0;
    private int IdCInspector2=0;
    private String uso_suelo="";
    private String densidad="";
    private String condominio="";
    private String motivo_orden="";
    private String actividad_giro="";
    private String nombre_comercial="";
    private String peticion="";
    private String nivel_economico="4";
    private String reincidencia="";
    private String folio_peticion="";
    private String fecha_ap="";
    private String decomiso="";
    private String numero_sellos="";
    private String folio_clausura="";
    private String fecha_clausura="";
    private String vigencia_inicial="";
    private String vigencia_final="";
    private int tipoEntrega=0;
    private String clave="";
    private String folio="";





    //private String manifiesta="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reimprimir);
        etNumero = findViewById(R.id.etNumero2);
        btnBuscar = findViewById(R.id.btnBuscarR);
        btnImprimir = findViewById(R.id.btnImprimir2);
        btnImprimir1 = findViewById(R.id.btnImprimir1);
        tilNumero = findViewById(R.id.tilNumero);
        progressIndicator = findViewById(R.id.progress);
        btnImprimir.setOnClickListener(this);
        btnImprimir1.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
        jsonParser = new JSONParser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscarR:
                if(!TextUtils.isEmpty(etNumero.getText().toString().trim())) {
                    new GetData().execute(etNumero.getText().toString());

                }
                break;
            case R.id.btnImprimir2:
                //checkPermisionToApp(Manifest.permission.WRITE_EXTERNAL_STORAGE,RP_STORAGE);
                //
                imprimirR("infraccion");
                if(consultaActa(etNumero.getText().toString())==0)
                descargarFotografia(etNumero.getText().toString());
                try {

                    try{
                        //if(id == 1) {

                        File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/"+numeroActa+"/" + numeroActa +".pdf");

                        if(file.exists()) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setPackage("com.dynamixsoftware.printershare");
                            if (Build.VERSION.SDK_INT < 24)
                                i.setDataAndType(Uri.fromFile(file), "application/pdf");
                            else
                                i.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file), "application/pdf");
                            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(i);
                        }
                    }catch (Exception e) {
                        Log.e("Error al", e.getMessage());
                    }
                }catch(Exception e) {
                    System.out.println(e.getMessage() + " lll");
                    Toast toast = Toast.makeText(ReimprimirActivity.this, "Hubo un error al imprimir", Toast.LENGTH_SHORT);
                    toast.setGravity(0, 0, 15);
                    toast.show();
                }
                break;
            case R.id.btnImprimir1:
                //printT();
                break;
        }
    }
    private void checkPermisionToApp(String permisionStr, int requestPermission) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this,permisionStr) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{permisionStr},requestPermission);
                return;
            }
            switch (requestPermission) {
                case RP_STORAGE:
                    //imprimir("infraccion");
                    break;
            }
        }else {
            //imprimir("infraccion");
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class GetData extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressIndicator.setVisibility(View.VISIBLE);
            btnBuscar.setEnabled(false);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... strings) {
            ArrayList<NameValuePair> acta = new ArrayList<>();
            acta.add(new BasicNameValuePair("acta", strings[0]));
            acta.add(new BasicNameValuePair("clave", "PGM2021"));
            try {
                if (consultaActa(strings[0]) == 1) {
                    JSONObject jsonObject = jsonParser.realizarHttpRequest("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getActaR.php", "GET", acta);
                    int estatus = jsonObject.getInt("estatus");
                    Log.v("estatus", String.valueOf(estatus));
                    JSONObject object = jsonObject.getJSONObject("data");
                    if (estatus == 1) {
                        for (int x = 0; x < jsonObject.length(); x++) {
                            if (x == 1) {
                                numeroActa = object.getString("numero_acta").trim();
                                id_c_direccion = object.getString("id_c_direccion").trim();
                                if (object.has("fecha"))
                                    fecha = object.getString("fecha").trim();
                                else
                                    fecha = "";
                                if (object.has("infraccion"))
                                    infraccion = object.getInt("infraccion");
                                else
                                    infraccion = 0;

                                hora = object.getString("hora_inicio").trim();
                                OV = object.getString("orden_visita").trim();
                                fechaOV = object.getString("fecha_orden_v").trim();
                                zona = object.getString("Zona").trim();
                                idCInspector = object.getInt("id_c_inspector1");
                                IdCInspector2 = object.getInt("id_c_inspector2");
                                nombreV = object.getString("nombre_visitado").trim();
                                seIdentifica = object.getString("se_identifica").trim();
                                manifiestaSer = object.getString("manifiesta_ser").trim();
                                fraccionamiento = object.getString("fraccionamiento").trim();
                                calle = object.getString("calle").trim();
                                numeroExt = object.getString("numero_ext").trim();
                                numeroInt = object.getString("numero_int").trim();
                                nombreRazon = object.getString("nombre_razon").trim();
                                nombreTestigo = object.getString("nombre_testigo1").trim();
                                ifeT = object.getString("ife_testigo1").trim();
                                designado1 = object.getString("designado_por1").trim();
                                nombreTestigo1 = object.getString("nombre_testigo2").trim();
                                ifeT1 = object.getString("ife_testigo2").trim();
                                designado2 = object.getString("designado_por2").trim();
                                hechos = object.getString("hechos").trim();
                                infracciones = object.getString("infracciones").trim();
                                uso_suelo = object.getString("uso_suelo").trim();
                                densidad = object.getString("densidad").trim();
                                manifiesta = object.getString("manifiesta").trim();
                                gravedad = object.getString("gravedad").trim();
                                horaTermino = object.getString("hora_termino").trim();
                                condominio = object.getString("condominio").trim();
                                entreC = object.getString("entre_calle1").trim();
                                entreC1 = object.getString("entre_calle2").trim();
                                medidaP = object.getString("medida").trim();
                                articulo_medida = object.getString("articulo_medida").trim();
                                motivo_orden = object.getString("motivo_orden").trim();
                                actividad_giro = object.getString("actividad_giro").trim();
                                nombre_comercial = object.getString("nombre_comercial").trim();
                                peticion = object.getString("peticion").trim();
                                nivel_economico = object.getString("nivel_economico").trim();
                                reincidencia = object.getString("reincidencia").trim();
                                folio_peticion = object.getString("folio_peticion").trim();
                                folio_ap = object.getString("folio_apercibimiento").trim();
                                fecha_ap = object.getString("fecha_apercibimiento").trim();
                                decomiso = object.getString("decomiso").trim();


                                Log.v("dato", fecha + " " + infraccion);
                                GestionBD gestionBD = new GestionBD(ReimprimirActivity.this, "inspeccion", null, 1);
                                SQLiteDatabase db = gestionBD.getReadableDatabase();
                                String sql = "SELECT nombre,folio,vigencia,vigencia_inicial,clave FROM c_inspector where id_c_inspector = " + idCInspector;
                                Cursor c = db.rawQuery(sql, null);
                                if (c.moveToFirst()) {
                                    do {
                                        nombre = c.getString(0);
                                        folio = c.getString(1);
                                        vigencia_inicial = c.getString(2);
                                        vigencia_final = c.getString(3);
                                        clave = c.getString(4);
                                    } while (c.moveToNext());
                                }
                                c.close();
                                db.close();
                                Log.v("data", object.getString("hora_inicio"));

                            }
                        }

                        return true;
                    } else {
                        return false;
                    }


                }else{
                    GestionBD gestionBD = new GestionBD(ReimprimirActivity.this, "inspeccion", null, 1);
                    SQLiteDatabase db = gestionBD.getReadableDatabase();
                    String sql = "SELECT * FROM levantamiento where numero_acta= '"+strings[0]+"'";
                    Cursor c = db.rawQuery(sql, null);
                    if (c.moveToFirst()) {
                        do {
                           /* for (int i = 0; i < c.getColumnCount(); i++) {
                                System.err.println(c.getColumnName(i) + ": " + c.getString(i));
                            }*/
                            numeroActa = strings[0];
                            id_c_direccion =String.valueOf( c.getInt(c.getColumnIndex("id_c_direccion")));
                            if (String.valueOf(c.getInt(c.getColumnIndex("fecha")))!="")
                                fecha = String.valueOf(c.getInt(c.getColumnIndex("fecha"))).trim();
                            else
                                fecha = "";


                            hora = c.getString(c.getColumnIndex("hora_inicio")).trim();
                            OV = c.getString(c.getColumnIndex("orden_vista")).trim();
                            fechaOV = c.getString(c.getColumnIndex("fecha_orden_v")).trim();
                            zona = c.getString(c.getColumnIndex("Zona")).trim();
                            idCInspector = c.getInt(c.getColumnIndex("id_c_inspector1"));
                            IdCInspector2 = c.getInt(c.getColumnIndex("id_c_inspector2"));
                            nombreV = c.getString(c.getColumnIndex("nombre_visitado")).trim();
                            seIdentifica = c.getString(c.getColumnIndex("se_identifica")).trim();
                            manifiestaSer = c.getString(c.getColumnIndex("manifiesta_ser")).trim();
                            fraccionamiento = c.getString(c.getColumnIndex("fraccionamiento")).trim();
                           calle = c.getString(c.getColumnIndex("calle")).trim();
                            numeroExt = c.getString(c.getColumnIndex("numero_ext")).trim();
                            numeroInt = c.getString(c.getColumnIndex("numero_int")).trim();
                            nombreRazon = c.getString(c.getColumnIndex("nombre_razon")).trim();
                            nombreTestigo = c.getString(c.getColumnIndex("nombre_testigo1")).trim();

                            ifeT = c.getString(c.getColumnIndex("ife_testigo1")).trim();
                            designado1 = c.getString(c.getColumnIndex("designado_por1")).trim();
                            nombreTestigo1 = c.getString(c.getColumnIndex("nombre_testigo2")).trim();
                            ifeT1 = c.getString(c.getColumnIndex("ife_testigo2")).trim();
                            designado2 = c.getString(c.getColumnIndex("designado_por2")).trim();
                            hechos = c.getString(c.getColumnIndex("hechos")).trim();
                            infracciones = c.getString(c.getColumnIndex("infracciones")).trim();
                            uso_suelo = c.getString(c.getColumnIndex("uso_suelo")).trim();
                            densidad = c.getString(c.getColumnIndex("densidad")).trim();
                            manifiesta = c.getString(c.getColumnIndex("manifiesta")).trim();
                            gravedad = c.getString(c.getColumnIndex("gravedad")).trim();
                           horaTermino = c.getString(c.getColumnIndex("hora_termino")).trim();
                            condominio = c.getString(c.getColumnIndex("condominio")).trim();
                            entreC = c.getString(c.getColumnIndex("entre_calle1")).trim();
                            entreC1 = c.getString(c.getColumnIndex("entre_calle2")).trim();
                            medidaP = c.getString(c.getColumnIndex("medida_seguridad")).trim();
                            articulo_medida = c.getString(c.getColumnIndex("articulo_medida")).trim();
                            //motivo_orden = c.getString(c.getColumnIndex("motivo_orden")).trim();
                            actividad_giro = c.getString(c.getColumnIndex("actividad_giro")).trim();
                            nombre_comercial = c.getString(c.getColumnIndex("nombre_comercial")).trim();
                            peticion = c.getString(c.getColumnIndex("peticion")).trim();
                            nivel_economico = String.valueOf( c.getInt(c.getColumnIndex("nivel_economico")));
                            reincidencia = c.getString(c.getColumnIndex("reincidencia")).trim();
                            folio_peticion = c.getString(c.getColumnIndex("folio_peticion")).trim();
                            folio_ap = c.getString(c.getColumnIndex("folio_apercibimiento")).trim();
                            fecha_ap = c.getString(c.getColumnIndex("fecha_apercibimiento")).trim();
                            decomiso = c.getString(c.getColumnIndex("decomiso")).trim();




                        } while (c.moveToNext());
                    }
                    c.close();
                    db.close();

                    //Log.v("dato", fecha + " " + infraccion);
                    GestionBD gestionBD2 = new GestionBD(ReimprimirActivity.this, "inspeccion", null, 1);
                    SQLiteDatabase db2 = gestionBD2.getReadableDatabase();
                    String sql2 = "SELECT nombre,folio,vigencia,vigencia_inicial,clave FROM c_inspector where id_c_inspector = " + idCInspector;
                    Cursor c2 = db2.rawQuery(sql2, null);
                    if (c2.moveToFirst()) {
                        do {
                            nombre = c2.getString(0);
                            folio = c2.getString(1);
                            vigencia_inicial = c2.getString(2);
                            vigencia_final = c2.getString(3);
                            clave = c2.getString(4);
                        } while (c2.moveToNext());
                    }
                    c2.close();
                    db2.close();
                    return true;
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                Log.e("error", e.getMessage(),null );
                return false;
            }

        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressIndicator.setVisibility(View.GONE);
            btnBuscar.setEnabled(true);
            if(aBoolean) {
                Log.v("aBoolean","trajo dato");
                btnImprimir.setVisibility(View.VISIBLE);
                btnImprimir1.setVisibility(View.VISIBLE);
            } else {
                Log.v("aBoolean","no trajo dato " + aBoolean);
                btnImprimir.setVisibility(View.GONE);
                btnImprimir1.setVisibility(View.GONE);
                final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ReimprimirActivity.this);
                builder.setTitle("Confirmación")
                        .setMessage("No se encontro el acta")
                        .setPositiveButton(getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        }
    }
    public int consultaActa(String acta){
        GestionBD gestionBD = new GestionBD(ReimprimirActivity.this, "inspeccion", null, 1);
        SQLiteDatabase db = gestionBD.getReadableDatabase();
        String sql = "SELECT numero_acta FROM levantamiento where numero_acta= '"+acta+"'";
        Cursor c = db.rawQuery(sql, null);
        String numero_acta2="";
        if (c.moveToFirst()) {
            do {
                numero_acta2 = c.getString(0);
                Log.e("entro", "consultaActa: "+numero_acta2,null );
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        //Log.e("entro2", "consultaActa: "+numero_acta,null );

        if(numero_acta2.isEmpty()){
            Log.e("entro3", "consultaActa: "+numero_acta2,null );

            return 1;
        }else{
            Log.e("entro4", "consultaActa: "+numero_acta2,null );

            return 0;
        }



    }
    public void imprimirR(String formato) {

        int len =0;
        final int MITAD = 135;
        String src;
        String [] txt;
        String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + numeroActa;
        File f = new File(path);
        f.mkdirs();

        Document doc = null;
        File file = null;
        FileOutputStream ficheroPdf = null;
        PdfWriter write = null;
        BaseFont bf = null;
        int marginleft=0;
        int margingright=0;
        if(formato.equalsIgnoreCase("infraccion")){
            marginleft=25;
            margingright=35;
        }else{
            marginleft=22;
            margingright=26;

        }
        try {
            doc = new Document(PageSize.LEGAL,marginleft,margingright,20,20);
            file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/"+ numeroActa+ "/"+numeroActa+".pdf");
            if(file.isFile()){

                file.delete();

            }
            ficheroPdf = new FileOutputStream(file.getAbsoluteFile());

            write = PdfWriter.getInstance(doc, ficheroPdf);


        } catch (Exception e) {

        }

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        String [] na = thisDate.split("/");
        Log.i("fecha", na[0] + "/" + na[1] + "/" + na[2]);
        fecha = na[0] + "/" + na[1] + "/" + na[2];
        String [] fechas = fecha.split("/");
        int dia, mes,a;
        String me;
        for (int i = 0; i < fechas.length; i++) {
            System.out.println(fechas[i]);
        }

        dia = Integer.parseInt(fechas[0]);
        mes = Integer.parseInt(fechas[1]);
        String an = fechas[2];
        //a = Integer.parseInt(fechas[2].substring(2, 4));
        a = Integer.parseInt(fechas[2]);
        System.out.println("entro");
        me = Justificar.mes(mes);



        Font font1 = new Font(Font.HELVETICA,9.5f, Color.BLACK);

        if (formato.equalsIgnoreCase("infraccion")) {
            String cuerpoInfra="";
            String cuerpoInfra21="";
            String mocha="";

            int banderacorte=0;
            //ACTA DE INFRACCION
            try {

                DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();
                dottedLineSeparator.setGap(7);

                Chunk chunk = new Chunk(dottedLineSeparator);

                //File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");


                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String  ext = file.getName().substring(file.getName().indexOf(".")+1);
                String tipo = mime.getMimeTypeFromExtension(ext);


                Paragraph p;

                doc.open();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = null;
						/*if(id != 4)
						    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_1);
						else
						    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_c);*/

                bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_vacia);
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
                Image img;

                try {

                    img = Jpeg.getInstance(stream.toByteArray());
                    img.setAbsolutePosition(0, 0);

                    float width = doc.getPageSize().getWidth();
                    float height = doc.getPageSize().getHeight();

                    img.scaleToFit(width, height);
                    doc.add(img);

                } catch (BadElementException e) {
                    System.err.println(e.getMessage() + " BadElementException");
                } catch (MalformedURLException e) {
                    System.err.println(e.getMessage() + " MalformedURLException");
                } catch (IOException e) {
                    System.err.println(e.getMessage() + " IOException");
                } catch (DocumentException e) {
                    System.err.println(e.getMessage() + " DocumentException");
                }

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",new Font(Font.BOLD,21,Color.BLACK)));

                Font font = new Font(Font.BOLD,10,Color.BLACK);


                PdfContentByte canvas = write.getDirectContent();


                //DIRECCION
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(30, 910);
                canvas.showText(getdireccion(id_c_direccion));
                canvas.endText();
                canvas.restoreState();

                //ZONA
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(200, 910);
                canvas.showText(zona);
                canvas.endText();
                canvas.restoreState();

                //NUMERO DE ACTA
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 11);
                canvas.moveText(450, 910);
                canvas.showText(numeroActa);
                canvas.endText();
                canvas.restoreState();

                String uso = "";
                String vig = vigencia_inicial;

                String [] vig1 = vig.split("-");

                int d,m,ax;

                d=Integer.parseInt(vig1[2]);
                m=Integer.parseInt(vig1[1]);
                ax=Integer.parseInt(vig1[0]);

                String prop = "",numero = "";
                prop=nombreRazon;
                //Log.e("PROPIEDAD",propiedad.equalsIgnoreCase("El Visitado") + "");




                if(numeroInt.trim().equalsIgnoreCase(""))
                    numero = numeroExt + " " + numeroInt;
                else
                    numero = numeroExt + " Int.  " + numeroInt;

                String datos = "";
                Log.i("gg", "imprimirR: " + OV + ": fecha: "+ fechaOV);
                if(!OV.equalsIgnoreCase("")) {


                    if(consultaActa(numeroActa)==1) {
                        String[] fecha_ov = fechaOV.split("-");
                        datos = "mediante y en cumplimiento de la Orden de Visita folio número "
                                + OV + "  dictada por el Director de Inspección y Vigilancia de Zapopan, Jalisco, el día " +  fecha_ov[2].substring(0, 3) + " de " + mes(fecha_ov[1]) + " del año " + fecha_ov[0] + " misma que en original exhibo y en original legible entrego " +
                                "al visitado, " + nombreV + ",";
                    }else{
                        String[] fecha_ov = fechaOV.split("/");
                        datos = "mediante y en cumplimiento de la Orden de Visita folio número "
                                + OV + "  dictada por el Director de Inspección y Vigilancia de Zapopan, Jalisco, el día " + fecha_ov[0] + " de " + mes(fecha_ov[1]) + " del año " + fecha_ov[2].substring(0, 4) + " misma que en original exhibo y en original legible entrego " +
                                "al visitado, " + nombreV + ",";

                    }
                } else {
                    datos = "en términos de lo dispuesto por el artículo 73, segundo párrafo, de la Ley del Procedimiento Administrativo del Estado de Jalisco,";
                }
                String tipoentrega="";


                if(nombreV.equals("No proporcionó datos")){
                    tipoentrega="el visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus artículos 86 y 87, con descripcion de media filiacion.";
                    tipoEntrega=0;
                }
                if(nombreV.equals("Ausente")){
                    tipoentrega="en ausencia de persona alguna, se llevó a cabo la presente diligencia por cédula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87.";
                    tipoEntrega=2;
                }

                /*243, 197*/
                /*}*/



                String peticionb="";
                if(peticion.equals("Flagrancia")){
                    peticionb=peticion;
                }else{
                    if(folio_peticion.length()>2){
                        peticionb=peticion+" con folio "+folio_peticion;

                    }else{
                        peticionb=peticion;

                    }

                }


                String vigencia=vigencia_final;
                //String vigencia_inicial=vigencia;
                String []recorte1=vigencia_inicial.split("-");
                String []recorte2=vigencia.split("-");

                String diaIni=recorte2[2];
                String diavigen=recorte1[2];
                vigencia_inicial=mes(recorte2[1]);
                vigencia=mes(recorte1[1]);

                Paragraph p2 = null;
                String testigos="";
                String nombresT="";
                if(nombreTestigo.length()>4 && nombreTestigo1.length()>4){
                    testigos= "mismos que se identifican con "+ifeT.trim() + " y " +ifeT1.trim() +" respectivamente; ";
                    nombresT=nombreTestigo.trim() + " y " + nombreTestigo1.trim();
                }
                if(nombreTestigo.length()>4 && nombreTestigo1.length()<=1){
                    testigos= "mismo que se identifica con "+ifeT.trim() +" ";
                    nombresT= nombreTestigo.trim().trim();
                }
                if(nombreTestigo.length()<=1 && nombreTestigo1.length()>=1){
                    testigos="mismo que se identifica con "+ifeT1.trim()+ " ";
                    nombresT=nombreTestigo1.trim();
                }
                String textC="";
                if(!condominio.equals(""))
                    textC="condominio "+condominio;

                //String decomiso = "";
                if(!TextUtils.isEmpty(decomiso))
                    decomiso += "decomiso: " + decomiso + " ";

                if(id_c_direccion.equals( "4")) {
                    String apercibimiento="";
                    if(condominio.trim().length()>1 && folio_ap.length()>1 && fecha_ap.length()>1){
                        String folioa=folio_ap;
                        String fechap=fecha_ap;
                        apercibimiento="Dar seguimiento a lo señalado en el previo apercibimiento folio "+folioa+ " de fecha "+fechap+"";

                    }




                    String medidasP=medidaP;



                    String numeroS="";
                    if(numero_sellos.trim().length()>0){
                        numeroS="con numero de sello(s) "+numero_sellos.trim();
                    }
                    hechos=hechos.trim().substring(0,hechos.trim().length()-1);
                    if(!uso_suelo.contains("pública") | !uso_suelo.contains("público"))
                        uso = "el uso " + uso_suelo + " ";
                    else
                        uso = uso_suelo;
                   /* p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + horaTermino.substring(10,16) + " horas del día de " +horaTermino.substring(8,10)  + " de " + mes(horaTermino.substring(5,7)) + " del año " + horaTermino.substring(0,4) + ", el suscrito " + nombre +
                            " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en " + uso +" marcada con el número " +
                            numero + " de la calle " + calle + " entre las calles " + entreC + " y " + entreC1 + " en la colonia y/o fraccionamiento " + fraccionamiento + " "+textC+ ",  cerciorándome de ser este el domicilio por coincidir con la nomenclatura oficial y/o georreferencia, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número" +
                            " "+folio + ", vigente del " +diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+ " de "+ vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del Gobierno Municipal de Zapopan, Jalisco, ante " + nombreV + " quien se identifica con, " + seIdentifica +
                            " manifiesta ser " + manifiestaSer + " del lugar en que se actúa, propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía por lo que fueron designados los C.C. " + nombresT + " por el " + designado1 +
                            ", "+ testigos + "así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " +
                            apercibimiento +", "+ hechos + ".Los cuales constituyen infracción a lo dispuesto por los artículo(s): 2, 3, 5, 7  FRACCIONES I  a la VI, 34,  167, 168, 169, 171 ," + infracciones + ". Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados , se procede indistintamente con las siguientes medidas: " + medidasP + " "+ numeroS+".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + articulo_medida + ". En uso de su derecho el visitado manifiesta: " + manifiesta +
                            ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                            horaTermino.substring(10,16)+ " horas del " +horaTermino.substring(8,10)  + " de " + mes(horaTermino.substring(5,7)) + " del " + horaTermino.substring(0,4)  + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=",font1);
*/
                    if(consultaActa(numeroActa)==1) {
                        cuerpoInfra = "En la ciudad de Zapopan, Jalisco, siendo las " + horaTermino.substring(10, 16) + " horas del día de " + fecha.substring(9, 10) + " de " + mes(fecha.substring(6, 8)) + " del año " + fecha.substring(0, 4) + ", el suscrito " + nombre +
                                " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, " + datos + " me constituí física y legalmente en " + uso + " marcada con el número " +
                                numero + " de la calle " + calle + " entre las calles " + entreC + " y " + entreC1 + " en la colonia y/o fraccionamiento " + fraccionamiento + " " + textC + ",  cerciorándome de ser este el domicilio por coincidir con la nomenclatura oficial y/o georreferencia, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número" +
                                " " + folio + ", vigente del " + diaIni + " de " + vigencia_inicial + " del " + recorte2[0] + " a " + diavigen + " de " + vigencia + " del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del Gobierno Municipal de Zapopan, Jalisco, ante " + nombreV + " quien se identifica con, " + seIdentifica +
                                " manifiesta ser " + manifiestaSer + " del lugar en que se actúa, propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía por lo que fueron designados los C.C. " + nombresT + " por el " + designado1 +
                                ", " + testigos + "así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " +
                                apercibimiento + hechos + ".Los cuales constituyen infracción a lo dispuesto por los artículo(s): 2, 3, 5, 7  FRACCIONES I  a la VI, 34,  167, 168, 169, 171 ," + infracciones + "  Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: " + medidasP.trim() + " " + numeroS + ".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + articulo_medida + ". En uso de su derecho el visitado manifiesta: " + manifiesta +
                                ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                horaTermino.substring(10, 16) + " horas del " + fecha.substring(8, 10) + " de " + mes(fecha.substring(5, 7)) + " del " + fecha.substring(0, 4) + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=";

                        mocha = ". En uso de su derecho el visitado manifiesta: " + manifiesta +
                                ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                horaTermino.substring(10, 16) + " horas del " + fecha.substring(8, 10) + " de " + mes(fecha.substring(5, 7)) + " del " + fecha.substring(0, 4) + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=";
                    }else {
                        Log.e("fecha", fecha,null );
                        cuerpoInfra = "En la ciudad de Zapopan, Jalisco, siendo las " + horaTermino + " horas del día de " + fecha.substring(0, 2) + " de " + mes(fecha.substring(3, 5)) + " del año " + fecha.substring(6, 10) + ", el suscrito " + nombre +
                                " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, " + datos + " me constituí física y legalmente en " + uso + " marcada con el número " +
                                numero + " de la calle " + calle + " entre las calles " + entreC + " y " + entreC1 + " en la colonia y/o fraccionamiento " + fraccionamiento + " " + textC + ",  cerciorándome de ser este el domicilio por coincidir con la nomenclatura oficial y/o georreferencia, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número" +
                                " " + folio + ", vigente del " + diaIni + " de " + vigencia_inicial + " del " + recorte2[0] + " a " + diavigen + " de " + vigencia + " del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del Gobierno Municipal de Zapopan, Jalisco, ante " + nombreV + " quien se identifica con, " + seIdentifica +
                                " manifiesta ser " + manifiestaSer + " del lugar en que se actúa, propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía por lo que fueron designados los C.C. " + nombresT + " por el " + designado1 +
                                ", " + testigos + "así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " +
                                apercibimiento + hechos + ".Los cuales constituyen infracción a lo dispuesto por los artículo(s): 2, 3, 5, 7  FRACCIONES I  a la VI, 34,  167, 168, 169, 171 ," + infracciones + "  Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: " + medidasP.trim() + " " + numeroS + ".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + articulo_medida + ". En uso de su derecho el visitado manifiesta: " + manifiesta +
                                ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                horaTermino + " horas del " + fecha.substring(0, 2) + " de " + mes(fecha.substring(3, 5)) + " del " + fecha.substring(6, 10) + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=";

                        mocha = ". En uso de su derecho el visitado manifiesta: " + manifiesta +
                                ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                horaTermino + " horas del " + fecha.substring(0, 2) + " de " + mes(fecha.substring(3, 5)) + " del " + fecha.substring(6, 10) + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=";

                    }

                    if(cuerpoInfra.length()>5000) {

                        String recrte = cuerpoInfra;
                        String texto ="";
                        int x=0;
                        if(cuerpoInfra.length()>=5000 && cuerpoInfra.length()<=5300){
                            cuerpoInfra21 = recrte.substring(0,cuerpoInfra.length()-mocha.length()+1 ) + " (CONTINUA EN EL REVERSO)";
                            banderacorte = cuerpoInfra.length()-mocha.length()+1;
                        }else {
                            for (int i = 5000; i < recrte.length(); i++) {
                                String substring = cuerpoInfra.substring(i, i + 1);
                                //Log.i(TAG, " caracteresgg1:"+ substring);. En uso


                                if (substring.equals(" ")) {
                                    //Log.i(TAG, " caracteresgg:"+ substring);
                                    cuerpoInfra21 = recrte.substring(0, i) + " (CONTINUA EN EL REVERSO)";
                                    banderacorte = i;
                                    break;
                                }


                           }
                        }
                    }else{
                        cuerpoInfra21=cuerpoInfra;
                    }

                    p2= new Paragraph(cuerpoInfra21,font1);

                }
                p2.setAlignment(Element.ALIGN_JUSTIFIED);
                p2.add(chunk);
                doc.add(p2);



                //GRAVEDAD
                if(id_c_direccion.equals("4")){
                    if(nombreV.equals("No proporcionó datos")){
                        tipoentrega="El visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus artículos 86 y 87, con descripcion de media filiacion.";
                        String []imprimir = Justificar.justifocarTexto1(tipoentrega, 35);
                        float salto=320;
                        for(int i=0;i<imprimir.length;i++){
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(175, salto);
                            canvas.showText(imprimir[i]);
                            canvas.endText();
                            canvas.restoreState();

                            salto-=10;
                        }
                        //tipoEntrega=0;
                    }
                    if(nombreV.equals("Ausente")){
                        tipoentrega="En ausencia de persona alguna, se llevó a cabo la presente diligencia por cédula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87.";
                        //tipoEntrega=2;
                        String []imprimir = Justificar.justifocarTexto1(tipoentrega, 35);
                        float salto=290;
                        for(int i=0;i<imprimir.length;i++){
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(175, salto);
                            canvas.showText(imprimir[i]);
                            canvas.endText();
                            canvas.restoreState();

                            salto-=10;
                        }
                    }
                }


                if (gravedad.equalsIgnoreCase("1")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(83, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (gravedad.equalsIgnoreCase("2")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(91, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (gravedad.equalsIgnoreCase("3")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(99, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (gravedad.equalsIgnoreCase("4")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(107, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(115, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                }

                //NIVEL ECONOMICO
                if (nivel_economico.equalsIgnoreCase("1")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(228, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (nivel_economico.equalsIgnoreCase("2")) {
                    canvas.saveState();
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(236, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (nivel_economico.equalsIgnoreCase("3")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(243, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (nivel_economico.equalsIgnoreCase("4")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(251, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(259, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                }


                //REINCIDENCIA

                if(!reincidencia.equals("NO")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(370, 195);
                    canvas.showText("SI");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(385, 195);
                    canvas.showText("NO");
                    canvas.endText();
                    canvas.restoreState();
                }

                //NUMERO DE INFRACCION
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(460, 190);
                canvas.showText(numeroActa);
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(117, 134);
                canvas.showText("__________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(180, 134);
                canvas.showText("___________________________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(351, 134);
                canvas.showText("_______________________________________________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(150, 123);
                canvas.showText(numeroActa);
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(148, 122);
                canvas.showText("______________________");
                canvas.endText();
                canvas.restoreState();



                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(125, 92);
                canvas.showText(String.valueOf(a));
                canvas.endText();
                canvas.restoreState();


                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(121, 90);
                canvas.showText("_________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(420, 90);
                canvas.showText("_________________________________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(30, 75);
                canvas.showText("______________________________________________________________________________________________________________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(30, 60);
                canvas.showText("______________________________________________________________________________________________________________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(30, 45);
                canvas.showText("______________________________________________________________________________________________________________");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(60, 19);
                canvas.showText("NOTA: En atencion a :"+ peticionb);
                canvas.endText();
                canvas.restoreState();


                if(cuerpoInfra.length()>5000){
                    doc.newPage();
                    int tamano=cuerpoInfra.length();
                    String cuerpo2=cuerpoInfra.substring(banderacorte,tamano);
                    //Log.i(TAG, "imprimir: entro al maximo caracteres2");

                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    Bitmap bitmap2 = null;


                    bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.hoja_blanca);
                    bitmap2.compress(Bitmap.CompressFormat.JPEG , 100, stream2);
                    Image img2;

                    try {

                        img2 = Jpeg.getInstance(stream2.toByteArray());
                        img2.setAbsolutePosition(0, 0);

                        float width = doc.getPageSize().getWidth();
                        float height = doc.getPageSize().getHeight();

                        img2.scaleToFit(width, height);
                        doc.add(img2);

                    } catch (BadElementException e) {
                        System.err.println(e.getMessage() + " BadElementException");
                    } catch (MalformedURLException e) {
                        System.err.println(e.getMessage() + " MalformedURLException");
                    } catch (IOException e) {
                        System.err.println(e.getMessage() + " IOException");
                    } catch (DocumentException e) {
                        System.err.println(e.getMessage() + " DocumentException");
                    }

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" ",font1));
                    doc.add(new Paragraph(" ",font1));
                    doc.add(new Paragraph(" ",font1));
                    doc.add(new Paragraph(" ",new Font(Font.BOLD,21,Color.BLACK)));

                    Font font2 = new Font(Font.BOLD,10,Color.BLACK);





                    //DIRECCION
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(30, 910);
                    canvas.showText("(CONTINUA DEL ANVERSO)");
                    canvas.endText();
                    canvas.restoreState();



                    p2= new Paragraph(cuerpo2.trim(),font2);
                    p2.setAlignment(Element.ALIGN_JUSTIFIED);
                    p2.add(chunk);
                    doc.add(p2);
                }


                doc.close();



            }catch (DocumentException e) {
                System.err.println(e.getMessage() + " doc ");
            }catch (IOException C) {

            }



            }

    }
    public String mes(String texto){
        String retorno="";
        if(texto.equals("01") || texto.equals("1")  ){
            retorno="Enero";

        }
        if(texto.equals("02") || texto.equals("2") ){
            retorno= "Febrero";

        }
        if(texto.equals("03") || texto.equals("3") ){
            retorno="Marzo";

        }
        if(texto.equals("04") || texto.equals("4") ){
            retorno= "Abril";

        }
        if(texto.equals("05") || texto.equals("5") ){
            retorno= "Mayo";

        }
        if(texto.equals("06") || texto.equals("6") ){
            retorno= "Junio";

        }
        if(texto.equals("07") || texto.equals("7") ){
            retorno="Julio";

        }
        if(texto.equals("08") || texto.equals("8") ){
            retorno="Agosto";

        }
        if(texto.equals("09") || texto.equals("9") ){
            retorno="Septiembre";

        }
        if(texto.equals("10")  ){
            retorno="Octubre";

        }
        if(texto.equals("11")){
            retorno= "Noviembre";

        }
        if(texto.equals("12")){
            retorno="Diciembre";

        }


        return retorno;
    }
    public String getdireccion(String id){
        if(id.equals("2")){
            return "Área Comercio e Industria";

        }
        if(id.equals("3")) {

            return "Área Técnica";
        }
        if(id.equals("4")){

            return "Área Construcción";
        }

        if(id.equals("5")){
            return "Área Horarios Especiales";
        }
        return "";
    }

    public void descargarFotografia(String numeroActa) {
        GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Fotografia where descripcion like '%PDF%' and numero_acta='"+numeroActa+"'", null);

        ContentValues cv = new ContentValues();
        cv.put("estatus1", "N");
        cv.put("estatus","N");

        //db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
        try {
            if (db != null) {
                System.out.println(c.getCount() + " F");
                if (c.moveToFirst()) {
                    do {
                          if(c.getString(c.getColumnIndex("estatus"))!="S" || c.getString(c.getColumnIndex("estatus1"))!="S")
                          db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);

                        System.out.println(c.getInt(0)+ " F en elchat");
                    } while (c.moveToNext());
                }else{

                        db.close();
                        c.close();

                    if(consultaActa(numeroActa)==0)
                    insertFotrografia(0,numeroActa,numeroActa+".pdf","PDF","N","N");


                }

            }
        } catch (SQLiteException e) {
            Log.e("descargar foto", e.getMessage());
        }
    }

    public long insertFotrografia(int levantamiento, String numeroActa,String archivo, String descripcion,String com,String estatus) {
        long n = 0;
        GestionBD gestionarBD = new GestionBD(this, "inspeccion",null,1);
        SQLiteDatabase db = gestionarBD.getWritableDatabase();

        if (db != null) {
            try {
                ContentValues cv = new ContentValues();
                cv.put("id_levantamiento", 0);
                cv.put("numero_acta", numeroActa);
                cv.put("archivo", archivo);
                cv.put("descripcion", descripcion);
                cv.put("enviado", com);
                cv.put("estatus", estatus);
                n = db.insert("Fotografia", null, cv);
            }catch (SQLiteException e) {
                Log.e("ERROR en:", e.getMessage());
            }
            finally {
                db.close();
            }
        }
        return n;
    }
}
