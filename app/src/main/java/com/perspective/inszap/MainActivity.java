package com.perspective.inszap;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {
	private Connection c;
	private InputStream is;
	//private Spinner spDireccion, spUsuario;
	private AutoCompleteTextView spDireccion,spUsuario;
	private EditText etContrasena;
	private Button btnIngresar;
//	//Esteban
//	private Button btnActualizar;

	private TextView tvInpector,tvContrasena,tvDireccion,title;
	private int id_;
	private Connection conn = new Connection();
	private String usuario,pass,direccion,mensaje;
	 static String vigencia,vigencia_inicial;
	 static int id_ins_sesion;
	private ArrayList<String> arregloLista = new ArrayList<String>();
	private ArrayList<String> user = new ArrayList<String>();
	private ArrayList<Integer> id = new ArrayList<Integer>();
	private Calendar cal = Calendar.getInstance();
	private Date date;
	private DateFormat sdf = new SimpleDateFormat("dd/MM/yy");
	private ArrayList<String> f = new ArrayList<String>();
	private ContentValues cv = null;
	private int idI = 0;
	private List<Integer> idInsp = new ArrayList<>();

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*if(true)
			throw new RuntimeException("error de prueba");*/

		Typeface helvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeueLTStd-Bd.otf");

		title = (TextView)findViewById(R.id.title);
		tvDireccion = (TextView)findViewById(R.id.tvDireccion);
		tvInpector = (TextView)findViewById(R.id.tvUsuario);
		tvContrasena = (TextView)findViewById(R.id.tvcontrasena);
		btnIngresar = (Button)findViewById(R.id.btnIngresar);

////		ESTEBAN
//		btnActualizar = (Button)findViewById(R.id.btnActualizar);

		//spDireccion = (Spinner)findViewById(R.id.spDireccion);

		 spDireccion = findViewById(R.id.spDireccion);
		 spUsuario = findViewById(R.id.spUsuario);
		//spUsuario = (Spinner)findViewById(R.id.spUsuario);
		etContrasena = (EditText)findViewById(R.id.etContrasena);

		title.setTypeface(helvetica);
		tvDireccion.setTypeface(helvetica);
		tvInpector.setTypeface(helvetica);
		tvContrasena.setTypeface(helvetica);
		btnIngresar.setTypeface(helvetica);

//		//Esteban
//		btnActualizar.setTypeface(helvetica);

		spDireccion.setFocusable(true);
		spUsuario.setFocusable(true);

		listar();
		listar1();
		Log.e("er", "ror");
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();

    	try {
			if(db != null){
				Cursor c  = db.query("Levantamiento", null, "id_levantamiento", null, null, null, null);
				//Cursor c = db.rawQuery("select * from Detalle_infraccion", null);
				if(c.moveToFirst()){
					do {
						System.out.println(c.getString(2) + " HOLA MUNDO");
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException e) {

		}
    	finally {
    		db.close();
    	}



		if (comprobar()) {
			Toast toast = Toast.makeText(this, "SE HA DETECTADO QUE EXISTE INFORMACION EN LA TABLETA DE DIAS ANTERIORES.", Toast.LENGTH_LONG);
			toast.setGravity(15, 0, 0);
			toast.show();
		}


		spDireccion.setFocusableInTouchMode(true);

		spDireccion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
		//spUsuario.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,user));

		/*spDireccion.setOnItemSelectedListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				//Log.i("id", MainActivity.this.id.get(position) + "");
				//editText.setText(selectedCity.getScreenText());
				String direccion2=spDireccion.getText().toString();

				id_ = MainActivity.this.id.get(position);
				direccion = arregloLista.get(position);
				Log.e("direccion:", direccion2);
				actualizar();
				for(int i = 0; i < MainActivity.this.id.size(); i++) {
					Log.i("mes1", MainActivity.this.id.get(i) + " direccion: " + direccion);
				}
			}
		});*/
		spDireccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//String direccion2=spDireccion.getText().toString();

				View txtvw=(TextView) view;
				String str= ((TextView) txtvw).getText().toString();


				for (int i = 0; i < arregloLista.size(); i++) {
					//Almacena la posición del ítem que coincida con la búsqueda
					if (arregloLista.get(i).equalsIgnoreCase(str)) {
						id_=MainActivity.this.id.get(i);
						direccion=arregloLista.get(i);
						break;
					}
				}

				//direccion = arregloLista.get(position);
				//direccion=str;

				Log.e("direccion:", direccion);
				Log.e("direccion:", String.valueOf(id_));
				actualizar();
			}
		});

		spUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//usuario = spUsuario.getItemAtPosition(position).toString();

				View txtvw=(TextView) view;
				String str= ((TextView) txtvw).getText().toString();


				for (int i = 0; i < user.size(); i++) {
					//Almacena la posición del ítem que coincida con la búsqueda
					if (user.get(i).equalsIgnoreCase(str)) {
						//id_=i+1;
						usuario= user.get(i);
						idI=idInsp.get(i);
						break;
					}
				}
				//usuario=user.get(position);
				//idI = idInsp.get(position);
				Log.i("mes2", usuario);
			}
		});

		/*spUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				usuario = spUsuario.getItemAtPosition(position).toString();
				idI = idInsp.get(position);
				Log.i("mes2", usuario);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {


			}
		});*/

