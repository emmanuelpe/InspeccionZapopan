package com.perspective.inszap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 *
 * @author Emmanuel
 *
 */


public class Descarga extends Activity implements android.content.DialogInterface.OnClickListener, OnClickListener {

	static final JSONParser jParser = new JSONParser();
	private Button btnReporte,btnDescargarD, btnDescargarF, btnActualizar, btnInfraccion, btnSalir, btnPrueba, btnConfig, btnUpdate, btnConsultarL, btnConsultar, btnReimprimir1, btnConsultarLicenciaC, btnLicencias,btnActualizarApp;
	private String mFTP = "172.16.1.21"/*"servicios.tlajomulco.gob.mx"/*"pgt.no-ip.biz"*/, dir, arch, result, us, msj, res, direccion, config = "";
	private int id, aux = 0, id_l, count = 0, countF = 0, ve = 0, con,values_cr = 0,totalCR = 0;
	private int v = 0, v1;
	private FTPClient client;
	private Connection conn = new Connection();
	private JSONArray jarray;
	private JSONArray jsonA;
	private JSONObject json_data, jObject;
	private StringBuilder sb = new StringBuilder();
	private Connection c;
	private Switch modoT;
	static String urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
	String fechasicro=" ";
	static int validarM;
	private ArrayList<String> foto = new ArrayList<String>();
	private ArrayList<String> archivo = new ArrayList<String>();
	private EditText mEdittText;
	private ProgressDialog pd;
	private static SharedPreferences sp;
	private final static String NOMBRE_DIRECTORIO = "MiPdf";
	private final static String NOMBRE_DOCUMENTO = "prueba.txt";
	private final static String ETIQUETA_ERROR = "ERROR";
	private BluetoothAdapter mBluetoothAdapter = null;
	private Bundle bundle = null;
	private JSONParser jsonParser = new JSONParser();
	private String[] tablet;
	//private String token,id_user;
	private String liga, version;
	private HttpURLConnection urlConnection;
	private TextView titleD;
	private ProgressBar mProgressBar;
	int x = 0;
	int z=0;
	int contador=0;
    TextView prog,titlem;
    private SharedPreferences.Editor editor = null;
    private int idIns = 0;

	private int folio=0;
	private int max=0;
	private int colchon;
	private int next=0;
	private int min = 0;
	private int auxId = 0;

	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descarga);

		savedInstanceState = getIntent().getExtras();
		this.us = savedInstanceState.getString("usuario");
		this.id = savedInstanceState.getInt("id");
		this.idIns = savedInstanceState.getInt("idInps");
		System.out.println(MainActivity.id_ins_sesion);
		this.direccion = savedInstanceState.getString("direccion");
        prog=(TextView) findViewById(R.id.progresoF);
        titlem=(TextView)findViewById(R.id.modoT);
		Typeface helvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeueLTStd-Bd.otf");

		this.btnInfraccion = (Button) findViewById(R.id.btnInfraccion);
		this.btnDescargarD = (Button) findViewById(R.id.btnDescargarDato);
		this.btnDescargarF = (Button) findViewById(R.id.btnDescargaFoto);
		this.btnActualizar = (Button) findViewById(R.id.btnActualizar);
		this.btnSalir = (Button) findViewById(R.id.btnSalir);
		btnPrueba = (Button) findViewById(R.id.btnPrueba);
		btnConfig = (Button) findViewById(R.id.btnConfig);
		this.btnUpdate = (Button) findViewById(R.id.btnDescargaApp);
		btnConsultarL = (Button) findViewById(R.id.btnConsultarLicencia);
		btnConsultar = (Button) findViewById(R.id.btnConsultar);
		btnReimprimir1 = (Button) findViewById(R.id.btnReimprimir1);
		btnConsultarLicenciaC = findViewById(R.id.btnConsultarLicenciaC);
		btnLicencias = findViewById(R.id.btnLicencias);
		mProgressBar = findViewById(R.id.mProgressBar);
		btnReporte=findViewById(R.id.btnReporte);
		modoT = findViewById(R.id.swModo);
		this.btnActualizarApp = (Button)findViewById(R.id.btnActualizarApp);

		titleD = (TextView) findViewById(R.id.titleD);

		titleD.setTypeface(helvetica);

		btnInfraccion.setTypeface(helvetica);
		btnDescargarD.setTypeface(helvetica);
		btnDescargarF.setTypeface(helvetica);
		btnActualizar.setTypeface(helvetica);
		btnSalir.setTypeface(helvetica);
		btnPrueba.setTypeface(helvetica);
		btnConfig.setTypeface(helvetica);
		btnUpdate.setTypeface(helvetica);
		btnConsultarL.setTypeface(helvetica);
		btnConsultar.setTypeface(helvetica);
		btnReimprimir1.setTypeface(helvetica);
		btnLicencias.setTypeface(helvetica);
		btnReporte.setTypeface(helvetica);
		btnActualizar.setTypeface(helvetica);

		btnPrueba.setOnClickListener(this);
		btnConfig.setOnClickListener(this);
		this.btnUpdate.setOnClickListener(this);
		btnConsultarL.setOnClickListener(this);
		btnConsultar.setOnClickListener(this);
		btnReimprimir1.setOnClickListener(this);
		btnConsultarLicenciaC.setOnClickListener(this);
		btnReporte.setOnClickListener(this);
		btnPrueba.setVisibility(View.GONE);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		btnLicencias.setOnClickListener(this);
		modoT.setChecked(false);
		mProgressBar.setVisibility(View.GONE);
		//desbloquear reimpresion
		if (!direccion.trim().contains("Construcc")) {
			btnReimprimir1.setVisibility(View.GONE);
		}else{

		}


		c = new Connection(getApplicationContext());

		//if(us.equalsIgnoreCase("administrador") | us.equalsIgnoreCase("subadministrador")) {

		System.out.println(direccion.equalsIgnoreCase("Administración"));
		sp = getSharedPreferences("infracciones", Context.MODE_PRIVATE);
		//validarM=getSharedPreferences("infracciones", Context.MODE_PRIVATE);

		validarM = sp.getInt("modo",0);

		if(validarM==1) {

			//modoT.setVisibility(View.VISIBLE);
			urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";
			titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
		}else {
			//modoT.setVisibility(View.VISIBLE);
			urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
			titlem.setText("Modo de Produccion "+ getResources().getString(R.string.version) );

		}
		if (direccion.equalsIgnoreCase("Administracion") | direccion.equalsIgnoreCase("Administración")) {
			btnInfraccion.setEnabled(false);
			btnDescargarD.setEnabled(false);
			btnDescargarF.setEnabled(false);
			btnConsultarL.setEnabled(false);
			btnConsultar.setEnabled(false);
			btnActualizar.setEnabled(true);
			btnConfig.setEnabled(true);
			btnUpdate.setEnabled(true);

			btnInfraccion.setVisibility(View.GONE);
			btnDescargarD.setVisibility(View.GONE);
			btnDescargarF.setVisibility(View.GONE);
			btnConsultarL.setVisibility(View.GONE);
			btnConsultar.setVisibility(View.GONE);
			//btnConsultarL.setVisibility(View.GONE);
			btnReporte.setVisibility(View.GONE);
			btnConsultarLicenciaC.setVisibility(View.GONE);
			if(actasPendientes()==0 && conn.validarConexion(getApplicationContext()) ) {
				modoT.setVisibility(View.VISIBLE);
			}else{
				MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Descarga.this)
						.setTitle("Para activar el cambio de modo entre tester y producción o conectar a la red")
						.setMessage( "Favor de descargar  documentos pendientes: "+ actasPendientes())
						.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {




							}
						});

				builder.create().show();
			}
			//titlem.setVisibility(View.VISIBLE);
			if(validarM==1) {
				modoT.setChecked(true);
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("modo", 1);
				editor.apply();
				titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
				urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";
			}else {
				modoT.setChecked(false);
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("modo", 0);
				editor.apply();
				titlem.setText("Modo de Produccion "+ getResources().getString(R.string.version) );
				urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
			}


		} else {

			auxId = sp.getInt("idIns",0);
			Log.v("ids",idIns + " " + auxId);
		}
		Log.v("direccion", direccion.trim().contains("Comercio") + "");
		if (direccion.trim().contains("Comercio")) {
			btnConsultarL.setEnabled(false);
			btnConsultarL.setVisibility(View.GONE);
			btnConsultarLicenciaC.setVisibility(View.GONE);
			btnConfig.setVisibility(View.GONE);
			btnActualizarApp.setVisibility(View.GONE);
			btnActualizar.setVisibility(View.GONE);
		}else {
			/*btnActualizar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnUpdate.setEnabled(false);*/
			auxId = sp.getInt("idIns",0);
			Log.v("ids",idIns + " " + auxId);
		}

		if (direccion.trim().contains("Construcc")) {
			btnConsultarLicenciaC.setEnabled(true);
			btnConsultarL.setVisibility(View.GONE);
			btnConfig.setVisibility(View.GONE);
			//btnConfig.setVisibility(View.GONE);
			btnActualizarApp.setVisibility(View.GONE);
			btnActualizar.setVisibility(View.GONE);
		}else {
			/*btnActualizar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnUpdate.setEnabled(false);*/
			auxId = sp.getInt("idIns",0);
			Log.v("ids",idIns + " " + auxId);
		}
		if (direccion.trim().contains("Técnica")) {
			btnConsultarL.setEnabled(false);
			btnConsultarL.setVisibility(View.GONE);
			btnConsultar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnConfig.setVisibility(View.GONE);
			btnUpdate.setEnabled(false);
			btnConsultarLicenciaC.setVisibility(View.GONE);
			btnActualizarApp.setVisibility(View.GONE);
			btnActualizar.setVisibility(View.GONE);

		}else {
			/*btnActualizar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnUpdate.setEnabled(false);*/
			auxId = sp.getInt("idIns",0);
			Log.v("ids",idIns + " " + auxId);
		}

		if(direccion.trim().contains("Especiales")) {
			btnConsultarL.setEnabled(false);
			btnConsultarL.setVisibility(View.GONE);
			btnConsultar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnConfig.setVisibility(View.GONE);
			btnUpdate.setEnabled(false);
			btnConsultarLicenciaC.setVisibility(View.GONE);
			btnActualizarApp.setVisibility(View.GONE);
			btnActualizar.setVisibility(View.GONE);

		}else {
			/*btnActualizar.setEnabled(false);
			btnConfig.setEnabled(false);
			btnUpdate.setEnabled(false);*/
			auxId = sp.getInt("idIns",0);
			Log.v("ids",idIns + " " + auxId);
		}


		editor = sp.edit();


		int co = sp.getInt("c", 0);

		con = sp.getInt("config", 0);

		values_cr = sp.getInt("values_cr",0);

		System.err.println(values_cr + " VALUES_CR");

		System.err.println(con + " c");

		if (con == 0) {
			btnInfraccion.setEnabled(false);

		} else {
			if (direccion.equalsIgnoreCase("Administracion") | direccion.equalsIgnoreCase("Administración")) {
				btnInfraccion.setEnabled(false);
				editor.putInt("idIns",1);
				editor.apply();
			} else {
				btnInfraccion.setEnabled(true);
				if(auxId == idIns) {
					Log.v("no","pasa nada");
				} else if(auxId == 0) {
					editor.putInt("idIns",idIns);
					editor.apply();
				} else {

					//validacion de actas pendientes por subir
					//System.out.println(verificarActas()+" :verifico");

						Log.v("si", "sincronizar");
						btnInfraccion.setEnabled(false);
						MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Descarga.this)
								.setTitle(getResources().getString(R.string.msj))
								.setMessage(getResources().getString(R.string.debe_sinconizar))
								.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if (Connection.validarConexion(Descarga.this)) {
											new Sincronizar().execute(String.valueOf(idIns));
										} else {
											String mensjae = "No hay conexion a internet!!";
											Toast toast = Toast.makeText(Descarga.this, mensjae, Toast.LENGTH_SHORT);
											toast.setGravity(0, 0, 15);
											toast.show();
										}
									}
								}).setNegativeButton("No", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								});
						builder.create().show();

				}
				auxId = sp.getInt("idIns",0);
				Log.v("ids",idIns + " " + auxId);

			}

		}

		config = sp.getString("numt", "");
		System.out.println(co);
		System.out.println(config + " config");

		if (co == 0) {
			GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
			SQLiteDatabase db = gestion.getReadableDatabase();
			db.execSQL("Alter table Levantamiento add correo TEXT");
			System.out.println(co + " if");
			editor.putInt("c", 1);
		} else
			System.out.println(co + " else");

		editor.putInt("v", 1);
		editor.commit();

		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			ve = info.versionCode;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		if(values_cr == 0)
			btnInfraccion.setEnabled(false);

		v = sp.getInt("v", 0);
		/*if (!conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
		//if (!conn.search("http://192.168.0.11/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
			if(conn.validarConexion(getApplicationContext())) {
				if(!conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getC_Direccion.php").equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if(!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if(!conn.search("http://192.168.0.11/serverSQL/getC_Direccion.php").equalsIgnoreCase("No se pudo conectar con el servidor")) {
					result = conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getVersion.php");
					//result = conn.search("http://172.16.1.21/serversql/getVersion.php");
					//result = conn.search("http://192.168.0.11/serversql/getVersion.php");
					if (!result.trim().equalsIgnoreCase("null")) {
						try {
							this.jarray = new JSONArray(result);
							for (int i = 0; i < jarray.length(); i++) {
								this.json_data = this.jarray.getJSONObject(i);
								v1 = json_data.getInt("version_app");
							}
						} catch (JSONException e) {
							Log.e("idl", e.getMessage());
						}
						if (v != v1) {
							mostrarMsg();
						}else
							System.out.println("no actualizar");
					}
				}
			}
		}*/
		/*yourCheckBox.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
                //is chkIos checked?
        if (((CheckBox) v).isChecked()) {
                         //Case 1
        }
        else
          //case 2

      }
    });*/
       if(id==1) {

		   modoT.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View view) {

				   if (modoT.isChecked()) {

					   MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Descarga.this)
							   .setTitle("Seguro de activar el modo tester?")
							   .setMessage(getResources().getString(R.string.continuar))
							   .setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
								   @Override
								   public void onClick(DialogInterface dialog, int which) {



									   titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
									   SharedPreferences.Editor editor = sp.edit();
									   editor.putInt("modo", 1);
									   editor.apply();


									   urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";

									   new Ingresar().execute(String.valueOf(values_cr));

								   }
							   }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
								   @Override
								   public void onClick(DialogInterface dialog, int which) {
									   modoT.setChecked(false);

									   SharedPreferences.Editor editor = sp.edit();
									   editor.putInt("modo", 0);
									   editor.apply();
									   titlem.setText("Modo de Produccion "+ getResources().getString(R.string.version) );

									   urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";

									   new Ingresar().execute(String.valueOf(values_cr));



								   }
							   });

					   builder.create().show();




				   } else {

					   MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Descarga.this)
							   .setTitle("Seguro de activar el modo Produccion?")
							   .setMessage(getResources().getString(R.string.continuar))
							   .setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
								   @Override
								   public void onClick(DialogInterface dialog, int which) {

									   modoT.setChecked(false);

									   SharedPreferences.Editor editor = sp.edit();
									   editor.putInt("modo", 0);
									   editor.apply();
									   titlem.setText("Modo de Produccion "+ getResources().getString(R.string.version) );

									   urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";

									   new Ingresar().execute(String.valueOf(values_cr));
								   }
							   }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
								   @Override
								   public void onClick(DialogInterface dialog, int which) {
                                     modoT.setChecked(true);
									   titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
									   SharedPreferences.Editor editor = sp.edit();
									   editor.putInt("modo", 1);
									   editor.apply();


									   urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";


									   new Ingresar().execute(String.valueOf(values_cr));




								   }
							   });

					   builder.create().show();





				   }
			   }
		   });
	   }
        this.btnReporte.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				//System.out.println("entro"+getactasrango("2020/01/01","2020/06/15","33"));
				Intent intent;

					intent = new Intent(Descarga.this, Reporte1.class);



				Bundle bundle = new Bundle();
				bundle.putString("usuario", us.trim());
				bundle.putInt("id", id);
				bundle.putString("direccion", direccion);
				bundle.putInt("con", con);
				bundle.putString("url",urlP);
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});


		//ESTEBAN
		btnActualizarApp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String x,y,z;
				String url = getString(R.string.url);
				String nombreApk = getString(R.string.nombreApk);
				String version = getString(R.string.version);
				String xyz[];

				btnActualizarApp.setEnabled(false);
				Toast.makeText(Descarga.this, "Espera un momento...", Toast.LENGTH_SHORT).show();

				xyz = version.split("\\.");

				x = xyz[0].substring(xyz[0].length()-1);
				y = xyz[1];
				z = xyz[2].substring(0,1);

				Actualizador actualizador = new Actualizador(Descarga.this);
				actualizador.execute(url,nombreApk,x,y,z);
			}
		});
		//Fin ESTEBAN

		this.btnDescargarD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Descargas().execute();
				new Actualizar().execute();

				//if (verificar().equals("")) {
					/*if (!conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
						if (conn.validarConexion(getApplicationContext())) {
							descargarLevantamiento();
							descargarDetalle();
							descargarFotografia();
							msj = "Datos enviados al servidor";
						}
						else
							msj =  "No se encontro conexion a internet";
					}
					else
						msj = "No se pudo conectar con el servidor";*/

				/*}else
					msj = "No hay datos guardados en el dispositivo";
				Toast toast = Toast.makeText(Descarga.this, msj, Toast.LENGTH_SHORT);
				toast.setGravity(0, 0, 15);
				toast.show();*/
			}
		});
