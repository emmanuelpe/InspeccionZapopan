package com.perspective.inszap;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateFolio extends Service {
    private final Handler mHandler = new Handler();
    private final JSONParser parser = new JSONParser();
    private SharedPreferences sp;
    private int foliox = 0;
    static int validarM;
    static String urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";



    public UpdateFolio() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("servicio","creado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer mTimer = new Timer();
        long notify_interval = 10000;
        mTimer.schedule(new TimerTaskToGetLocation(),5, notify_interval);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("servicio","destruido");
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(datos() > 0) {
                        if(Connection.validarConexion(getApplicationContext()))
                            new SetFolios().execute();
                    }
                }
            });
        }
    }

    public int datos() {
        int count=0;
        try{
            GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);

            SQLiteDatabase db = gestion.getReadableDatabase();
            String sql = "SELECT * FROM c_inspector where next_min = 1 and next_max=0";

            Cursor cursor = db.rawQuery(sql, null);
            count = cursor.getCount();
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Log.e("erro", "datos: ",e );

        }

        return count;
    }

    public class SetFolios extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            sp = getSharedPreferences("infracciones", Context.MODE_PRIVATE);
            foliox = sp.getInt("folio",0);
            validarM = sp.getInt("modo",0);

            if(validarM==1) {
                //modoT.setChecked(true);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("modo", 1);
                editor.apply();
                //titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
                urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";
            }else {
                //modoT.setChecked(false);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("modo", 0);
                editor.apply();
                //titlem.setText("");
                urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
            }
            int count = 0;
            int bandera = 0;
            GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
            SQLiteDatabase db = gestion.getReadableDatabase();
            String sql = "SELECT * FROM c_inspector where next_min = 1 and next_max=0";

            Cursor cursor = db.rawQuery(sql, null);
            count=cursor.getCount();
            if (cursor.moveToFirst()) {
                do {
                    ArrayList<NameValuePair> id = new ArrayList<>();
                    id.add(new BasicNameValuePair("id", cursor.getString(0)));
                    JSONObject jo = parser.realizarHttpRequest(urlP+"getFolioLast.php", "GET", id);

                    try {
                        int estatus = jo.getInt("estatus");
                        int next_min = jo.getInt("folio_min");
                        int next_max = jo.getInt("folio_max");
                        if (estatus == 1) {
                            //update Local
                            ContentValues cv = new ContentValues();
                            Log.v("nexT_min", String.valueOf(next_min));
                            Log.v("next_max", String.valueOf(next_max));
                            cv.put("next_min", next_min);
                            cv.put("next_max", next_max);
                            int x = db.update("c_inspector", cv, "id_c_inspector = " + cursor.getInt(0), null);
                            Log.v("x",String.valueOf(x));
                            if(x > 0)
                                bandera++;

                        }
                    } catch (JSONException e) {
                        Log.v("error", e.getMessage() + "");
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return bandera==count;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast toast = Toast.makeText(getApplicationContext(),"Se envio",Toast.LENGTH_LONG);
                toast.setGravity(0,0,15);
                toast.show();
                stopSelf();
            }
        }
    }
}