//		//ESTEBAN
//		btnActualizar.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Toast.makeText(MainActivity.this, "Espera un momento...", Toast.LENGTH_SHORT).show();
//				String version,xyz[],x,y,z;
//				version = getString(R.string.version);
//
//				xyz = version.split("\\.");
//				x = xyz[0].substring(xyz[0].length()-1,xyz[0].length());
//				y = xyz[1];
//				z = xyz[2].substring(0,1);
//
//				Actualizador actualizador = new Actualizador(MainActivity.this);
//				actualizador.execute("https://github.com/esteban1810/Inspeccion/raw/main","inspeccion",x,y,z);
//			}
//		});
//		//Fin ESTEBAN

		btnIngresar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pass = etContrasena.getText().toString();


				if(validarCampos(etContrasena)){
					Toast toast = Toast.makeText(MainActivity.this, "Ingrese la contraseña", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
				else{

					if(ingresar(usuario, pass)){

						if(estaInstaladaAplicacion("com.dynamixsoftware.printershare",getApplicationContext())) {

							Log.i("id_c_direccion", String.valueOf(id_));
							Log.i("direccion", direccion);
							Intent intent = new Intent(MainActivity.this, Descarga.class);
							//Intent intent = new Intent(MainActivity.this,TestActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("direccion", direccion);
							bundle.putString("usuario", usuario.trim());
							bundle.putInt("id", id_);
							bundle.putInt("idInps", idI);


							intent.putExtras(bundle);
							SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = preferencias.edit();
							editor.putString("usuario", usuario.trim());
							editor.putInt("id_usuario", id_);
							editor.putString("direccion", direccion);


							editor.commit();
							startActivity(intent);
							MainActivity.this.finish();
							mensaje = null;
						}else{

							Toast toast = Toast.makeText(MainActivity.this, "¡¡No se encuentra instalado PRINTER SHARE!! ", Toast.LENGTH_SHORT);
							toast.setGravity(0, 0, 15);
							toast.show();

						}
					}
					else{
						if(mensaje!=null){
							if (conn.validarConexion(getApplicationContext())) {
								AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
								dialog.setTitle(mensaje);
								dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								});
								dialog.setMessage("¿Esta seguro?").setPositiveButton("SI", new DialogInterface.OnClickListener() {


									@Override
									public void onClick(DialogInterface dialog, int which) {
										new Actualizar().execute();

									}
								});
								AlertDialog alert = dialog.create();
								alert.show();
							} else{
								Toast toast = Toast.makeText(MainActivity.this, "No se encontro conexion a internet,vuelva a ingresar para actualizar", Toast.LENGTH_SHORT);
								toast.setGravity(0, 0, 15);
								toast.show();
							}
								//msj = "No se encontro conexion a internet";
						}else{
							Toast toast = Toast.makeText(MainActivity.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT);
							toast.setGravity(0, 0, 15);
							toast.show();
						}


					}
				}
				etContrasena.setText("");
			}
		});

		spDireccion.setFocusable(true);

		//listarInf();
		if(!isTableExists("Articulos_fracciones")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table Articulos_fracciones(id_articulo INTEGER PRIMARY KEY AUTOINCREMENT,art Integer,fraccion TEXT,orden Integer)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");
		if(!isTableExists("c_tabletas")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_tabletas(id_c_tabletas integer PRIMARY KEY AUTOINCREMENT,tableta TEXT, usuario_tableta TEXT, capturo TEXT, fecha numeric)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");
		if(!isTableExists("c_giro2")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_giro2(id integer PRIMARY KEY AUTOINCREMENT,giro TEXT, capturo TEXT, fecha TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");
		if(!isTableExists("tablet_user")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table tablet_user(id integer PRIMARY KEY AUTOINCREMENT,codigo TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");
		if(!isTableExists("SeguimientoM")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table SeguimientoM(id_MedidaS INTEGER PRIMARY KEY AUTOINCREMENT,numero_acta TEXT,id_inspector TEXT,medida_precautoria TEXT,fecha TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(!isTableExists("v_LicenciasReglamentos")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table v_LicenciasReglamentos(id_v_LicenciasReglamentos integer PRIMARY KEY AUTOINCREMENT,NumeroLicencia TEXT, Nombre TEXT, NombreCalle TEXT, Exterior TEXT,Interior TEXT,NombreColonia TEXT,GiroPrincipal text,Categoria TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(!isTableExists("vs_InspM2")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table vs_InspM2(id_vs_InspM2 integer PRIMARY KEY AUTOINCREMENT,IdLicencia TEXT, NombrePropietario TEXT, Calle TEXT, Numero TEXT,Fraccionamiento TEXT,GiroConstruccionLic TEXT,m2 text,m3 TEXT,mL TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");


		if(!isTableExists("c_medida_seguridad")) {
			System.out.println("false c_medida_seguridad");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_medida_seguridad(id_c_medida_seguridad integer PRIMARY KEY AUTOINCREMENT,medida_seguridad TEXT, articulos TEXT, capturo TEXT, fecha numeric)");
				System.out.println("false c_medida_seguridad");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true c_medida_seguridad");


		if(!isTableExists("c_competencias")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_competencias(id_c_competencia integer PRIMARY KEY AUTOINCREMENT,id_c_direccion integer,reglamento TEXT, competencia TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(registros("c_competencias") == 0) {
			System.out.println("insert");
			insertCompetencia();
		}


		if(!isTableExists("c_medida_precautoria")) {
			System.out.println("false M");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_medida_precautoria(id_c_medida_precautoria integer PRIMARY KEY AUTOINCREMENT,campo TEXT,medida_precautoria TEXT,articulos TEXT,ordenamiento TEXT,vigencia TEXT, capturo TEXT,fecha TEXT)");
				System.out.println("false M");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true M");

		if(!isTableExists("c_medida_precautoria_espacios")) {
			System.out.println("false e");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_medida_precautoria_espacios(id_c_medida_precautoria integer PRIMARY KEY AUTOINCREMENT,campo TEXT,medida_precautoria TEXT,articulos TEXT,ordenamiento TEXT,vigencia TEXT, capturo TEXT,fecha TEXT)");
				System.out.println("false e");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true e");

		if(!isTableExists("c_medida_tabla")) {
			System.out.println("false c_medida_tabla");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_medida_tabla(id_c_medida_tabla integer PRIMARY KEY AUTOINCREMENT,fundamento TEXT)");
				System.out.println("false e");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true c_medida_tabla");

		if(!isTableExists("c_medida_tabla_fraccion")) {
			System.out.println("false c_medida_tabla_fraccion");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_medida_tabla_fraccion(id_c_medida_tabla_fraccion_id integer PRIMARY KEY AUTOINCREMENT,id_c_medida_tabla integer,fraccion TEXT)");
				System.out.println("false e");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true c_medida_tabla_fraccion");


		if(!isTableExists("c_virtud")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_virtud(id_c_virtud integer PRIMARY KEY AUTOINCREMENT,virtud TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(!isTableExists("c_me_constitui")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_me_constitui(id_c_me_constitui integer PRIMARY KEY AUTOINCREMENT,me_constitui TEXT)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(registros("c_virtud") == 0) {
			System.out.println("insert");
			insertVirtud();
		} else
			System.out.println("no insert");

		if(validarCampo() == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add id_c_competencia integer";
			db1.execSQL(sql);

			System.err.println("alter");

		} else {
			System.err.println("no alter");
		}


		if(validarCampo("Detalle_infraccion", "especificacion") == 0) {

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Detalle_infraccion add especificacion text";
			db1.execSQL(sql);
		}



		if(validarCampo("Levantamiento", "id_c_competencia1") == 0) {

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add id_c_competencia1 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_competencia2 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_competencia3 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_competencia4 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_competencia5 integer";
			db1.execSQL(sql);

		}

		if(validarCampo("Levantamiento", "licencia_giro") == 0) {

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add licencia_giro text";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add actividad_giro text";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add axo_licencia integer";
			db1.execSQL(sql);

		}

		if(validarCampo("Levantamiento", "id_c_inspector3") == 0) {

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add id_c_inspector3 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_inspector4 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_inspector5 integer";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add id_c_inspector6 integer";
			db1.execSQL(sql);

		}

		if(validarCampo("Levantamiento", "peticion") == 0) {

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add peticion Text";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add v_firma Text";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add motivo_orden Text";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add medida_seguridad Text";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add articulo_medida Text";
			db1.execSQL(sql);

		}

		if(validarCampo("vs_InspM2","fechaA") == 0){
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table vs_InspM2 add fechaA numeric";
			db1.execSQL(sql);
		}

		if(validarCampo("v_LicenciasReglamentos","fechaA") == 0){
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table v_LicenciasReglamentos add fechaA numeric";
			db1.execSQL(sql);
		}

		if(validarCampo("Levantamiento", "status") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add status Text";
			db1.execSQL(sql);
		}

		if(validarCampo("Levantamiento", "motivo_orden") == 0) {

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add motivo_orden Text";
			db1.execSQL(sql);

		}

		if(validarCampo("Levantamiento", "identifica") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add identifica TEXT";
			db1.execSQL(sql);

		}

		if(validarCampo("Levantamiento", "entre_calle1") == 0){
			System.out.println("salter");

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add entre_calle1 TEXT";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add entre_calle2 TEXT";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add responsable_obra TEXT";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add registro_responsable TEXT";
			db1.execSQL(sql);

		}else {
			System.out.println("nalter");
		}

		if(validarCampo("Levantamiento", "medidas") == 0){
			System.out.println("salter");

			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add medidas TEXT";
			db1.execSQL(sql);

		}else {
			System.out.println("nalter");
		}

		if(validarCampo("Levantamiento", "nombre_comercial") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add nombre_comercial TEXT";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add sector TEXT";
			db1.execSQL(sql);
		}

		if(validarCampo("Detalle_infraccion", "estatus1") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Detalle_infraccion add estatus1 TEXT default 'N'";
			db1.execSQL(sql);
		}
		if(validarCampo("Detalle_infraccion", "unidad") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Detalle_infraccion add unidad TEXT";
			db1.execSQL(sql);
		}

		if(validarCampo("Fotografia", "estatus1") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Fotografia add estatus1 TEXT default 'N'";
			db1.execSQL(sql);
		}

		if(validarCampo("c_medida_precautoria","id_c_direccion") == 0){
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table c_medida_precautoria add id_c_direccion integer";
			db1.execSQL(sql);
		}

		if(!isTableExists("vista_levantamiento"))  {
			System.err.println(false);
			try {
				GestionBD gestionBD = new GestionBD(this, "inspeccion", null, 1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table vista_levantamiento(id_vista_levantamiento integer PRIMARY KEY AUTOINCREMENT,id_levantamiento integer,numero_acta text,calle text,numero_ext text,numero_int text,fraccionamiento text,nombre_razon text,fecha numeric,nombre text,hechos text,id_pago integer,id_c_infraccion text)");
				System.err.println(false);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}else
			System.err.println("true");

		if(validarCampo("Levantamiento", "id_levantamiento_reincidencia") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add id_levantamiento_reincidencia INTEGER";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add numero_acta_reincidencia TEXT";
			db1.execSQL(sql);

			System.err.println("AQUI LALALALA1111");
		}

		if(validarCampo("Levantamiento", "l_construccion") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add l_construccion TEXT";
			db1.execSQL(sql);

			/*sql = "alter table Levantamiento add numero_acta_reincidencia TEXT";
			db1.execSQL(sql);*/

			System.err.println("AQUI LALALALA1111");
		}
		if(validarCampo("Levantamiento", "l_alineamiento") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add l_alineamiento TEXT";
			db1.execSQL(sql);

			/*sql = "alter table Levantamiento add numero_acta_reincidencia TEXT";
			db1.execSQL(sql);*/

			System.err.println("AQUI LALALALA1111");
		}

		if(validarCampo("Levantamiento","nivel_economico") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add nivel_economico TEXT";
			db1.execSQL(sql);
			sql = "alter table Levantamiento add reincidencia TEXT";
			db1.execSQL(sql);

			System.err.println("peticion");
		}
		if(validarCampo("Levantamiento","tipo_cedula") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add tipo_cedula integer";
			db1.execSQL(sql);

			System.err.println("tipo_cedula");
		}
		if(validarCampo("Levantamiento","folio_peticion") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add folio_peticion TEXT";
			db1.execSQL(sql);

			System.err.println("folio_peticion");
		}
		if(validarCampo("Levantamiento","folio_apercibimiento") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add folio_apercibimiento TEXT";
			db1.execSQL(sql);

			System.err.println("folio_apercibimiento");
		}
		if(validarCampo("Levantamiento","fecha_apercibimiento") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add fecha_apercibimiento TEXT";
			db1.execSQL(sql);

			System.err.println("fecha_apercibimiento");
		}
		if(validarCampo("Levantamiento","folio_clausura") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add folio_clausura TEXT";
			db1.execSQL(sql);

			System.err.println("folio_clausura");
		}
		if(validarCampo("Levantamiento","fecha_clausura") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add fecha_clausura TEXT";
			db1.execSQL(sql);

			System.err.println("fecha_clausura");
		}
		if(!isTableExists("c_peticion")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table c_peticion(id_c_peticion integer PRIMARY KEY AUTOINCREMENT,peticion TEXT,capturo Text,fecha numeric)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(!isTableExists("concepto_ov")) {
			System.out.println("false");

			try {
				GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
				SQLiteDatabase db1 = gestionBD.getReadableDatabase();
				db1.execSQL("create table concepto_ov(id_concepto_ov integer PRIMARY KEY AUTOINCREMENT,id_c_direccion TEXT,concepto Text,articulo text,fraccion text)");
				System.out.println("false");
			} catch(SQLiteException e) {
				System.err.println(e.getMessage());
			}
		} else
			System.out.println("true");

		if(validarCampo("Levantamiento","numero_sellos") == 0) {
			GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db1 = gestionBD.getWritableDatabase();

			String sql = "alter table Levantamiento add numero_sellos TEXT";
			db1.execSQL(sql);

			sql = "alter table Levantamiento add decomiso TEXT";
			db1.execSQL(sql);

			System.err.println("numero_sellos");
		}

		consultade();
		consultaf();
		consultaTodo1();

		setMobileDataEnabled(getApplicationContext(),true);
	}


	public boolean isTableExists(String tableName) {
		GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionBD.getReadableDatabase();
	    Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
	    if(cursor!=null) {
	        if(cursor.getCount()>0) {
	        	cursor.close();
	            return true;
	        }
	        cursor.close();
	    }
	    return false;
	}

	public int registros(String table) {
		GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionBD.getReadableDatabase();

		Cursor cursor = db.rawQuery("select * from " + table, null);
		if(db != null) {
			if(cursor.getCount() > 0) {
				return cursor.getCount();
			}
			cursor.close();
		}
		return 0;
	}

	public void insertVirtud() {
		GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionBD.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("virtud", "virtud de denuncia an�nima recibida en esta Direcci�n");

		db.insert("c_virtud", null, cv);


		cv.put("virtud", "virtud de solicitud por escrito por parte de particular presentado en esta Direcci�n");

		db.insert("c_virtud", null, cv);


		cv.put("virtud", "acatamiento a lo ordenado por el (juzgado/tribunal o Derechos Humanos)");

		db.insert("c_virtud", null, cv);

		cv.put("virtud", "atenci�n al reporte: ###.");

		db.insert("c_virtud", null, cv);

		db.close();
	}
	private boolean estaInstaladaAplicacion(String nombrePaquete, Context context) {

		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(nombrePaquete, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}
	public void insertCompetencia() {

		GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionBD.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("id_c_direccion", 1);
		cv.put("reglamento", "C�digo Urbano");
		cv.put("competencia", "art�culos: 1�, 2, 3, 6 fracci�n III, 10 fracciones I, XL, XLI, LII, 11 fracciones XIV, XV, XVI.");

		db.insert("c_competencias", null, cv);

		cv.put("id_c_direccion", 1);
		cv.put("reglamento", "Reglamento de Zonificaci�n Especifica para Estaciones de Servicio o Gasolineras");
		cv.put("competencia", "art�culos: 1, 2, 5, art�culo sexto transitorio de la reforma al art�culo 9 aprobado en sesi�n ordinaria celebrada el 12 de agosto de 2010 y publicada el 26 de agosto de 2010 en el Suplemento de la Gaceta Municipal");

		db.insert("c_competencias", null, cv);

		cv.put("id_c_direccion", 1);
		cv.put("reglamento", "Ley General para la Prevenci�n y Gesti�n Integral de los Residuos");
		cv.put("competencia", "art�culos: 1, 6, 9 �ltimo p�rrafo, 10 fracciones II, III, VII, VIII, X, XII, 95");

		db.insert("c_competencias", null, cv);

		cv.put("id_c_direccion", 1);
		cv.put("reglamento", "Reglamento de Rastro en el Municipio de Guadalajara");
		cv.put("competencia", "1, 3 fracci�n I y IX, 5, 78, 79 fracciones I, II, III, IV, V");

		db.insert("c_competencias", null, cv);

		cv.put("id_c_direccion", 1);
		cv.put("reglamento", "Ley para Regular la Venta y el Consumo de Bebidas Alcoh�licas del Estado de Jalisco");
		cv.put("competencia", "art�culos 1 punto 1 fracciones I y II, 2 puntos 1 y 2; 3, 4 punto 1 fracciones I, II y III, 5 punto 1 fracciones I y II, 6 punto 1 y 2; 8 punto 1 fracciones II y III, 9. ");

		db.insert("c_competencias", null, cv);

	}

	public int validarCampo(){

		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Levantamiento", null);
		try {
			if(db != null){
				//if (c.moveToFirst()) {
				if(c.getColumnIndex("id_c_competencia") > 0)
					return c.getColumnIndex("id_c_competencia");
				//}

			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
		return 0;

	}

	public int validarCampo(String tabla , String campo){

		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + tabla, null);
		try {
			if(db != null){
				//if (c.moveToFirst()) {
				if(c.getColumnIndex(campo) > 0)
					return c.getColumnIndex(campo);
				//}

			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
		return 0;

	}

	public ArrayList<String> fechaL () {
		ArrayList<String> fecha = new ArrayList<String>();
		GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionBD.getReadableDatabase();
		if(db != null) {
			Cursor c = db.query("Levantamiento", null, "id_levantamiento", null, null, null, null);
			if (c.moveToFirst()) {
				do {
					fecha.add(c.getString(6));
				} while (c.moveToNext());
			}
			c.close();
		}
		db.close();
		return fecha;
	}

	public boolean comprobar () {
		boolean r = false;
		if (!fechaL().isEmpty()) {
			f = fechaL();
			for (int i = 0; i < f.size(); i++) {
				try {
					date = (Date)sdf.parse(f.get(i));
					System.out.println(sdf.parse(date.toString()) + "fecha F");
					//if (cal.getTime().compareTo(date) > 0) {
					if (cal.getTime().before(date)) {
						r = true;
					}
				} catch (ParseException e) {

				}

			}
		}
		return r;
	}

	public void listarInf() {

    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	Cursor c;
    	try{
    		//Log.i("consulta", "select * from C_infraccion where vigente = 's' order by id_c_infraccion");
    		//c = db.rawQuery("select vigente from C_infraccion where id_c_direccion = '1' ", null);
			c = db.rawQuery("select * from c_inspector where id_c_direccion = '1' and trim(vigente) = 'S'", null);

	    	if(c.moveToFirst()){
	    		do{
	    			System.out.println(c.getString(c.getColumnIndex("ife")) + ": ife " + c.getString(c.getColumnIndex("nombre")));
	    			//Log.i("listado", "Infraccion: " + c.getString(2));
	    		}while(c.moveToNext());
	    	}
	    	else{
	    		Toast toast = Toast.makeText(this, "No hay infracciones en la bd", Toast.LENGTH_SHORT);
	    		toast.setGravity(0, 0, 15);
				toast.show();
	    	}
	    	c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}
    	finally {
    		db.close();
    	}

    }

	public void actualizar(){
		listarUsuario(id_);
		if(user.isEmpty()){
			spUsuario.setEnabled(false);
			user.add("");
			idInsp.add(0);
			spUsuario.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,user));
			Toast toast = Toast.makeText(this, "No hay usuarios en la direcci�n que selecciono", Toast.LENGTH_SHORT);
			toast.setGravity(0, 0, 15);
			toast.show();
			btnIngresar.setEnabled(false);
			etContrasena.setEnabled(false);
		}
		else{

			spUsuario.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,user));
			btnIngresar.setEnabled(true);
			etContrasena.setEnabled(true);
			spUsuario.setEnabled(true);
		}
	}

	public void compr() {
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM c_puestos", null);
    		System.out.println(c.getColumnName(0) + " holaaaaaaaaaaa");
    		c.close();
    	}catch(SQLiteException e) {
    		System.err.println(e.getMessage());
    	}
    	finally {
    		db.close();
    	}
	}

	//SELECT * FROM C_inspector WHERE nombre = 'Jaret Leopoldo Gayt�n Pulido'

	public void listar1(){
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();

    	try{
    		Cursor c = db.rawQuery("SELECT unidad FROM c_infraccion WHERE infraccion like '%Se establece la clausura de la obra, como medida de seguridad%'", null);
    		if(c.moveToFirst()){
    			do{
    				Log.i("inspextor", "nombre:" + c.getString(0)+"12");
    			}while(c.moveToNext());
    			c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    	}catch (SQLiteException e) {
    		Log.e("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
	}

	public void listar(){
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();

    	try{
    		Cursor c = db.query("C_Direccion", null, "id_c_direccion", null, null, null, null);
    		if(c.moveToFirst()){
    			do{
    				arregloLista.add(c.getString(1));
    				id.add(c.getInt(0));
    				Log.i("listado", "nombre: " + c.getString(1));
    			}while(c.moveToNext());
    			//arregloLista.add("Por violar el estado previo de clausurado de una finca,  al laborar en flagrancia en el interior o exterior de la misma, o al realizar trabajos propios o inherentes de cualquier tipo con el fin de continuar con el proceso de construccion, remodelacion, remozamiento o cualquier otro señalado en el cuerpo del acta de clausura previa.");
    			c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
				gestionarBD.ingresarDireccion(db);

    		}
    	}catch (SQLiteException e) {
    		Log.e("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
	}

	public void listarUsuario(int id){
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	user.clear();
    	idInsp.clear();
    	try{
    		Cursor c = db.rawQuery("Select * from C_inspector where id_c_direccion = '" + id + "' and (trim(vigente) = 'S' or trim(vigente) = 's') order by nombre asc", null);
    		if(c.moveToFirst()){
    			do{
    				System.out.println(c.getString(1) + " " + c.getColumnName(1));
    				user.add(c.getString(1));
    				idInsp.add(c.getInt(0));
    			}while(c.moveToNext());
    			c.close();
    		}else{
				gestionarBD.ingresarInspectores(db);
			}
    	}catch (SQLiteException e) {
    		Log.e("bd", e.getMessage());
		}finally {
    		db.close();
		}
	}

	public boolean ingresar(String usuario, String pass){
		boolean r = false;

		/*if(true){
			throw new RuntimeException("pitp");
		}*/

		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		try {
			Cursor c = db.rawQuery("Select * from C_inspector where nombre = '" + usuario + "' and contrasena = '" + pass + "'", null);
			Log.e("ingresar: ","Select * from C_inspector where nombre = '" + usuario + "' and contrasena = '" + pass + "'"  );
			if(c.moveToFirst()) {
				if (c.getInt(c.getColumnIndex("id_c_direccion"))==1) {
					r = true;
				} else {

                    id_ins_sesion=c.getInt(c.getColumnIndex("id_c_inspector"));
					System.out.println(id_ins_sesion);
					vigencia_inicial = c.getString(c.getColumnIndex("vigencia_inicial"));
					vigencia = c.getString(c.getColumnIndex("vigencia"));
					System.out.println(vigencia_inicial);
					System.out.println(vigencia);
					Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(vigencia.trim());
					Date date2= new Date();
					//Date date2=new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-01");

					System.out.println(date1);
					System.out.println(date2);


					if (date2.compareTo(date1) < 0 ) {
						r = true;
						System.out.println("fechas  funka");
					} else {
						System.out.println("fechas no funkan");
						r = false;
						mensaje = "Las vigencias se actualizaran";
					}

				}
				}else{
				System.out.println("no entro a la db");
					r = false;
				}

			c.close();

		} catch (Exception e) {

		}
		finally {
			db.close();
		}
		return r;
	}

	public boolean validarCampos(EditText et){
		return((et == null) || (et.getText().toString() == null) || et.getText().toString().equalsIgnoreCase(""));
	}

	public void consultal() {
			GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
			SQLiteDatabase db = gestion.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM Levantamiento limit 1", null);
			try {
				if(db != null){
					//if (c.moveToFirst()) {
						//do {
							if(c.getColumnIndex("l_alineamiento") == -1){
								db.execSQL("ALTER TABLE Levantamiento ADD COLUMN l_alineamiento TEXT");
								System.err.println(c.getColumnIndex("l_alineamiento"));
							}
							if(c.getColumnIndex("l_construccion") == -1){
								System.err.println(c.getColumnIndex("l_construccion"));
								db.execSQL("ALTER TABLE Levantamiento ADD COLUMN l_construccion TEXT");
							}
							System.err.println(c.getColumnIndex("l_construccion") + " " + c.getColumnIndex("l_alineamiento")/* + " " + c.getString(c.getColumnIndex("l_construccion"))*/);
						//}while (c.moveToNext());
					//}

				}
			} catch (SQLiteException e) {
				Log.e("SQLite", e.getMessage());
			}
			finally {
				db.close();
				c.close();
			}
	}

	public void consultale() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Levantamiento", null);
		try {
			if(db != null){
				//if (c.moveToFirst()) {
					//System.err.println(c.getCount() + " lev");
				//}

			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}

	public void consultade() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Detalle_infraccion", null);
		try {
			if(db != null){
				if (c.moveToFirst()) {
					do {
						//System.err.println(c.getString(c.getColumnIndex("estatus1")) + " " + c.getColumnName(c.getColumnIndex("estatus1")) + " detalle");
					}while(c.moveToNext());
				}

			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}

	public void consultaf() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Fotografia ", null);
		try {
			if(db != null){
				if (c.moveToFirst()) {
					do {
						//System.err.println(c.getString(c.getColumnIndex("estatus1")) + " foto");
					}while(c.moveToNext());
				}

			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}

	public void consultaTodo() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM levantamiento", null);
		try {
			if(db != null){
				if (c.moveToFirst()) {
					do {
						for (int i = 0; i < c.getColumnCount(); i++) {
							System.err.println(c.getColumnName(i) + " " + c.getString(i));
						}
					} while (c.moveToNext());
				}

			}
		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}

	public void consultaTodo1() {
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM levantamiento where numero_acta like '%IN/12/541/21/6/2019/02%'", null);
		String column = "",dato = "";
		try {
			if(db != null){
				if (c.moveToFirst()) {
					do {
						for (int i = 0; i < c.getColumnCount(); i++) {
							System.err.println(c.getColumnName(i) + " " + c.getString(i));
							column += c.getColumnName(i) + ",";
							dato += "'" + c.getString(i) + "',";
						}
					} while (c.moveToNext());
					System.err.println(column);
					System.err.println(dato);
				}
				cv = new ContentValues();
				cv.put("status", "N");
				//System.err.println(db.update("Levantamiento", cv, "numero_acta like 'IN/12/541/21/6/2019/01'", null) + " update");
			}

		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
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
			//mProgressBar.setProgress(values[0]);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			//insertar(params[0]);
			Context main=getApplicationContext();
			c=new Connection(main);

			Descarga.actualiza2(c,main);
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			//mProgressBar.setVisibility(View.GONE);
			//mProgressBar.setVisibility(View.INVISIBLE);

			//editor.commit();
			AlertDialog.Builder builder =
					new AlertDialog.Builder(MainActivity.this);

			builder.setMessage("Se ha actualizado correctamente vuelva iniciar sesion")
					.setTitle("Información")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			builder.create().show();
			/*Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
			toast.setGravity(0, 0, 15);
			toast.show();*/
		}
	}

	public void test(String numeroActa) {
		//IN/2/47/14/8/2020/01
		GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestion.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM levantamiento where numero_acta like '%"+numeroActa+"%'", null);
		String column = "",dato = "";
		try {
			if(db != null){
				if (c.moveToFirst()) {
					do {
						for (int i = 0; i < c.getColumnCount(); i++) {
							System.err.println(c.getColumnName(i) + " " + c.getString(i));
						}
					} while (c.moveToNext());
				}
			}

		} catch (SQLiteException e) {
			Log.e("SQLite", e.getMessage());
		}
		finally {
			db.close();
			c.close();
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public static void setMobileDataEnabled(Context context, boolean enabled) {
		final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			Class conmanClass;
			conmanClass = Class.forName(conman.getClass().getName());
			Log.v("name",conman.getClass().getName());
			Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			Object iConnectivityManager = iConnectivityManagerField.get(conman);
			Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			Method setMobileDataEnabledMethod;
			setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
			Thread.sleep(5000);
		} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException x) {
			Log.e("error",x.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