this.btnUpdate.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		Uri uri = Uri.parse("https://github.com/CristianOmarSandovalAceves/APKZapopan/blob/master/app-debug.apk");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
});
		this.btnDescargarF.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (conn.validarConexion(getApplicationContext())) {
					new EFoto().execute();
					/*Toast toast = Toast.makeText(getApplicationContext(), "Proceso de actualizar iniciado espere por favor!!", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();*/
					//new Ingresar().execute(String.valueOf(values_cr));
				}else{
					msj = "No se encontro conexion a internet";
					Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});

		this.btnActualizar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (conn.validarConexion(getApplicationContext())) {
					//if(comprobar()) {
					//insertar();
					new Ingresar().execute(String.valueOf(values_cr));
					//msj = "Datos Actualizados";
					//}
					/*else
						msj = "Problemas con el servidor";*/
				} else{
					msj = "No se encontro conexion a internet";
					Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}



			}
		});

		this.btnInfraccion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = null;
				if (id != 3) {
					intent = new Intent(Descarga.this, InfraccionesActivity.class);
				}else{
					intent = new Intent(Descarga.this, InfraccionesActivityTecnica.class);
				}
				Bundle bundle = new Bundle();
				bundle.putString("usuario", us.trim());
				bundle.putInt("id", id);
				bundle.putString("direccion", direccion);
				bundle.putInt("con", con);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
        btnSalir.setVisibility(View.GONE);
		this.btnSalir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onDestroy();
			}
		});

		consultaTabletas();
