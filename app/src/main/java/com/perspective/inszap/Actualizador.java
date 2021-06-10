package com.perspective.inszap;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Button;

import net.mftd313.updatelibrary.UpdateLibrary;
import net.mftd313.updatelibrary.listeners.UpdateDownloadStartedListener;
import net.mftd313.updatelibrary.listeners.UpdateInstallStartedListener;
import net.mftd313.updatelibrary.listeners.UpdateReadyToDownloadListener;
import net.mftd313.updatelibrary.listeners.UpdateReadyToInstallListener;

import java.net.HttpURLConnection;
import java.net.URL;


//REQUISITOS ANDROID +4.2.0
//Se tiene que trabajar con el link directo de descarga
//El archivo tiene que ser .apk
//Se debe de llevar el siguiente formato de versiones nombreApk_X_Y_Z.apk
//Siempre que se realize una nueva version el nombre del apk tiene que ser ascendente
//Ejem: old: nombreApk_1_3_8.apk ------ new: nombreApk_1_3_9.apk

//EJEMPLO DE UNA URL CORRECTA
//https://github.com/esteban1810/IntentoApp/raw/main/intentoapp_1_1_1.apk

//Agregar

//build.gradle PROJECT
//allprojects {
//    repositories {
//    ...
//    maven { url 'https://jitpack.io' }
//    }
//}

//build.gradle MODULE
//dependencies {
//    ...
//    implementation 'com.github.mfadi313:android-app-update-library:1.1.1'
//}

public class Actualizador extends AsyncTask<String, Void, String> {
    Context context;

    public Actualizador(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    //Recibe como argumentos
    //Url ejem: https://github.com/esteban1810/IntentoApp/raw/main
    //NombreApk ejem: intentoapp
    //Valores x,y,z  ejem: intentoapp_1_2_3.apk - intentoapp_x_y_z.apk
    //x = 1
    //y = 2
    //z = 3
    @Override
    protected String doInBackground(String... params) { //Busca que exista una actualizacion
        int auxX=0,auxY=0,auxZ=0;
        String url="";
        String nombreApk="";
        String base;
        String direccion;

        if(params.length==2){
            url = params[0];
            nombreApk = params[1];
            direccion = url+"/"+nombreApk+".apk";

            System.out.println(direccion);
            if(this.actualizar(direccion)){
                return direccion;
            }
        } else {
            if(params.length==5){
                url = params[0];
                nombreApk = params[1];
                auxX = Integer.parseInt(params[2]);
                auxY = Integer.parseInt(params[3]);
                auxZ = Integer.parseInt(params[4])+1;

                base = url+"/"+nombreApk;

                for(int x = auxX;x<10;x++){
                    for(int y = auxY;y<10;y++){
                        for(int z = auxZ;z<10;z++){
                            direccion = base+"_"+x+"_"+y+"_"+z+".apk";
                            System.out.println(direccion);

                            if(this.actualizar(direccion)){
                                return direccion;
                            }
                        }
                        auxZ=0;
                    }
                    auxY=0;
                }
            }
        }
        return null;
    }

    public boolean actualizar(String direccion){
        try{
            final URL u = new URL(direccion);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");
            int responseCode = huc.getResponseCode();

            if (responseCode != 404) {
                System.out.println("GOOD");
                return true;
            } else {
                System.out.println("BAD");
                return false;
            }
        } catch (Exception e){
            System.out.println("ERROR");
            return false;
        }
    }

    @Override
    protected void onPostExecute(String url) { //Actualiza la app con la url obtenida
        final ProgressDialog progressDialog = new ProgressDialog(context);

        if(url==null){
            System.out.println("F");
            return;
        }

        UpdateLibrary.with(context)

                // while downloading notification
                .setDownloadingNotificationTitle(context.getString(R.string.app_name))
                .setDownloadingNotificationText(context.getString(R.string.downloading_new_version))

                // before installing notification
                .setDownloadedNotificationSmallIconResource(R.mipmap.ic_launcher)
                .setDownloadedNotificationLargeIconResource(R.mipmap.ic_launcher)
                .setDownloadedNotificationTitle(context.getString(R.string.app_name))
                .setDownloadedNotificationText(context.getString(R.string.download_completed))

                .setUpdateReadyToDownloadListener(new UpdateReadyToDownloadListener() {
                    @Override
                    public void onReadyToDownload(final Context context, Uri uri) {
                        UpdateLibrary.getUpdateManager().download(context);
                        progressDialog.hide();
                    }
                })
                .setUpdateDownloadStartedListener(new UpdateDownloadStartedListener() {
                    @Override
                    public void onDownloadStarted(Context context, Uri uri) {
                        progressDialog.setMessage(context.getString(R.string.downloading_new_version));
                        progressDialog.show();
                    }
                })
                .setUpdateReadyToInstallListener(new UpdateReadyToInstallListener() {
                    @Override
                    public void onReadyToInstall(final Context context, Uri uri) {
                        UpdateLibrary.getUpdateManager().install(context);
                        progressDialog.hide();
                    }
                })
                .setUpdateInstallStartedListener(new UpdateInstallStartedListener() {
                    @Override
                    public void onInstallStarted(Context context, Uri uri) {
                        progressDialog.setMessage(context.getString(R.string.installing_new_version));
                        progressDialog.show();
                    }
                })

                .init(Uri.parse(url));
    }
}


