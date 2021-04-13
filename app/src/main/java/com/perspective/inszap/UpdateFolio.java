package com.perspective.inszap;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();
        String sql = "SELECT * FROM c_inspector where next_min = 1";

        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public class SetFolios extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            int count = 0;
            int bandera = 0;
            GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
            SQLiteDatabase db = gestion.getReadableDatabase();
            String sql = "SELECT * FROM c_inspector where next_min = 1";

            Cursor cursor = db.rawQuery(sql, null);
            count=cursor.getCount();
            if (cursor.moveToFirst()) {
                do {
                    ArrayList<NameValuePair> id = new ArrayList<>();
                    id.add(new BasicNameValuePair("id", cursor.getString(0)));
                    JSONObject jo = parser.realizarHttpRequest("http://192.168.100.19/", "GET", id);

                    try {
                        int estatus = jo.getInt("estatus");
                        int next_min = jo.getInt("folio_min");
                        int next_max = jo.getInt("folio_max");
                        if (estatus == 1) {
                            //update Local
                            ContentValues cv = new ContentValues();
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