//23/04
		if(direccion.equalsIgnoreCase("administracion") || direccion.equalsIgnoreCase("administración") || direccion.equalsIgnoreCase("Administración")  ) {
			Log.i("admin", "entro" );
		} else {
			comprobarFolio(idIns);
			clearFolio(idIns);
		}
	}

	private void clearFolio(int id) {
		int fol = 0;
		int folioMax = 0;

			 GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
			 SQLiteDatabase db = gestion.getReadableDatabase();
			 String sql = "Select max(cast(numero_acta as int)) from Levantamiento where id_c_inspector1 = "+ id;
			 Cursor cursor = db.rawQuery(sql, null);
			 if (cursor.moveToFirst()) {
				 do {
					 fol = cursor.getInt(0);
				 } while (cursor.moveToNext());
			 }
			 cursor.close();
			 sql = "SELECT f_max FROM c_inspector where id_c_inspector = " + id;
			 cursor = db.rawQuery(sql, null);
			 if (cursor.moveToFirst()) {
				 do {
					 folioMax = cursor.getInt(0);
				 } while (cursor.moveToNext());
			 }
			 cursor.close();
		System.out.println(fol == folioMax);
		db.close();
			 if (fol == folioMax)
				 startService(new Intent(Descarga.this, ClearFolios.class));

	}

	private void comprobarFolio(final int id) {
		/*int folio=0;
		int max=0;
		int colchon = 0;
		int next=0;
        /*int next_min=0;
        int next_max=0;*/


			GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
			SQLiteDatabase db = gestion.getReadableDatabase();


			String sql = "SELECT max(cast (numero_acta as int)) FROM Levantamiento where id_c_inspector1 = " + id +" and infraccion=1";
		//String sql = "SELECT id_c_inspector1 FROM Levantamiento where numero_acta = '3469'";

		Log.v("sql", sql);
			Cursor cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {
					if(cursor.getString(0)==null){
						folio=0;
					}else{
						folio = cursor.getInt(0);
					}

				} while (cursor.moveToNext());
			}
			cursor.close();
		Log.v("folio actual tableta", String.valueOf(folio));
			sql = "SELECT  f_min FROM C_inspector where id_c_inspector= '" + id + "'  LIMIT 1";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						System.err.println(cursor.getColumnName(i) + " " + cursor.getString(i));
						if(cursor.getString(i)==null || cursor.getString(i)=="" || cursor.getString(i).isEmpty()){
							min=0;
						}else{
							min = Integer.parseInt(cursor.getString(i));
						}

					}
				} while (cursor.moveToNext());
			}
			cursor.close();
			sql = "SELECT f_max FROM c_inspector where id_c_inspector = " + id;
			Log.v("sql1", sql);
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {
					if(cursor.getString(0)==null || cursor.getString(0)==""){
						max=0;
					}else{
						max = cursor.getInt(0);
					}

				} while (cursor.moveToNext());
			}
			Log.v("folios", max + " " + folio);
			cursor.close();
			sql = "SELECT next_min FROM c_inspector where id_c_inspector = " + id;
			Log.v("sql1", sql);
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				do {
					if(cursor.getString(0)==null || cursor.getString(0)==""){
						next=0;
					}else{
						next = cursor.getInt(0);
					}

				} while (cursor.moveToNext());
			}
			Log.v("next", next + " ");
			cursor.close();
            db.close();

			colchon = max - folio;

		System.out.println("colchon : gg :"+ max +"-"+colchon);


		/*if(next == 0 ) {*/
			if (colchon <= 5) {
				if (folio >= 0) {
					MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Descarga.this)
							.setTitle(getResources().getString(R.string.actualizar_folios))
							.setMessage(getResources().getString(R.string.esta_suguro))
							.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									actualizaFolios(id);
									startService(new Intent(Descarga.this, UpdateFolio.class));
								}
							})
							.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {

								}
							});
					builder.create().show();
				}
			}
		/*}*/

	}

	private int actualizaFolios(int id) {
		ContentValues cv = new ContentValues();
		cv.put("next_min",1);
		GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		db.update("c_inspector",cv,"id_c_inspector = " + id,null);
		db.close();
		return 1;

		//startService(new Intent(MainActivity.this,UpdateFolio.class));
	}

	public void mostrarMsg() {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(Descarga.this);
		dialogo.setTitle("Message").setMessage("La versión de la aplicacion no esta actualizada").setPositiveButton("Ok", this);
		dialogo.create().show();
	}

	public int verificarActas() {
		GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT distinct(numero_acta)  FROM Levantamiento WHERE status = 'N'", null);
		//int	count = 0;

		return c.getCount();
	}
   public int actasPendientes(){
		int cantidad=0;

	   GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
	   SQLiteDatabase db = gestion.getReadableDatabase();
	   Cursor c = db.rawQuery("SELECT * FROM Levantamiento WHERE status = 'N'", null);

	   cantidad=c.getCount();



	   db.close();
	   c.close();





		return cantidad;
   }
	public void descargarLevantamiento() {
		GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Levantamiento WHERE status = 'N'", null);
		try {
			if (db != null) {
				count = 0;
				if (c.moveToFirst()) {
					do {
						//System.out.println("acta1: "+c.getInt(1));
						//System.out.println(buscarLevantamiento(c.getString(1))  + " l");
						//if (buscarLevantamiento(c.getString(1)) < 1) {
							if (Connection.inserta(c.getString(1), c.getString(2), c.getInt(3), c.getString(4), c.getInt(5), c.getString(6), c.getString(7), c.getDouble(8), c.getDouble(9),
									c.getString(10), c.getString(11), c.getString(12), c.getInt(13), c.getInt(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18),
									c.getString(19), c.getString(20), c.getString(21), c.getString(22), c.getString(23), c.getString(24), c.getString(25), c.getString(26), c.getString(27),
									c.getString(28), c.getString(29), c.getString(30), c.getString(31), c.getString(32), c.getString(33), c.getString(34), c.getString(35), c.getString(36),
									c.getString(37), c.getInt(38), c.getInt(39), c.getString(40), c.getString(41), c.getString(46), c.getString(47), c.getString(48), c.getString(49), c.getString(58), " " + c.getString(c.getColumnIndex("correo")), c.getString(c.getColumnIndex("l_alineamiento")), c.getString(c.getColumnIndex("l_construccion")), c.getString(c.getColumnIndex("entre_calle1")), c.getString(c.getColumnIndex("entre_calle2")), c.getString(c.getColumnIndex("responsable_obra")), c.getString(c.getColumnIndex("registro_responsable")), c.getInt(c.getColumnIndex("id_c_competencia")),
									c.getString(c.getColumnIndex("medida_seguridad")), c.getString(c.getColumnIndex("articulo_medida")), c.getString(c.getColumnIndex("motivo_orden")),
									c.getInt(c.getColumnIndex("id_c_inspector3")), c.getInt(c.getColumnIndex("id_c_inspector4")), c.getInt(c.getColumnIndex("id_c_inspector5")), c.getInt(c.getColumnIndex("id_c_inspector6")),
									c.getInt(c.getColumnIndex("id_c_competencia1")), c.getInt(c.getColumnIndex("id_c_competencia2")), c.getInt(c.getColumnIndex("id_c_competencia3")), c.getInt(c.getColumnIndex("id_c_competencia4")), c.getInt(c.getColumnIndex("id_c_competencia5")),
									c.getString(c.getColumnIndex("licencia_giro")), c.getString(c.getColumnIndex("actividad_giro")), c.getInt(c.getColumnIndex("axo_licencia")),
									c.getString(c.getColumnIndex("nombre_comercial")), c.getString(c.getColumnIndex("sector")), con, c.getString(c.getColumnIndex("peticion")), c.getString(c.getColumnIndex("nivel_economico")), c.getString(c.getColumnIndex("reincidencia")),c.getInt(c.getColumnIndex("tipo_cedula")),c.getString(c.getColumnIndex("folio_peticion")),c.getString(c.getColumnIndex("folio_apercibimiento")),c.getString(c.getColumnIndex("fecha_apercibimiento")),c.getString(c.getColumnIndex("numero_sellos")),c.getString(c.getColumnIndex("decomiso")),c.getString(c.getColumnIndex("folio_clausura")),c.getString(c.getColumnIndex("fecha_clausura")),/*"http://172.16.1.21/serverSQL/insertLevantamiento.php"*/ urlP+"insertLevantamientoas.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php" "http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/insertLevantamiento.php"*/).equalsIgnoreCase("S")) {

								System.out.println("si");
								ContentValues cv = new ContentValues();
								cv.put("status", "S");
								//System.out.println("acta2: "+c.getInt(1));
								System.out.println("acta3: "+c.getString(1));

								db.update("Levantamiento", cv, "numero_acta = '" + c.getString(1)+"'", null);
								count += 1;
								System.out.println("entro aqui levantamiento 1");
								//db.delete("Levantamiento", "numero_acta = '" + c.getString(1) +  "'", null);
							} else {
								System.out.println("entro aqui levantamiento 2");
								System.out.println("no");
								System.err.println(c.getColumnName(1) + " " + c.getString(1) + ",    " + c.getColumnName(2) + " " + c.getString(2) + ",    " + c.getColumnName(3) + " " + c.getInt(3) + ",    " + c.getColumnName(4) + " " + c.getString(4) + ",    " + c.getColumnName(5) + " " + c.getInt(5) + ",    " + c.getColumnName(6) + " " + c.getString(6) + ",    " + c.getColumnName(7) + " " + c.getString(7) + ",    " + c.getColumnName(8) + " " + c.getDouble(8) + ",    " + c.getColumnName(9) + " " + c.getDouble(9) + ",    " +
										c.getColumnName(10) + " " + c.getString(10) + ",    " + c.getColumnName(11) + " " + c.getString(11) + ",    " + c.getColumnName(12) + " " + c.getString(12) + ",    " + c.getColumnName(13) + " " + c.getInt(13) + ",    " + c.getColumnName(14) + " " + c.getInt(14) + ",    " + c.getColumnName(15) + " " + c.getString(15) + ",    " + c.getColumnName(16) + " " + c.getString(16) + ",    " + c.getColumnName(17) + " " + c.getString(17) + ",    " + c.getColumnName(18) + " " + c.getString(18) + ",    " +
										c.getColumnName(19) + " " + c.getString(19) + ",    " + c.getColumnName(20) + " " + c.getString(20) + ",    " + c.getColumnName(21) + " " + c.getString(21) + ",    " + c.getColumnName(22) + " " + c.getString(22) + ",    " + c.getColumnName(23) + " " + c.getString(23) + ",    " + c.getColumnName(24) + " " + c.getString(24) + ",    " + c.getColumnName(25) + " " + c.getString(25) + ",    " + c.getColumnName(26) + " " + c.getString(26) + ",    " + c.getColumnName(27) + " " + c.getString(27) + ",    " +
										c.getColumnName(28) + " " + c.getString(28) + ",    " + c.getColumnName(29) + " " + c.getString(29) + " ,   " + c.getColumnName(30) + " " + c.getString(30) + ",    " + c.getColumnName(31) + " " + c.getString(31) + ",    " + c.getColumnName(32) + " " + c.getString(32) + ",    " + c.getColumnName(33) + " " + c.getString(33) + ",    " + c.getColumnName(34) + " " + c.getString(34) + ",    " + c.getColumnName(35) + " " + c.getString(35) + ",    " + c.getColumnName(36) + " " + c.getString(36) + ",    " +
										c.getColumnName(37) + " " + c.getString(37) + ",    " + c.getColumnName(38) + " " + c.getInt(38) + ",    " + c.getColumnName(39) + " " + c.getInt(39) + ",    " + c.getColumnName(40) + " " + c.getString(40) + ",    " + c.getColumnName(41) + " " + c.getString(41) + ",    " + c.getColumnName(46) + " " + c.getString(46) + ",    " + c.getColumnName(47) + " " + c.getString(47) + ",    " + c.getColumnName(48) + " " + c.getString(48) + ",    " + c.getColumnName(49) + " " + c.getString(49) + ",    " + c.getColumnName(58) + " " + c.getString(58) + ", " + c.getColumnName(c.getColumnIndex("correo")) + " " + c.getString(c.getColumnIndex("correo")) + ",  " + c.getColumnName(c.getColumnIndex("l_alineamiento")) + " " + c.getString(c.getColumnIndex("l_alineamiento")) + ",  " + c.getColumnName(c.getColumnIndex("l_construccion")) + " " + c.getString(c.getColumnIndex("l_construccion")) +
										c.getColumnName(c.getColumnIndex("entre_calle1")) + " " + c.getString(c.getColumnIndex("entre_calle1")) + " " + c.getColumnName(c.getColumnIndex("entre_calle2")) + " " + c.getString(c.getColumnIndex("entre_calle2")) + " " + c.getColumnName(c.getColumnIndex("entre_calle2")) + " " + c.getString(c.getColumnIndex("responsable_obra")) + " " + c.getColumnName(c.getColumnIndex("registro_responsable")) + " " + c.getString(c.getColumnIndex("registro_responsable")) + " " + c.getColumnName(c.getColumnIndex("id_c_competencia")) + " " + c.getInt(c.getColumnIndex("id_c_competencia")));
							}
						//}
						//db.delete("Levantamiento", "numero_acta = '" + c.getString(1) +  "'", null);
					} while (c.moveToNext());
				}


			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		} finally {
			db.close();
			c.close();
		}
	}

	public void descargarDetalle() {
		GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Detalle_infraccion where estatus1 = 'N'", null);

		ContentValues cv = new ContentValues();
		cv.put("estatus1", "S");
		//db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
		try {
			if (db != null) {
				if (c.moveToFirst()) {
					do {
						//System.out.println((buscarDetalle(c.getString(2), c.getString(3), c.getString(4),c.getString(c.getColumnIndex("especificacion"))) == 0) + " d");
						System.out.println(c.getString(2) + " " + c.getString(3) + " " + c.getString(4) + " d");
						if (buscarDetalle(c.getString(2), c.getString(3), c.getString(4),c.getString(c.getColumnIndex("especificacion"))) == 0) {
							System.out.println("entro aqui detalle 1");
							id_l = idLe(c.getString(2));
							conn.insertDetalle(id_l, c.getString(2), Integer.parseInt(c.getString(3)), Float.parseFloat(c.getString(4)),c.getString(c.getColumnIndex("unidad")),c.getString(c.getColumnIndex("especificacion")),/*"http://172.16.1.21/serverSQL/insertDetalle.php"*/urlP+"insertDetalle.php"/*"http://pgt.no-ip.biz/serverSQL/insertDetalle.php"/"http://192.168.0.15/serverSQL/insertDetalle.php"*/);
							db.update("Detalle_infraccion", cv, "id_detalle_infraccion = " + c.getInt(0), null);
						} else {
							System.out.println("entro aqui detalle 2");
							//db.update("Detalle_infraccion", cv, "id_detalle_infraccion = " + c.getInt(0), null);
						}
						//db.delete("Detalle_infraccion", "id_detalle_infraccion = '" + c.getInt(0) + "'", null);
					} while (c.moveToNext());
				}

			}
		} catch (SQLiteException e) {
			Log.e("SQLITE", e.getMessage());
		} finally {
			db.close();
			c.close();
		}
	}

	public void descargarFotografia() {
		GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Fotografia where estatus1 = 'N'", null);

		ContentValues cv = new ContentValues();
		cv.put("estatus1", "S");
		//db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
		try {
			if (db != null) {
				System.out.println(c.getCount() + " F");
				if (c.moveToFirst()) {
					do {
						System.out.println(c.getString(2)+"-"+ c.getString(3)+"-"+ c.getString(4) + " f");

						System.out.println((buscarFoto(c.getString(2), c.getString(3), c.getString(4)) == 0) + " f");
						if (buscarFoto(c.getString(2), c.getString(3), c.getString(4)) == 0) {
							System.out.println("entro aqui 1");							id_l = idLe(c.getString(2));
							conn.insertFoto(id_l, c.getString(2), c.getString(3), c.getString(4), /*"http://172.16.1.21/serverSQL/insertFoto.php"*/urlP+"insertFoto.php"/*"http://pgt.no-ip.biz/serverSQL/insertFoto.php"/"http://192.168.0.15/serverSQL/insertFoto.php"*/);
							db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);
						} else {
							System.out.println("entro aqui 2");
							db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);
						}

					} while (c.moveToNext());
				}

			}
		} catch (SQLiteException e) {
			Log.e("descargar foto", e.getMessage());
		} finally {
			db.close();
			c.close();
		}
	}

	public int buscarLevantamiento(String numeroActa) {
		try {
			result = conn.search(numeroActa, urlP+"getNumeroActaL.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaL.php"/"http://192.168.0.15/serverSQL/getNumeroActaL.php"*/);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		//result = conn.search(numeroActa,"http://172.16.1.21/serverSQL/getNumeroActaL.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaL.php""http://192.168.0.11/serverSQL/getNumeroActaL.php"*/);
		if (result!=null) {
			try {
				this.jarray = new JSONArray(result);
				return jarray.length();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return 0;
	}

	public int buscarDetalle(String numeroActa, String id_c_infraccion, String cantidad,String especificacion) {
		try {
			System.out.println(urlP+"getNumeroActaD.php");
			result = conn.detalleInfraccion(numeroActa, id_c_infraccion, cantidad,especificacion, urlP+"getNumeroActaD.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaD.php"/"http://192.168.0.15/serverSQL/getNumeroActaD.php"*/);
		}catch (Exception e){
				System.err.println(e.getMessage());
			}
		//result = conn.detalleInfraccion(numeroActa, id_c_infraccion, cantidad, "http://172.16.1.21/serverSQL/getNumeroActaD.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaD.php""http://192.168.0.11/serverSQL/getNumeroActaD.php"*/);
		System.out.println(result);
		if (result!=null) {
			try {
				this.jarray = new JSONArray(result);
				System.err.println("aqui");
				return jarray.length();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return 0;
	}

	public int idLe(String numero_acta) {
		try {
			res = conn.idLevantamiento(urlP+"getIdLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getIdLevantamiento.php"/"http://192.168.0.15/serverSQL/getIdLevantamiento.php"*/, numero_acta);
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		//res = conn.idLevantamiento("http://172.16.1.21/serverSQL/getIdLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getIdLevantamiento.php""http://192.168.0.11/serverSQL/getIdLevantamiento.php"*/, numero_acta);
		int id = 0;
		if (res!=null) {

			try {
				this.jarray = new JSONArray(res);
				for (int i = 0; i < jarray.length(); i++) {
					this.json_data = this.jarray.getJSONObject(i);
					id = json_data.getInt("id_levantamiento");
					return  id;
				}
			} catch (JSONException e) {
				Log.e("idl", e.getMessage());
			}
		}
		return 0;
	}

	public int buscarFoto(String numeroActa, String archivo, String descripcion) {
		try {
			result = conn.fotografia(numeroActa, archivo, descripcion, urlP+"getNumeroActaF.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaF.php"/"http://192.168.0.15/serverSQL/getNumeroActaF.php"*/);
		}catch (Exception e) {
			System.err.println(e.getMessage());
			}
		//result = conn.fotografia(numeroActa, archivo, descripcion, "http://172.16.1.21/serverSQL/getNumeroActaF.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaF.php""http://192.168.0.11/serverSQL/getNumeroActaF.php"*/);

		if (result!=null) {
			try {
				this.jarray = new JSONArray(result);
				return jarray.length();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
    public String getactasrango(String f1, String f2, String idinspector){
		String numeroacta = "chetos";

		return numeroacta;
	}
	public void fotografias() {
		boolean res;
		countF = 0;
		GestionBD gestionarBD = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		String s, dir, ar;
		dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
		File f;
		try {
			if (db != null) {
				Cursor c = db.rawQuery("SELECT * FROM Fotografia where estatus='N'", null);
				if (c.moveToFirst()) {
					foto.clear();
					archivo.clear();
					do {
						foto.add(c.getString(2));
						archivo.add(c.getString(3));
						s = c.getString(2).replace("/", "_");
						ar = c.getString(3);
						Log.i("Dato", s + " " + ar);
						f = new File(dir + s + "/" + ar);
						System.out.println(f.exists());
						if (f.exists()) {
							Log.i("Mes", "if exist");
							File file = new File(f.getAbsolutePath());
							MultipartEntity mpEntity = new MultipartEntity();
							ContentBody foto = new FileBody(file, "image/jpeg");

							mpEntity.addPart("fotoUp", foto);
							mpEntity.addPart("foto", new StringBody(s));

							JSONObject json = jsonParser.subirImage(urlP+"pruebaI.php", "POST", mpEntity);

							//JSONObject json = jsonParser.subirImage("http://192.168.0.16:8080/sitios/pruebas/pruebaI.php", "POST", mpEntity);

							int success = json.getInt("status");
							System.out.println(success + " success");

							ContentValues cv = new ContentValues();
							String sql;

							if (success == 1) {
								System.out.println("envio movio");
								cv.put("estatus", "S");
								//sql = "update Fotografia set ";
								db.update("Fotografia", cv, " id_fotografia = " + c.getInt(0), null);
							} else if (success == 0)
								System.out.println("envio no movio");
							else if (success == 3) {
								System.out.println("existe");
								cv.put("estatus", "S");
								db.update("Fotografia", cv, " id_fotografia = " + c.getInt(0), null);
							} else
								System.out.println("no envio f");

						}
						//db.delete("Fotografia", c.getColumnName(0) + " = '" + c.getString(0) + "'", null);
					} while (c.moveToNext());
					c.close();
					Thread.sleep(4000);
					if (countF > 0) {
						try {

							ArrayList<NameValuePair> carga = new ArrayList<NameValuePair>();

							carga.add(new BasicNameValuePair("tableta", config));
							carga.add(new BasicNameValuePair("registros", String.valueOf(0)));
							carga.add(new BasicNameValuePair("fotos", String.valueOf(countF)));

							//JSONObject json = jsonParser.realizarHttpRequest("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/insertCarga.php", "POST", carga);

							JSONObject json = jsonParser.realizarHttpRequest(urlP+"insertCarga.php", "POST", carga);

							int estatus = json.getInt("status");

							if (estatus == 1)
								System.err.println("inserto");
							else
								System.err.println("no inserto");

						} catch (JSONException e) {
							System.out.println(e.getMessage() + " mm");
						}
					}
				}

			}
		} catch (Exception e) {

		}
	}

	public void foto() {
		countF = 0;
		GestionBD gestionarBD = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		String s, dir, ar;
		dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
		File f;
		FTPFile[] fil;
		try {
			if (db != null) {
				Cursor c = db.rawQuery("SELECT * FROM Fotografia", null);
				if (c.moveToFirst()) {
					foto.clear();
					archivo.clear();
					do {
						foto.add(c.getString(2));
						archivo.add(c.getString(3));
						s = c.getString(2).replace("/", "_");
						ar = c.getString(3);
						Log.i("Dato", s + " " + ar);
						f = new File(dir + s + "/" + ar);
						System.out.println(f.exists());
						if (f.exists()) {
							Log.i("Mes", "if exist");
							fil = ftp(s);
							if (fil.length > 0) {
								Log.i("Mes", "fil not null");
								for (int i = 0; i < fil.length; i++) {
									Log.i("Mes", "for");
									Log.i("Parametros", " s " + s + " ar " + ar);
									Log.i("Nombre", fil[i].getName() + " " + ar);
									Log.i("tama�o", fil[i].getSize() + " " + f.length());
									if (fil[i].getName().equalsIgnoreCase(ar)) {
										Log.i("Mes", "if son iguales");
										if (fil[i].getSize() != f.length()) {
											Log.i("Mes", "if diferente tama�o");
											subirFoto(s, ar, c.getInt(0));
										}
									} else {
										Log.i("Mes", "else iguales name");
										subirFoto(s, ar, c.getInt(0));
									}
								}
							} else {
								Log.i("Mes", "else nulo");
								subirFoto(s, ar, c.getInt(0));
							}
						}
						//db.delete("Fotografia", c.getColumnName(0) + " = '" + c.getString(0) + "'", null);
					} while (c.moveToNext());
					c.close();
					Thread.sleep(4000);
					System.out.println(countF + " qqqqq");
					if (countF > 0) {
						try {

							ArrayList<NameValuePair> carga = new ArrayList<NameValuePair>();

							carga.add(new BasicNameValuePair("tableta", config));
							carga.add(new BasicNameValuePair("registros", String.valueOf(0)));
							carga.add(new BasicNameValuePair("fotos", String.valueOf(countF)));

							//JSONObject json = jsonParser.realizarHttpRequest("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/insertCarga.php", "POST", carga);

							JSONObject json = jsonParser.realizarHttpRequest(urlP+"insertCarga.php", "POST", carga);

							int estatus = json.getInt("status");

							if (estatus == 1)
								System.err.println("inserto");
							else
								System.err.println("no inserto");

						} catch (JSONException e) {
							System.out.println(e.getMessage() + " mm");
						}
					}
				}

			}
		} catch (Exception e) {
			Log.e("foto", e.getMessage() + " ");
		} finally {
			db.close();
			//eliminarCarpetas();
		}
	}

	public FTPFile[] ftp(String ruta) {
		client = null;
		FTPFile[] files = null;
		try {
			client = new FTPClient();
			//client.connect(InetAddress.getByName(mFTP));
			client.connect(InetAddress.getByName(mFTP), 21);
			System.out.println("logueo " + client.login("pgm", "pgm2012"));
			//System.out.println("logueo "+client.login("prueba", "1234"));
			System.out.println(InetAddress.getByName(mFTP));
			System.out.println(client.printWorkingDirectory());
			System.out.println("Conectado: " + client.isConnected() + " " + client.printWorkingDirectory());
			System.out.println(client.mkd(ruta));
			if (client.changeWorkingDirectory("/" + ruta)) {

				files = client.listFiles();
				System.out.println(client.isConnected());
				for (FTPFile arch : files) {
					System.out.println(arch.toString() + " t " + arch.getSize());
				}
			}
			System.out.println(client.logout());
			client.disconnect();
			System.out.println("Conectado: " + client.isConnected() + "");
		} catch (IOException e) {
			System.err.println("Error" + e.getMessage());
		}
		System.out.println(files.length);
		return files;
	}

	public void subirFoto(String dir, String name, final int idFoto) {
		this.dir = dir;
		this.arch = name;
		/*pd = new ProgressDialog(Descarga.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Espere...");
		pd.setTitle("Subiendo Fotograf�a(s)");
		pd.setCancelable(true);
		pd.show();*/
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				FTPClient client = null;
				try {
					client = new FTPClient();
					client.connect(InetAddress.getByName(mFTP));
					/*System.out.println(client.login("pgm", "pgm2012"));
					if (client.login("pgm", "pgm2012")) {*/
					System.out.println(client.login("prueba", "1234"));
					if (client.login("prueba", "1234")) {
						Log.i("Status", client.isConnected() + "");
						Log.i("Dir", client.printWorkingDirectory());
						Log.i("Crear dir", client.mkd(Descarga.this.dir) + "");
						client.changeWorkingDirectory("/" + Descarga.this.dir);
						client.printWorkingDirectory();

						if (new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + Descarga.this.dir + "/" + arch).exists()) {

							client.setFileType(FTP.BINARY_FILE_TYPE);
							BufferedInputStream buffIn = null;
							buffIn = new BufferedInputStream(new FileInputStream(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + Descarga.this.dir + "/" + arch));
							client.enterLocalPassiveMode();

							if (client.storeFile(Descarga.this.arch, buffIn)) {
								countF += 1;
							}

						}

						client.logout();
						client.disconnect();
						client.isConnected();
						eliminarFoto(idFoto);
					}
					//pd.dismiss();
				} catch (IOException e) {
					Log.e("Error en", e.getMessage());
					//pd.dismiss();
				} finally {
					//pd.dismiss();
				}
			}
		});
		t.start();
	}

	public void eliminarCarpetas() {
		dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
		File archivo;
		try {
			for (int i = 0; i < foto.size(); i++) {
				arch = foto.get(i).replace("/", "_");
				File f = new File(dir + arch);
				Log.i("dir", f.getAbsolutePath());
				Log.i("dir", dir + arch);
				if (f.exists()) {
					archivo = new File(f.getAbsolutePath() + "/" + arch + ".txt");
					Log.i("dir", archivo.getAbsolutePath());
					if (archivo.exists())
						Log.i("borrar txt", archivo.delete() + "");
					for (int j = 0; j < this.archivo.size(); j++) {
						archivo = new File(f.getAbsolutePath() + "/" + this.archivo.get(j));
						Log.i("dir", archivo.getAbsolutePath());
						if (archivo.exists())
							Log.i("borrar archivo", archivo.delete() + "");
					}
					Log.i("borrar ", f.delete() + "");
				}

			}
		} catch (Exception e) {
			Log.e("folder", e.getMessage());
		}
	}

	public String verificar() {
		StringBuilder sb = new StringBuilder();
		GestionBD gestionar = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		Cursor l = db.rawQuery("SELECT * FROM Levantamiento", null);
		Cursor d = db.rawQuery("SELECT * FROM Detalle_infraccion", null);
		if (l.getCount() == 0)
			sb.append(" Levantamientos");
		if (d.getCount() == 0)
			sb.append(" Detalle infraccion");
		db.close();
		return sb.toString();
	}

	public String VerificarFoto() {
		StringBuilder sb = new StringBuilder();
		GestionBD gestionar = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		Cursor F = db.rawQuery("SELECT * FROM Fotografia where estatus = 'N'", null);
		if (F.getCount() == 0)
			sb.append(" Fotografia");
		db.close();
		return sb.toString();
	}

	public void eliminarFoto(int idfoto) {
		GestionBD gestionar = new GestionBD(this, "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		db.delete("Fotografia", "id_fotografia = '" + idfoto + "'", null);
		db.close();
	}

	public void tablas() {
		result = conn.search("http://172.20.246.89:8080/serverSQL/getTablas.php");
		try {
			this.jarray = new JSONArray(result);
			for (int i = 0; i < jarray.length(); i++) {
				this.json_data = this.jarray.getJSONObject(i);
				if (json_data.getString("Tabla").substring(0, 2).equalsIgnoreCase("C_")) {
					if (aux == 0) {
						sb.append("Create table " + json_data.getString("Tabla") + "(");
					} else {
						sb.append("); Create table " + json_data.getString("Tabla") + "(");
					}
					aux++;
					result = conn.buscarTabla(json_data.getString("Tabla"), "http://172.20.246.89:8080/serverSQL/getCunsultaTabla.php");
					this.jsonA = new JSONArray(result);
					for (int j = 0; j < jsonA.length(); j++) {
						if (j != 0) {
							sb.append(",");
						}
						this.jObject = jsonA.getJSONObject(j);
						sb.append(jObject.getString("columna") + " " + jObject.getString("Tipo"));
					}
				}
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public boolean comprobar() {
		return c.insetarRegistros("http://pgt.no-ip.biz/serverSQL/getC_Direccion.php", "C_Direccion");
	}

	public int sicrof(String catalogo, String url,String s) {
		GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getWritableDatabase();
		System.out.println("SELECT COUNT(*) FROM " + catalogo);
		Cursor c2;
		if(catalogo.equalsIgnoreCase("c_infraccion"))
			c2 = db.rawQuery("SELECT * FROM " + catalogo + " where vigente = 'S'", null);
		else
			c2 = db.rawQuery("SELECT * FROM " + catalogo, null);
		System.out.println(c2.getCount());

		if (c.validar1(url, catalogo,s) == c2.getCount()) {
			System.out.println("entro");
			return 0;
		} else {
			System.out.println("no entro");
			return 0;
		}
	}
	public String fechasicronizacion(String catalogo){
		String fecha=" ";
		GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getWritableDatabase();
		System.out.println("select max(fecha) fecha_sincronizacion from "+catalogo+" where id_c_inspector1 =" + MainActivity.id_ins_sesion);
		Cursor c2;


		c2=db.rawQuery("select max(fecha) fecha_sincronizacion from "+catalogo+" where id_c_inspector1 =" + MainActivity.id_ins_sesion,null);
		if (c2.moveToFirst()) {

			do {
             fecha=c2.getString(c2.getColumnIndex("fecha_sincronizacion"));

			} while (c2.moveToNext());


		}
		return fecha;
	}
    String mensaje="";
	public void insertar(String values_cr) {

		Log.e("fire", "insertar: pasa1");
		int i = 0;
		final String url = urlP+"contarreglones.php";
		// consultar cantidad de renglones de comercio y renglones de construccion
		//if (c.validar3("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getrenglonvs_InspM2.php", 0) >= c.validar2(url2, "parametros", 14) && c.validar3("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getcountlicenciasr.php", 0) >= c.validar2(url2, "parametros", 15)) {
		//GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		//SQLiteDatabase db = gestion.getWritableDatabase();
		Log.e("fire", "insertar: "+urlP+"getC_Direccion.php");
			if (!c.search(urlP+"getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
				Log.e("fire", "insertar: pasa2");
				if (!c.search(urlP+"getC_Direccion.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_Direccion");
					System.out.println("degueando ando");
					c.insetarRegistros(urlP+"getC_Direccion.php", "C_Direccion");
					x += sicrof("c_direccion", url,"0");
					if (x>0){
						mensaje=mensaje+" c_direccion";
					}
				}

				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getc_insepctor.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_inspector");
					c.insetarRegistros(urlP+"getc_insepctor.php", "C_inspector");
					x += sicrof("C_inspector", url,"1");
					if (x>0){
						mensaje=mensaje+" C_inspector";
					}
				}
				System.out.println(x);
				mProgressBar.setProgress(i);
				i++;
				if (!c.search(urlP+"getC_infraccion.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_infraccion");
					c.insetarRegistros(urlP+"getC_infraccion.php", "C_infraccion");
					x += sicrof("C_infraccion", url,"1");
					if (x>0){
						mensaje+=" C_infraccion";
					}
				}
				System.out.println( "cantidad: "+x);
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getc_dia_no_habil.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_dia_no_habil");
					c.insetarRegistros(urlP+"getc_dia_no_habil.php", "C_dia_no_habil");
					x += sicrof("C_dia_no_habil", url,"0");
					if (x>0){
						mensaje=mensaje+" C_dia_no_habil";
					}
				}
				mProgressBar.setProgress(i);
				i++;
				if (!c.search(urlP+"getC_ley_ingesos.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_ley_ingresos");
					c.insetarRegistros(urlP+"getC_ley_ingesos.php", "C_ley_ingresos");
					x += sicrof("C_ley_ingresos", url,"0");
					if (x>0){
						mensaje=mensaje+" C_ley_ingresos";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getc_zonas.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_zonas");
					c.insetarRegistros(urlP+"getc_zonas.php", "C_zonas");
					x += sicrof("C_zonas", url,"0");
					if (x>0){
						mensaje=mensaje+" C_zonas";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getC_fraccionamiento.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_fraccionamiento");
					c.insetarRegistros(urlP+"getC_fraccionamiento.php", "C_fraccionamiento");
					x += sicrof("C_fraccionamiento", url,"0");
					if (x>0){
						mensaje=mensaje+" C_fraccionamiento";
					}
				}

				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getC_poblacion.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_poblacion");
					c.insetarRegistros(urlP+"getC_poblacion.php", "C_poblacion");
					x += sicrof("C_poblacion", url,"0");
					if (x>0){
						mensaje=mensaje+" C_poblacion";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getC_visitado_identifica.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_visitado_identifica");
					c.insetarRegistros(urlP+"getC_visitado_identifica.php", "C_visitado_identifica");
					x += sicrof("C_visitado_identifica", url,"0");
					if (x>0){
						mensaje=mensaje+" C_visitado_identifica";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getC_visitado_manifiesta.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_visitado_manifiesta");
					c.insetarRegistros(urlP+"getC_visitado_manifiesta.php", "C_visitado_manifiesta");
					x += sicrof("C_visitado_manifiesta", url,"0");
					if (x>0){
						mensaje=mensaje+" C_visitado_manifiesta";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getC_uso_suelo.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_uso_suelo");
					c.insetarRegistros(urlP+"getC_uso_suelo.php", "C_uso_suelo");
					x += sicrof("C_uso_suelo", url,"0");
					if (x>0){
						mensaje=mensaje+" C_uso_suelo";
					}
				}

				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getC_ordenamiento.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("C_ordenamiento");
					c.insetarRegistros(urlP+"getC_ordenamiento.php", "C_ordenamiento");
					x += sicrof("C_ordenamiento", url,"0");
					if (x>0){
						mensaje=mensaje+" C_ordenamiento";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getTabletas.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("c_tabletas");
					c.insetarRegistros(urlP+"getTabletas.php", "c_tabletas");
					x += sicrof("c_tabletas", url,"1");
					if (x>0){
						mensaje=mensaje+" c_tabletas";
					}
				}
				mProgressBar.setProgress(i);

				i++;

				if (!c.search(urlP+"getc_medida.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("c_medida_precautoria");
					c.insetarRegistros(urlP+"getc_medida.php", "c_medida_precautoria");
					x += sicrof("c_medida_precautoria", url,"0");
					if (x>0){
						mensaje=mensaje+" c_medida_precautoria";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"getCPeticion.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("c_peticion");
					c.insetarRegistros(urlP+"getCPeticion.php", "c_peticion");
					x += sicrof("c_peticion", url,"0");
					if (x>0){
						mensaje=mensaje+" c_peticion";
					}
				}
				mProgressBar.setProgress(i);

				i++;
				if (!c.search(urlP+"get_c_medida_tabla.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("c_medida_tabla");
					c.insetarRegistros(urlP+"get_c_medida_tabla.php", "c_medida_tabla");
					x += sicrof("c_medida_tabla", url,"0");
					if (x>0){
						mensaje=mensaje+" c_medida_tabla";
					}
				}
				mProgressBar.setProgress(i);

				i++;

				if (!c.search(urlP+"get_c_medida_tabla_fraccion.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("c_medida_tabla_fraccion");
					c.insetarRegistros(urlP+"get_c_medida_tabla_fraccion.php", "c_medida_tabla_fraccion");
					x += sicrof("c_medida_tabla_fraccion", url,"0");
					if (x>0){
						mensaje=mensaje+" c_medida_tabla_fraccion";
					}
				}
				if (!c.search(urlP+"get_concepto_ov.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("concepto_ov");
					c.insetarRegistros(urlP+"get_concepto_ov.php", "concepto_ov");
					x += sicrof("concepto_ov", url,"0");
					if (x>0){
						mensaje=mensaje+" concepto_ov";
					}
				}
				if (!c.search(urlP+"getcmeconstitui.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("c_me_constitui");
					c.insetarRegistros(urlP+"getcmeconstitui.php", "c_me_constitui");
					x += sicrof("c_me_constitui", url,"0");
					if (x>0){
						mensaje=mensaje+" c_me_constitui";
					}
				}
				if(!c.search(urlP+"getCgiro.php").trim().equalsIgnoreCase("null")){
					eliminaRegistros("c_giro2");
					c.insetarRegistros(urlP+"getCgiro.php", "c_giro2");
					//x += sicrof("c_giro", url,"0");
				/*	if(x>0){
						mensaje=mensaje+"c_giro";
					}*/
				}
				mProgressBar.setProgress(i);

				i++;

				if (!c.search(urlP+"getCPeticion.php").trim().equalsIgnoreCase("null")) {
					eliminaRegistros("vs_InspM2");
					int x1;
					Log.e("ENTRO", "insertar: LICENCIAS CONSTRUCCION" );
					c.insetarRegistros("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/get_vs_InspM22.php", "vs_InspM2");
					//x1 = c.validar2("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getparametros2.php", "parametros");


				mProgressBar.setProgress(i);
				i++;




				}
				msj = "Datos Actualizados";
			}
			else
				msj = "No se pudo conectar con el servidor";
	}



	public void actualiza() {
		System.out.println(fechasicro);
		int i = 0;
		final String url = urlP+"contarreglones.php";
		final String url2=urlP+"sicronizacionD.php";
		GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getWritableDatabase();
		if (!c.search(urlP+"getC_Direccion.php").trim().equalsIgnoreCase("null")) {
			if (!c.search(urlP+"getc_insepctor.php").trim().equalsIgnoreCase("null")) {


				if(c.cambiosT("C_inspector",fechasicro,url2)>0){
           	Log.e("entro en cambios","Yes C_inspector");
			   x = sicrof("C_inspector", url,"1");
			   if (x<=0){

				   Log.e("no actualizo","Yes");
			   }else{
				   mensaje=mensaje+" C_inspector";
				   eliminaRegistros("C_inspector");
				   c.insetarRegistros(urlP+"getc_insepctor.php"/*"http://pgt.no-ip.biz/serverSQL/getc_insepctor.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "C_inspector");
				   Log.e("actualizo","Yes");
			   }
		   }

			}
			System.out.println(x);
			mProgressBar.setProgress(i);
			i++;
			if (!c.search(urlP+"getC_Direccion.php").trim().equalsIgnoreCase("null")) {

				if(c.cambiosT("C_infraccion",fechasicro,url2)>0) {
					Log.e("entro en cambios","Yes C_infraccion");
					x = sicrof("C_infraccion", url, "1");
					if (x <= 0) {

						Log.e("no actualizo","Yes");
					}else{
						mensaje = mensaje + " C_infraccion";
						eliminaRegistros("C_infraccion");
						c.insetarRegistros(urlP+"getC_infraccion.php"/*"http://pgt.no-ip.biz/serverSQL/getC_infraccion.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "C_infraccion");
						Log.e(" actualizo","Yes");
					}
				}
			}
			System.out.println(x);
			mProgressBar.setProgress(i);
			i++;
			if (!c.search(urlP+"getc_zonas.php").trim().equalsIgnoreCase("null")) {

				if(c.cambiosT("C_zonas",fechasicro,url2)>0) {
					Log.e("entro en cambios","Yes C_zonas");
					x = sicrof("C_zonas", url, "0");
					if (x <= 0) {

						Log.e("no actualizo","Yes");
					} else {
						mensaje = mensaje + " C_zonas";
						eliminaRegistros("C_zonas");
						c.insetarRegistros(urlP+"getc_zonas.php"/*"http://pgt.no-ip.biz/serverSQL/getc_zonas.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "C_zonas");
						Log.e(" actualizo","Yes");
					}
				}
			}
			mProgressBar.setProgress(i);
			i++;
			if (!c.search(urlP+"getC_fraccionamiento.php").trim().equalsIgnoreCase("null")) {

				if(c.cambiosT("C_fraccionamiento",fechasicro,url2)>0) {
					Log.e("entro en cambios","Yes C_fraccionamiento");
					x = sicrof("C_fraccionamiento", url, "0");
					if (x <= 0) {

						Log.e("no actualizo","Yes");
					} else {
						mensaje = mensaje + " C_fraccionamiento";
						eliminaRegistros("C_fraccionamiento");
						c.insetarRegistros(urlP+"getC_fraccionamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_fraccionamiento.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "C_fraccionamiento");
						Log.e(" actualizo","Yes");
					}
				}
			}
			mProgressBar.setProgress(i);
			i++;
			if (!c.search(urlP+"getC_ordenamiento.php").trim().equalsIgnoreCase("null")) {
				if(c.cambiosT("C_ordenamiento",fechasicro,url2)>0) {
					Log.e("entro en cambios","Yes C_ordenamiento");
					x = sicrof("C_ordenamiento", url, "0");
					if (x <= 0) {

						Log.e("no actualizo","Yes");
					} else {
						mensaje = mensaje + " C_ordenamiento";
						eliminaRegistros("C_ordenamiento");
						c.insetarRegistros(urlP+"getC_ordenamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "C_ordenamiento");
						Log.e(" actualizo","Yes");
					}
				}
			}
			mProgressBar.setProgress(i);
			i++;
			if (!c.search(urlP+"getTabletas.php").trim().equalsIgnoreCase("null")) {
				if(c.cambiosT("c_tabletas",fechasicro,url2)>0) {
					Log.e("entro en cambios","Yes c_tabletas");
					x = sicrof("c_tabletas", url, "1");
					if (x <= 0) {

						Log.e("no actualizo","Yes");
					} else {
						mensaje = mensaje + " c_tabletas";
						eliminaRegistros("c_tabletas");
						c.insetarRegistros(urlP+"getTabletas.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "c_tabletas");
						Log.e(" actualizo","Yes");
					}
				}
			}
			mProgressBar.setProgress(i);
			i++;
			if (!c.search(urlP+"getc_medida.php").trim().equalsIgnoreCase("null")) {

				if(c.cambiosT("c_medida_precautoria",fechasicro,url2)>0) {
					Log.e("entro en cambios","Yes c_medida_precautoria");
					x = sicrof("c_medida_precautoria", url, "0");
					if (x <= 0) {

						Log.e("no actualizo","Yes");
					} else {
						mensaje = mensaje + " c_medida_precautoria";
						eliminaRegistros("c_medida_precautoria");
						c.insetarRegistros(urlP+"getc_medida.php"/*"http://pgt.no-ip.biz/serverSQL/getC_visitado_manifiesta.php""http://192.168.1.87/serverSQL/getC_Direccion.php"*/, "c_medida_precautoria");
						Log.e(" actualizo","Yes");
					}
				}
			}
			mProgressBar.setProgress(i);
			i++;
			String fechaR = "",fechaC = "";
			String fechas [];
			boolean br,bc;
			Calendar cal = Calendar.getInstance();
			Cursor cursor;
			cursor = db.rawQuery("SELECT fechaA FROM v_LicenciasReglamentos order by fechaA desc limit 1",null);
			/*if(cursor.moveToFirst())
				fechaR = cursor.getString(0);*/
			cursor = db.rawQuery("SELECT fechaA FROM vs_InspM2 order by fechaA desc limit 1",null);
			if(cursor.moveToFirst())
				fechaC = cursor.getString(0);
			System.err.println(fechaC + " fechaC " + fechaR + " fechaR");
			//fechas = fechaR.split("/");
			//fechaR = (Integer.parseInt(fechas[0]) + 1) + "/" + fechas[1] + "/" + fechas[2];
			//fechas = fechaC.split("/");
			//fechaC = (Integer.parseInt(fechas[0]) + 1) + "/" + fechas[1] + "/" + fechas[2];
			//System.err.println(fechaC + " fechaC " + fechaR + " fechaR");
			//if(fechaC.equalsIgnoreCase(cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR)))
			//	bc = false;
			//else
			//	bc = true;
			/*if(fechaR.equalsIgnoreCase(cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR)))
				br = false;
			else
				br = true;*/
			//reglamentos
			/*if(!br) {
				if (!c.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getCPeticion.php").trim().equalsIgnoreCase("null")) {
					int x1;
					c.insetarRegistros("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getLicenciasReglamentos.php", "v_LicenciasReglamentos", fechaR);
					x1 = c.validar2("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getparametros.php", "parametros");
					Cursor c2 = db.rawQuery("SELECT * FROM " + "vs_InspM2", null);
					z = c2.getCount();
					if (x1 > z) {
						x++;
						mensaje += mensaje + " vs_InspM2 " + c.validar2("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getparametros2.php", "parametros");
					}
				}
				mProgressBar.setProgress(i);
				i++;
			}*/
			//Construccion
			/*if(!bc) {
				if (!c.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getCPeticion.php").trim().equalsIgnoreCase("null")) {
					int x1;
					c.insetarRegistros("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getLicenciasConstruccion.php", "vs_InspM2", fechaC);
					x1 = c.validar2("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getparametros2.php", "parametros");
					Cursor c2 = db.rawQuery("SELECT * FROM " + "vs_InspM2", null);
					z = c2.getCount();
					if (x1 > z) {
						x++;
						mensaje += mensaje + " vs_InspM2 " + c.validar2("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getparametros2.php", "parametros");
					}
				}
				mProgressBar.setProgress(i);
				i++;
			}
			msj = "Datos Actualizados";*/
		}
		else
			msj = "No se pudo conectar con el servidor";
	}
	public static void actualiza2(Connection c2, Context f) {
		//int i = 0;
		//final String url = "http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/contarreglones.php";
		validarM = sp.getInt("modo",0);
		if(validarM==1) {
			//modoT.setChecked(true);

			//titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
			urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";
		}else {
			//modoT.setChecked(false);

			//titlem.setText("");
			urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
		}

		GestionBD gestion = new GestionBD(f, "inspeccion", null, 1);
		SQLiteDatabase db = gestion.getWritableDatabase();


		if (!c2.search("getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
			if (!c2.search(urlP+"getc_insepctor.php").trim().equalsIgnoreCase("null")) {
				ArrayList<NameValuePair> inspector = new ArrayList<>();
             if(InfraccionesActivity.id_inspectorQ!=0) {

				 inspector.add(new BasicNameValuePair("numero", String.valueOf(InfraccionesActivity.id_inspectorQ)));
			 }
             if(InfraccionesActivityTecnica.id_inspectorQ!=0){
				 inspector.add(new BasicNameValuePair("numero", String.valueOf(InfraccionesActivityTecnica.id_inspectorQ)));
			 }
				 //inspector.add(new BasicNameValuePair("numero", String.valueOf(InfraccionesActivity.id_inspectorQ)));
				 JSONArray jsonArray = jParser.realizarHttpRequest1(urlP+"getfoliolast.php", "POST",inspector);



				eliminaRegistros2("C_inspector",f);
				c2.insetarRegistros(urlP+"getc_insepctor.php", "C_inspector");

			}
		}


	}
	public  void eliminaRegistros(String tabla) {
		GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		db.beginTransaction();
		try {
			Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);
			if (c.moveToFirst()) {
				do {
					db.delete(tabla, "1", null);
				} while (c.moveToNext());
			}
			db.setTransactionSuccessful();
			c.close();
		} catch (SQLiteException e) {
			//Log.e("SQLiteException ", e.getMessage());
		}
		finally {
			db.endTransaction();
			db.close();
		}
	}
	public static void eliminaRegistros2(String tabla,Context f) {
		GestionBD gestion = new GestionBD(f, "inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		db.beginTransaction();
		try {
			Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);
			if (c.moveToFirst()) {
				do {
					db.delete(tabla, "1", null);
				} while (c.moveToNext());
			}
			db.setTransactionSuccessful();
			c.close();
		} catch (SQLiteException e) {
			//Log.e("SQLiteException ", e.getMessage());
		}
		finally {
			db.endTransaction();
			db.close();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			onDestroy();
			//Intent intent = new Intent();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.finish();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		bundle.putString("usuario", us.trim());
		bundle.putInt("id", id);
		bundle.putString("direccion", direccion);
		switch (v.getId()) {
			case R.id.btnConfig:
				bundle  = new Bundle();
				bundle.putString("usuario", us);
				bundle.putInt("id", id);
				bundle.putString("direccion", direccion);
				//mostrarVentana();
				startActivity(new Intent(getApplicationContext(), Configurar.class).putExtras(bundle));
				onDestroy();
				break;

			case R.id.btnDescargaApp:
				String version = String.valueOf(ve);
				new Update(Descarga.this).execute(version);
				break;

			case R.id.btnConsultarLicencia:
				startActivity(new Intent(getApplicationContext(), ConsultarLicencias.class).putExtras(bundle));
				break;

			case R.id.btnConsultar:

				startActivity(new Intent(getApplicationContext(), ConsultarInfracciones.class).putExtras(bundle));
				break;

			case R.id.btnReimprimir1:
				startActivity(new Intent(getApplicationContext(), ReimprimirActivity.class).putExtras(bundle));
				break;
			case R.id.btnConsultarLicenciaC:
				startActivity(new Intent(getApplicationContext(),ConsultarLicenciaConstruccion.class));
				break;

			case R.id.btnLicencias:
				startActivity(new Intent(getApplicationContext(),Licencias.class));
				break;

			default:
				break;
		}

		/*System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + NOMBRE_DIRECTORIO + "/" + NOMBRE_DOCUMENTO);

		Document documento = new Document();

		try {

			// Creamos el fichero con el nombre que deseemos.
			File f = crearFichero(NOMBRE_DOCUMENTO);

			// Creamos el flujo de datos de salida para el fichero donde
			// guardaremos el pdf.
			FileOutputStream ficheroPdf = new FileOutputStream(
					f.getAbsolutePath());

			// Asociamos el flujo que acabamos de crear al documento.
			PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

			// Incluimos el p�e de p�gina y una cabecera
			HeaderFooter cabecera = new HeaderFooter(new Phrase(
					"Esta es mi cabecera"), false);
			HeaderFooter pie = new HeaderFooter(new Phrase(
					"Este es mi pie de p�gina"), false);

			documento.setHeader(cabecera);
			documento.setFooter(pie);

			// Abrimos el documento.
			documento.open();

			// A�adimos un t�tulo con la fuente por defecto.
			documento.add(new Paragraph("T�tulo 1"));

			// A�adimos un t�tulo con una fuente personalizada.
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD, Color.RED);
			documento.add(new Paragraph("T�tulo personalizado", font));

			// Insertamos una imagen que se encuentra en los recursos de la
			// aplicaci�n.
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.gob_mun_tlajomulco);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			Image imagen = Image.getInstance(stream.toByteArray());
			documento.add(imagen);

			// Insertamos una tabla.
			PdfPTable tabla = new PdfPTable(5);
			for (int i = 0; i < 15; i++) {
				tabla.addCell("Celda " + i);
			}
			documento.add(tabla);

			// Agregar marca de agua
			font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD, Color.GRAY);
			ColumnText.showTextAligned(writer.getDirectContentUnder(),Element.ALIGN_CENTER, new Paragraph("amatellanes.wordpress.com", font), 297.5f, 421,writer.getPageNumber() % 2 == 1 ? 45 : -45);

			if (!mBluetoothAdapter.isEnabled()) {

	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, 3);
			}

				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + NOMBRE_DIRECTORIO + "/" + NOMBRE_DOCUMENTO);
				ArrayList<Uri> uris=new ArrayList<Uri>();
		    	Intent Int=new Intent();
		    	Int.setAction(android.content.Intent.ACTION_SEND_MULTIPLE);
		    	Int.setType("*");

		    	uris.add(Uri.fromFile(file));

		    	Int.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

		    	startActivity(Int);


		} catch (DocumentException e) {

			Log.e(ETIQUETA_ERROR, e.getMessage());

		} catch (IOException e) {

			Log.e(ETIQUETA_ERROR, e.getMessage());

		} finally {

			// Cerramos el documento.
			documento.close();
		}*/
	}

	public void mostrarVentana() {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.config));

		dialog.setItems(tablet, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				bundle =  new Bundle();
				bundle.putString("usuario", us);
				bundle.putInt("id", id);
				bundle.putString("direccion", direccion);

				SharedPreferences.Editor editor = sp.edit();

				//System.out.println(tablet[which]);
				editor.putString("numt", tablet[which]);
				editor.commit();

				startActivity(new Intent(getApplicationContext(), Descarga.class).putExtras(bundle));
				onDestroy();
			}

		});
		/*final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.config));
		mEdittText = new EditText(getApplicationContext());
		mEdittText.setInputType(InputType.TYPE_CLASS_TEXT);
		mEdittText.setHint(getResources().getString(R.string.config));
		mEdittText.setTextColor(android.R.color.black);
		dialog.setView(mEdittText);

		dialog.setTitle(getResources().getString(R.string.config)).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(!mEdittText.getText().toString().equalsIgnoreCase("")) {

					bundle =  new Bundle();
					bundle.putString("usuario", us);
					bundle.putInt("id", id);
					bundle.putString("direccion", direccion);

					SharedPreferences.Editor editor = sp.edit();
					System.out.println(mEdittText.getText().toString());
					editor.putString("numt", mEdittText.getText().toString());
					editor.commit();

					startActivity(new Intent(getApplicationContext(), Descarga.class).putExtras(bundle));
					onDestroy();
				}
			}
		});*/
		dialog.create().show();

	}

	public static File crearFichero(String nombreFichero) throws IOException {
		File ruta = getRuta();
		File fichero = null;
		if (ruta != null)
			fichero = new File(ruta, nombreFichero);
		return fichero;
	}

	public static File getRuta() {

		// El fichero ser� almacenado en un directorio dentro del directorio
		// Descargas
		File ruta = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);

			if (ruta != null) {
				if (!ruta.mkdirs()) {
					if (!ruta.exists()) {
						return null;
					}
				}
			}
		} else {
		}

		return ruta;
	}

	public void consultaTabletas() {
		int co = 0;
		GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		try {
			Cursor c = db.rawQuery("SELECT * FROM c_tabletas", null);
			if (c.moveToFirst()) {
				tablet = new String[c.getCount()];
				do {
					tablet[co] = c.getString(2);
					co++;
				} while (c.moveToNext());
			}
			c.close();
		} catch (SQLiteException e) {
			Log.e("SQLiteException ", e.getMessage());
		}
		finally {
			db.close();
		}
	}

	/*public class Update extends AsyncTask<String,String,String> {


	    @Override
	    protected void onPreExecute (){
	        super.onPreExecute();
	        pd = new ProgressDialog(Descarga.this);
	        pd.setMessage("Buscando Actualizaciones, Espere");
	        pd.setTitle("Conectando al servidor");
	        pd.show();
	        pd.setCancelable(false);

	        try {
	            /*Cursor c = db.rawQuery("SELECT * FROM usuario", null);
	            c.moveToLast();
	            token = c.getString(1);
	            id_user = c.getString(2);
	            Log.e("TOKEN",token);*
	        }catch (CursorIndexOutOfBoundsException e){
	            Log.e("", "error al obtener token");
	        }

	    }

	    @SuppressLint("SdCardPath")
		protected String doInBackground(String... sUrl) {

	        String path = "/sdcard/Archivo.apk";
	        StringBuilder result = new StringBuilder();
	        //String url_base = "";
	        try {
	            //Realizamos la consulta a la API

	            URL link = new URL("http://10.10.23.54/infracciones/infracciones/sistema/update.php?foco=rojo");
	            urlConnection = (HttpURLConnection) link.openConnection();
	            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                result.append(line);
	            }
	            JSONObject obj = new JSONObject(result.toString());
	            JSONArray jsonArray = obj.optJSONArray("data");
	            int length = jsonArray.length();
	            for (int i = 0; i < length; i++) {
	                JSONObject jsonObject = jsonArray.getJSONObject(i);
	                 liga = jsonObject.optString("link").toString();
	                 version = jsonObject.optString("version").toString();
	                 System.err.println("Mensaje con datos " + liga+" "+version + " " + sUrl[0]);

	            }
	            if (new Double(sUrl[0]).compareTo(new Double(version))==-1){
	                Log.e("Mensaje con datos",liga+" "+version +sUrl[0]);
	                try {
	                    URL url = new URL(liga);
	                    URLConnection connection = url.openConnection();
	                    connection.connect();

	                    int fileLength = connection.getContentLength();

	                    // download the file
	                    InputStream input = new BufferedInputStream(url.openStream());
	                    OutputStream output = new FileOutputStream(path);

	                    byte data[] = new byte[1024];
	                    long total = 0;
	                    int count;
	                    while ((count = input.read(data)) != -1) {
	                        total += count;
	                        publishProgress(String.valueOf((int) (total * 100 / fileLength)));
	                        output.write(data, 0, count);
	                    }

	                    output.flush();
	                    output.close();
	                    input.close();
	                } catch (Exception e) {
	                    Log.d("Cargando", "Fallo conexion");
	                    path = "No";
	                }
	            }else {
	                path ="No";
	            }


	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
	            e.printStackTrace();
	        } catch (NullPointerException e){

	        }
	        return path;
	        }
	        // begin the installation by opening the resulting file

	    @Override
	    protected void onPostExecute(String path) {
	    	pd.dismiss();
	        if (path.equals("No")){
	            Toast toast = Toast.makeText(getApplicationContext(), "No existen Actualizaciones", Toast.LENGTH_LONG);
	            toast.setGravity(Gravity.CENTER, 0, 0);
	            toast.show();
	        }else {
	            Intent i = new Intent();
	            i.setAction(Intent.ACTION_VIEW);
	            i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
	            Log.d("Cargando", "Se instalara el nuevo APK");
	            startActivity(i);
	        }

	    }

	    @Override
	    protected void onCancelled() {
	        super.onCancelled();
	    }
	}*/

	public class Ingresar extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
			mProgressBar.setMax(23);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mProgressBar.setProgress(values[0]);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			Log.e("fire", "doInBackground: 1234");
			insertar(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.INVISIBLE);
			if (x>0){
				btnInfraccion.setVisibility(View.GONE);
				msj="no se actualizo ";
				msj+=mensaje;
				editor.putInt("values_cr",0);
			} else{
				if(msj.equals("No se pudo conectar con el servidor")){
					AlertDialog.Builder builder =
							new AlertDialog.Builder(Descarga.this);

					builder.setMessage(msj)
							.setTitle("Información")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});

					builder.create().show();
					Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}else{
					msj=" Se actualizo correctamente ";
					msj+=mensaje;
					btnInfraccion.setVisibility(View.VISIBLE);
					editor.putInt("values_cr",1);

					AlertDialog.Builder builder =
							new AlertDialog.Builder(Descarga.this);

					builder.setMessage(msj)
							.setTitle("Información")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});

					builder.create().show();
					Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}

			}
			editor.commit();

		}

	}

	public class Actualizar extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mProgressBar.setProgress(values[0]);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			//insertar(params[0]);
			fechasicro=fechasicronizacion("levantamiento");
			Log.e("fecha sicronizacion",""+fechasicro);
			if(fechasicro!=null){
				actualiza();

				return null;
			}else{
				System.out.println("primera sicronizacion");
			}
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.INVISIBLE);
			if (x>0){
				btnInfraccion.setVisibility(View.GONE);
				msj=" No se actualizo,";

				editor.putInt("values_cr",1);
			} else{
				if(msj==null)
				msj=" Se actualizo correctamente ";


				msj+=mensaje;
				btnInfraccion.setVisibility(View.VISIBLE);
				editor.putInt("values_cr",1);
				/*AlertDialog.Builder builder =
						new AlertDialog.Builder(Descarga.this);

				builder.setMessage(msj)
						.setTitle("Información")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				builder.create().show();*/
				Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
				toast.setGravity(0, 0, 15);
				toast.show();
			}
			editor.commit();

		}
	}

	public class Descargas extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			//No se pudo conectar con el servidor
			Log.i("imprime:", String.valueOf(conn.search(urlP+"getC_visitado_manifiesta.php").equals("No se pudo conectar con el servidor")));
			if (conn.search(urlP+"getC_Direccion.php").equals("No se pudo conectar con el servidor") || conn.search(urlP+"getC_Direccion.php").trim().equals(null)) {
				//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
				//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {

				msj = "No se encontro conexion a internet";
			}
			else {
				if (conn.validarConexion(getApplicationContext())) {
					Log.i("entro descarga levantam", "1 ");
					descargarLevantamiento();
					Log.i("entro descarga detalle", "1 ");
					descargarDetalle();
					Log.i("entro descarga foto", "1 ");
					descargarFotografia();
					msj = "Datos enviados al servidor";
				} else {
					msj = "No se encontro conexion a internet";
				}

			}
			return msj;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

	}

	/*
	 * if(VerificarFoto().equals("")){
					if (!conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
						if (conn.validarConexion(getApplicationContext())) {
							//foto();
							fotografias();
							msj = "La(s) Imagen(es) se ha(n) enviado al servidor";
						}
						else
							msj = "No se encontro conexion a internet";
					}else
						msj = "No se pudo conectar con el servidor";
				}
				else
					msj = "No hay datos guardados en el dispositivo";
				Toast toast = Toast.makeText(Descarga.this, msj, Toast.LENGTH_SHORT);
				toast.setGravity(0, 0, 15);
				toast.show();
	 */

	public  class EFoto extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			if(VerificarFoto().equals("")){
				if (conn.search(urlP+"getC_Direccion.php").equals("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					//if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
					msj = "No se pudo conectar con el servidor";

				}else{

					if (conn.validarConexion(getApplicationContext())) {
						//foto();
						fotografias();
						msj = "La(s) Imagen(es) se ha(n) enviado al servidor";
					}
					else
						msj = "No se encontro conexion a internet";
				}

			}
			else
				msj = "No hay fotografias por enviar";
			return msj;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgressBar.setVisibility(View.GONE);
			Toast toast = Toast.makeText(Descarga.this, result, Toast.LENGTH_SHORT);
			toast.setGravity(0, 0, 15);
			toast.show();
		}

	}
	public class Sincronizar extends AsyncTask<String,Void,Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Boolean doInBackground(String... strings) {
			ArrayList<NameValuePair> folio = new ArrayList<>();
			folio.add(new BasicNameValuePair("id",strings[0]));

			JSONObject json = jsonParser.realizarHttpRequest(urlP+"ultimoFolio.php","GET",folio);
			int estatus = 0;
			try {
				estatus = json.getInt("estatus");
				System.out.println(estatus+"ESTATUS:::");
				if(estatus == 1)
					Descarga.this.folio = json.getInt("folio")+1;
			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			} catch (NullPointerException e){
				e.printStackTrace();
				return false;
			}
			return estatus==1;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
			super.onPostExecute(aBoolean);
			mProgressBar.setVisibility(View.GONE);
			String m = "";
			System.out.println(aBoolean);
			if(aBoolean) {
				SharedPreferences.Editor editor = sp.edit();
				if(folio == 1)
					folio = min;
				editor.putInt("folio", folio);
				editor.apply();
				editor.putInt("idIns",idIns);
				editor.apply();
				m = "Se sincronizo correctamente";
				btnInfraccion.setEnabled(true);
				System.out.println("entro si");
			}else {
				btnInfraccion.setEnabled(false);
				m = "No se sincronizo correctamente. Revise su conexion a internet!!";
				System.out.println("entro no");

			}
			Toast toast = Toast.makeText(getApplicationContext(),m,Toast.LENGTH_SHORT);
			toast.setGravity(0,0,15);
			toast.show();
		}
	}

}
